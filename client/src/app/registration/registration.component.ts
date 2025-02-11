  import { Component, OnInit } from '@angular/core';
 import { FormBuilder, FormGroup, Validators } from '@angular/forms';
 import { Router } from '@angular/router';
 import { AuthService } from '../../services/auth.service';
 import { HttpService } from '../../services/http.service';
  
 @Component({
   selector: 'app-registration',
   templateUrl: './registration.component.html',
   styleUrls: ['./registration.component.scss']
 })
 export class RegistrationComponent implements OnInit {
   itemForm: FormGroup;
  
   constructor(
     private formBuilder: FormBuilder,
     private httpService: HttpService,
     private authService: AuthService,
     private router: Router
   ) {
     this.itemForm = this.formBuilder.group({
       email: ['', [Validators.required, Validators.email]],
       password: ['', Validators.required],
       role: [null, Validators.required],
       username: ['', Validators.required]
     });
   }
  
   ngOnInit() {
     // Any initialization logic if needed
   }
  
   onSubmit() {

    console.log("call method");
     if (this.itemForm.valid) {
      // console.log('HI')
       this.httpService.registerUser(this.itemForm.value).subscribe({
         next: () => {
           this.router.navigate(['/login']);
         },
         error: (error) => {
           console.error('Registration error:', error);
         }
       });
     }
   }
 }