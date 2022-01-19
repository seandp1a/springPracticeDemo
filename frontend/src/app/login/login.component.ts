import { BackendService } from './../service/backend.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  userName!: String;
  password!: String;

  constructor(private backendSvc: BackendService) { }

  doLogin() {
    // this.backendSvc.getRoot().subscribe(res=>{
    //   console.log(res);
    // })
    if (!this.userName || !this.password) return;
    const body = {
      username: this.userName,
      password: this.password
    };
    this.backendSvc.loginUser(body).subscribe(res=>{
      console.log(res);
    });
  }

  ngOnInit(): void {
  }



}
