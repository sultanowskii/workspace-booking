import { Directive, Component, inject, model, signal, importProvidersFrom } from "@angular/core";
import { HttpClient, HttpHeaders, HttpClientModule, HttpParams} from "@angular/common/http";
import { CommonModule } from '@angular/common';
import { environment } from '../../environments/environment';
import {FormsModule} from '@angular/forms';
import {ActivatedRoute, Router} from "@angular/router";
import {CdkDragDrop, CdkDragEnd, CdkDrag, CdkDropList, moveItemInArray} from '@angular/cdk/drag-drop';
import { table } from "console";
import { AuthService } from "../services/auth.service";

@Component({
    selector: "scheme-app",
    templateUrl: './scheme.component.html',
    styleUrls: ['./scheme.component.scss'],
    standalone: true,
    imports: [FormsModule,
        HttpClientModule,
        CommonModule,
        CdkDropList, CdkDrag],
})

export class SchemeComponent {
    offices: Array<{id: number; name: string; address: string}> = [];
    rooms: Array<{id: number; name: string; office: string}> = [];
    tables:  Array<{id: number; name: string; visibility: string; mon: number; type: string; position: {x: number, y: number}; length: number; width: number; room: string, book: string;} > = [];
    books: Array<{id: number; name: string; date: string; user: string;} > = [];
    walls: Array<{visibility: string; position: {x1: number, y1: number, x2: number, y2: number}; room: string;} > = [];

    private baseUrl = environment.baseUrl;
    role = '';
    tableType = '';
    useroffice = '';
    selectedTable: any;
    isBookingModalOpen = false;
    user = '';
    filteredRooms: Array<{ id: number; name: string; office: string }> = [];
    filteredOffices = [...this.offices];
    officeSearch = '';
    groups: Array<{id: number; name: string; office: string;} > = [];
    isbooked: string[] = [];
    userRooms: Array<{ id: number; name: string; office: string }> = []; 
    isAddTableFormOpen = false;

    newTable = {
      tabtype: 'rm',  
      rabpernme: '',
      x: '',
      y: '',
      length: '',
      width: '',
      mon: 0
    };
    officeForm:
    any = {
    office: '',
    }
    roomForm:
    any = {
    room: '',
    }
    bookingForm: any = {date: null, selectedTimes:[]};
    availableDates: Date[] = [];
    availableTimes: string[] = [];
      isMyBookingsFormOpen = false;
    
      myBookings: any[] = [];
    
      // openMyBookingsForm() {
      //   this.isMyBookingsFormOpen = true;
      //   this.getMyBookings(); 
      // }
    
      closeMyBookingsForm() {
        this.isMyBookingsFormOpen = false;
      }
    
      // getMyBookings() {
      //   ...
      //   ;
      // }
    
    

    addtable() {
        this.tables.push({
            id: this.tablesForm.id,
            name: this.tablesForm.rabpernme,
            visibility: 'visible',
            mon: Number(this.tablesForm.mon),
            type: this.tablesForm.tabtype,
            position: {x: Number(this.tablesForm.x), y:Number(this.tablesForm.y)},
            width:this.tablesForm.width,
            length:this.tablesForm.length,
            room: this.tablesForm.room,
            book: '',
        });
        this.updateTables();
    }

    addwall() {
        this.walls.push({
            visibility: 'visible',
            position: {x1: Number(this.wallsForm.x1), y1:Number(this.wallsForm.y1), x2:Number(this.wallsForm.x2), y2:(this.wallsForm.y2)},
            room: this.wallsForm.room,
        });
        this.updateWalls();
    }

    newposition(x: number, x2: number, y: number, y2: number) {
        let new_x = x + x2;
        let new_y = y + y2;
        return {"x": new_x, "y": new_y};
    }
    tablesForm: any = {
        x: 0,
        y: 0,
        mon: 0,
        tabtype: '',
        rabpernme: ''
    }

    wallsForm: any = {
        x1: 0,
        y1: 0,
        x2: 0, 
        y2: 0,
        room: ''
    }

    isreg = 0;

    constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient, public authService: AuthService) {
    if (authService.user) {
        this.isreg = 1;
        this.officeslist();
        this.groupslist();
        this.tableslist();
        this.getBooks();
        this.generateAvailableDates();
        this.generateAvailableTimes();
        this.bookingForm.date = this.availableDates[0];
            } else {
                console.log('not reged');
            }
        };
    
        groupslist() {
            this.http
              .get(this.baseUrl + "/api/employeeGroups", {
                headers: {
                  Authorization: `Bearer ${this.authService.user.token}`,
                },
              })
              .subscribe((data: any) => {
                data.forEach((group: any) => {
                  this.groups.push({
                    id: group["id"],
                    name: group["name"],
                  } as any);
                });
              });
          }

    doBook() {
		if (this.bookingForm.date !== "" || this.bookingForm.datetime !== "") {
			let date = this.bookingForm.date;
			let time = "00:00";
			if (this.bookingForm.datetime !== "") {
				date = this.bookingForm.datetime.split("T")[0];
				time = this.bookingForm.datetime.split("T")[1];
			}
	
			// Формируем данные для отправки
			const book_json = JSON.stringify({
				book_name: this.selectedTable, 
				book_date: date, 
				book_time: time, 
				user: this.user
			});
	
			this.http.post(this.baseUrl+"/api", book_json).subscribe(data => {
				// Если бронирование прошло успешно
				if (data && Object.values(data)[0] > 0) {
					this.isbooked.push(this.selectedTable);
					this.checkDate(date);
					
					alert("Бронирование успешно!");
				}
			}, error => {
				console.log(error);
				alert("Ошибка при бронировании.");
			});
		}
	}


    updateFilteredRooms(): void {
        this.filteredRooms = this.rooms.filter(room => 
          room.office === this.officeForm.office
        );
        this.userRooms = this.filteredRooms;
      }
      
  

    updateTables() {
        var tables_json = JSON.stringify(this.tables);
        const body = {json: tables_json};
        this.http.post(this.baseUrl, body).subscribe(data => {});
    }

    updateWalls() {
        var walls_json = JSON.stringify(this.walls);
        const body = {json: walls_json};
        this.http.post(this.baseUrl, body).subscribe(data => {});
    }

    officeslist() {
        this.http
          .get(`${this.baseUrl}/api/offices`, {
            headers: {
              Authorization: `Bearer ${this.authService.user.token}`,
            },
          })
          .subscribe((data: any) => {
            data.forEach((office: any) => {
              this.offices.push({
                id: office["id"],
                name: office["name"],
                address: office["address"]
              });
            });
          });
      }

    roomslist() {
        let params = new HttpParams().set('type', 'rooms');
        this.http.get(this.baseUrl, {params}).subscribe(data => {
            (Object.keys(data)).forEach((key, index) => {
                this.rooms.push({
                    id: Object.values(data)[index]["id"],
                    name: Object.values(data)[index]["name"],
                    office: Object.values(data)[index]["office"]
                });
            });
        });
    }

	unbook(table: string) {
		const unbook_json = JSON.stringify({ unbook: table });
		this.http.post(this.baseUrl, unbook_json).subscribe(data => {
			this.isbooked.splice(this.isbooked.indexOf(table), 1);
			this.tables = this.tables.map(item => 
				item.name === table ? { ...item, book: '' } : item
			);
			if (Object.values(data)[0] === 'ok') {
				alert("Бронирование отменено");
			}
		}, error => {
			console.log(error);
			alert("Ошибка при отмене бронирования.");
		});
	}
	

    tableslist() {
		let params = new HttpParams().set('type', 'tables');
		this.http.get(this.baseUrl, { params }).subscribe(data => {
			this.tables = Object.values(data).map((table: any) => ({
        id: table.id,
				name: table.name,
				visibility: table.visibility,
				mon: table.mon,
				type: table.type,
				position: { x: table.position.x, y: table.position.y },
				width: table.width,
				length: table.length,
				room: table.room,
				book: '' 
			}));
			this.getBooks();
		});
	}

    getBooks(): void {
		let params = new HttpParams().set('type', 'getbooks');
		this.http.get(this.baseUrl, { params }).subscribe(data => {
			// Фильтруем только те записи, которые относятся к текущему пользователю
			const userBooks = Object.values(data).filter((item: any) => item.user === this.user);
			userBooks.forEach((book: any) => {
				this.isbooked.push(book.name);
			});
			this.tables = this.tables.map(item => {
				const bookedItem = userBooks.find(newItem => newItem.name === item.name);
				return bookedItem ? { ...item, book: '1' } : item;
			});
		});
	}


    generateAvailableDates(){
        this.availableDates = [];
        const today = new Date();
        for (let i = 0; i < 5; i++){
            const nextDay = new Date(today);
            nextDay.setDate(today.getDate() + i);
            this.availableDates.push(nextDay);
        }
    }

    generateAvailableTimes(){
      this.availableTimes = [];
      for (let hour = 9; hour < 18; hour++){
        const time = `${hour.toString().padStart(2, '0')}:00`
          this.availableTimes.push(time);
      }
    }

    filterOffices() {
      if (this.role === 'ADMIN') {
      this.filteredOffices = [...this.offices];
      } else {
      this.filteredOffices = this.offices.filter((office) => {
        return this.groups.some((group) => group.office === office.name);
      });
      }
    }
    

  selectOffice(office: any) {
    this.officeForm.office = office;
    this.filteredOffices = [];
    this.officeSearch = '';
      if (office.rooms){
          this.tablesForm.room = office.rooms[0].name;
          this.roomForm.room = this.tablesForm.room
      }
  }

  onRoomChange(){
    this.roomForm.room = this.tablesForm.room
  }

   checkDate(date: Date) {
    console.log('Selected date:', date);
    // Дополнительная логика проверки даты
  }

   dragEnded_new(event: any, i:number){
        this.tables[i].position = event.source.getFreeDragPosition();
   }

  deltab(i: number){
    this.tables.splice(i, 1);
  }

    openBookingModal(table:any){
       this.selectedTable = table;
       this.isBookingModalOpen = true;
        console.log('table: ', table);
    }

  closeBookingModal(){
      this.isBookingModalOpen = false;
      this.selectedTable = null;
      this.bookingForm.selectedTimes = [];
  }

    onTimeChange(time:string, event:any) {
    if (event.target.checked) {
      this.bookingForm.selectedTimes.push(time);
    } else {
      this.bookingForm.selectedTimes = this.bookingForm.selectedTimes.filter((t: string) => t !== time);
    }
  }

    bookTable(){
      console.log('Забронировано:', this.selectedTable, ' на дату: ', this.bookingForm.date, ' время: ', this.bookingForm.selectedTimes);
        this.selectedTable.book = '1';
        this.closeBookingModal()
    }

    openAddTableForm() {
      this.isAddTableFormOpen = true;
    }
  
    // Функция для закрытия формы
    closeAddTableForm() {
      this.isAddTableFormOpen = false;
    }
  
    // Функция для сохранения нового рабочего места
    saveTable() {
      if (this.newTable.rabpernme && this.newTable.x && this.newTable.y) {
        // Логика сохранения нового рабочего места, например, отправка данных на сервер
        console.log('Сохранено новое рабочее место:', this.newTable);
  
        // Закрытие формы после сохранения
        this.closeAddTableForm();
        
        // Очистка формы
        this.newTable = {
          tabtype: 'rm',
          rabpernme: '',
          x: '',
          y: '',
          length: '',
          width: '',
          mon: 0
        };
      }
    }
  }
