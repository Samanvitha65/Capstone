  import { HttpClient, HttpHeaders } from '@angular/common/http';
 import { Injectable } from '@angular/core';
 import { Observable } from 'rxjs';
 import { environment } from '../environments/environment.development';
  
 @Injectable({
   providedIn: 'root'
 })
 export class AuthService {
  
   private token: string | null = null;
   private isLoggedIn: boolean = false;
   id: string | null | undefined;
  
   constructor() {}
  
   // Method to save token received from login
   saveToken(token: string) {
     this.token = token;
     this.isLoggedIn = true;
     // Optionally, you can save the token to local storage or a cookie for persistence
     localStorage.setItem('token', token);
   }
  
    SetRole(role:any)
   {
     localStorage.setItem('role',role);
   }
   get getRole ():string|null
   {
     return localStorage.getItem('role');
   }
   // Method to retrieve login status
   get getLoginStatus(): boolean {
  
       return !!localStorage.getItem('token');
    
   }
   getToken(): string | null {
    this.token= localStorage.getItem('token');
     return this.token;
   }

   getUserId():string|null{
    return localStorage.getItem('userId');
   }
  
   logout(){
     localStorage.removeItem('token');
     localStorage.removeItem('role');
      this.token=null;
      this.isLoggedIn=false
      window.location.reload()
    }
    saveUserId(userid: string) {
     localStorage.setItem('userId',userid);
   }
 }
  