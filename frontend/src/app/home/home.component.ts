import { BackendService } from './../service/backend.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(
    private backendSvc:BackendService
  ) { }
  public logout(){
    this.backendSvc.logOut().subscribe(res=>{
      console.log(res);
    });
  }

  getRoot(){
    this.backendSvc.getRoot().subscribe(res=>{
      console.log(res);
    });
  }

  getRootAndUser(){
    this.backendSvc.getRootAndUser().subscribe(res=>{
      console.log(res);
    });
  }
  ngOnInit(): void {
  }

}
