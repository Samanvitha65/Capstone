  import { Component, OnInit } from '@angular/core';
 import { FormGroup, FormBuilder, Validators } from '@angular/forms';
 import { Router } from '@angular/router';
 import { AuthService } from '../../services/auth.service';
 import { HttpService } from '../../services/http.service';
  
 @Component({
   selector: 'app-create-investigator',
   templateUrl: './create-investigator.component.html',
   styleUrls: ['./create-investigator.component.scss']
 })
  
 export class CreateInvestigatorComponent implements OnInit {
   itemForm: FormGroup;
  
   constructor(
     private formBuilder: FormBuilder,
     private httpService: HttpService,
     private authService: AuthService,
     private router: Router
   ) {
     this.itemForm = this.formBuilder.group({
       report: ['', Validators.required],
       status: ['', Validators.required]
     });
   }
  
   ngOnInit() {
     // Load any initial data if needed
   }
  
   onSubmit() {
     if (this.itemForm.valid) {
       this.httpService.createInvestigation(this.itemForm.value).subscribe({
         next: () => {
           // Handle successful creation
           this.router.navigate(['/investigations']); // Adjust route as needed
         },
         error: (error) => {
           console.error('Error creating investigation:', error);
         }
       });
     }
   }
 }