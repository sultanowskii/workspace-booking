import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SchemeService {
  private dataUrl = 'assets/data/workplaces.json';

  constructor(private http: HttpClient) {}

  getWorkplaces(): Observable<any> {
    return this.http.get(this.dataUrl);
  }
}
