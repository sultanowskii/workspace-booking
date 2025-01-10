import { Directive, Component, inject, model, signal, importProvidersFrom } from "@angular/core";
import { HttpClient, HttpHeaders, HttpClientModule, HttpParams} from "@angular/common/http";
import { CommonModule } from '@angular/common';
import { environment } from '../../environments/environment';
import {FormsModule} from '@angular/forms';
import {ActivatedRoute, Router} from "@angular/router";
import { getAuth,createUserWithEmailAndPassword, signInWithEmailAndPassword, onAuthStateChanged } from "firebase/auth";
import { getDatabase, ref, set, update } from "firebase/database";
import { getStorage, ref as ref_storage, uploadBytesResumable, getDownloadURL } from "firebase/storage";
import {CdkDragDrop, CdkDragEnd, CdkDrag, CdkDropList, moveItemInArray} from '@angular/cdk/drag-drop';

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
    offices: Array<{id: number; name: string; }> = [];
    rooms: Array<{id: number; name: string; office: string}> = [];
    tables:  Array<{name: string; visibility: string; mon: number; type: string; position: {x: number, y: number}; length: number; width: number; room: string, book: string;} > = [];
    books: Array<{id: number; name: string; date: string; user: string;} > = [];
    walls: Array<{visibility: string; position: {x1: number, y1: number, x2: number, y2: number}; room: string;} > = [];

    private baseUrl = environment.baseUrl;
    status = '';
    selectedTable = '';
    tableType = '';
    useroffice = '';
    user = '';
    isbooked: string[] = [];

    bookingForm: any = {
        date: '',
        datetime: '',
    }

    addtable() {
        this.tables.push({
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

    dragEnded_new(event: CdkDragEnd<string[]>, i: number) {
        this.tables[i].position = this.newposition(this.tables[i].position.x, event.distance.x, this.tables[i].position.y, event.distance.y);
        this.updateTables();
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

    constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient ) {
        const auth = getAuth();
        onAuthStateChanged(auth, (user) => {
            if (user) {
                const uid = user.uid;
                if (user.email != null) {
                    this.user = user.email;
                    this.GetStatus(user.email);
                }
                this.isreg = 1;
                this.officeslist();
                this.roomslist();
                this.tableslist();
                this.getBooks();
            } else {
                console.log('not reged');
            }
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
	
			this.http.post(this.baseUrl, book_json).subscribe(data => {
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
        let params = new HttpParams().set('type', 'offices');
        this.http.get(this.baseUrl, {params}).subscribe(data => {
            (Object.keys(data)).forEach((key, index) => {
                this.offices.push({
                    id: Object.values(data)[index]["id"],
                    name: Object.values(data)[index]["name"]
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

    deltab(i: number) {
        this.tables.splice(i, 1);
        var tables_json = JSON.stringify(this.tables);
        const body = {json: tables_json};
        this.http.post(this.baseUrl, body).subscribe(data => {});
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
	

    GetStatus(user: string) {
        let params = new HttpParams()
            .set('type', 'status')
            .set('user', user);
        this.http.get(this.baseUrl, {params}).subscribe(data => {
            (Object.keys(data)).forEach((key, index) => {
                this.status = Object.values(data)[0];
                this.useroffice = Object.values(data)[1];
            });
        });
    }

    checkDate(date: string) {
		// Сначала сбрасываем все статусы бронирования
		this.tables = this.tables.map(item => ({ ...item, book: '' }));
		// Фильтруем по дате
		let items = this.books.filter(item => item.date.includes(date));
		// Обновляем статус бронирования для каждого стола
		this.tables = this.tables.map(item => {
			const bookedItem = items.find(newItem => newItem.name === item.name);
			return bookedItem ? { ...item, book: bookedItem.user === this.user ? '1' : '0' } : item;
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
}	