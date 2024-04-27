import { Injectable } from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ServiceService {
 public users:any={
   admin:{password:'1234',roles:['STUDENT','ADMIN']},
   user1:{password:'1234',roles:['STUDENT']}
 }

 public username:any;
 public isAuthenticated:boolean=false;
 public roles:string[]=[];
 public apiUrl="http://localhost:9080/"
  constructor(private router:Router,
              private http:HttpClient) { }

  public login(username:string,password:string):boolean{

  if(this.users[username] && this.users[username]['password'] == password){
    this.username=username;
    this.isAuthenticated=true;
    this.roles=this.users[username]['roles'];
    return true;
  }else{
    return false;
  }

 }

 logout(){
    this.isAuthenticated=false;
    this.roles=[];
    this.username=undefined;
    this.router.navigateByUrl("/")
 }

 loadPayment():Observable<any>{
    return this.http.get("http://localhost:9080/payments");
 }
}
