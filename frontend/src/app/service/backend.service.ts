import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Data } from '../student/student.component';

@Injectable({
  providedIn: 'root'
})
export class BackendService {

  constructor(private http: HttpClient) {

  }
  public loginUser(body: { username: String; password: String }) {
    // const headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded');
    // const bodyString = `username=${body.name}&password=${body.password}`;
    // return this.http.post<any>('/login', bodyString, { headers });
    return this.http.post<any>('/api/login', body);
  }

  getRoot() {
    return this.http.get('/hasRootAuthority', { responseType: 'text' });
  }
  getRootAndUser(){
    return this.http.get('/hasRootAndUserAuthority', { responseType: 'text' });
  }

  public getStudentDetail() {
    return this.http.get<Data>('/api/USER/getStudentList');
  }

  public logOut() {
    return this.http.get('/api/logout');
  }
}
