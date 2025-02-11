   import { Component, OnInit } from '@angular/core';
 import { FormBuilder, FormGroup, Validators } from '@angular/forms';
 import { Router } from '@angular/router';
 import { HttpService } from '../../services/http.service';
 import { AuthService } from '../../services/auth.service';
  
 @Component({
   selector: 'app-login',
   templateUrl: './login.component.html',
   styleUrls: ['./login.component.scss']
 })
 export class LoginComponent implements OnInit {
   itemForm: FormGroup;
  
   constructor(
     private formBuilder: FormBuilder,
     private httpService: HttpService,
     private authService: AuthService,
     private router: Router
   ) {
     this.itemForm = this.formBuilder.group({
       username: ['', Validators.required],
       password: ['', Validators.required]
     });
   }
  
   ngOnInit() {
     // Any initialization logic if needed
   }
  
   onSubmit() {
     if (this.itemForm.valid) {
       this.httpService.Login(this.itemForm.value).subscribe({
         next: (response) => {
           // Handle successful login
           this.router.navigate(['/dashboard']);
         },
         error: (error) => {
           console.error('Login error:', error);
         }
       });
     }
   }
  
   onSignUp(){
     this.router.navigate(['/registration'])
   }
  
   onCreate(){
     this.router.navigate(['/create-claim'])
   }
 }