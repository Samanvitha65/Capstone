  import { Component, OnInit } from '@angular/core';
 import { FormGroup, FormBuilder, Validators } from '@angular/forms';
 import { Router } from '@angular/router';
 import { AuthService } from '../../services/auth.service';
 import { HttpService } from '../../services/http.service';
  
 @Component({
   selector: 'app-update-claim',
   templateUrl: './update-claim.component.html',
   styleUrls: ['./update-claim.component.scss']
 })
 export class UpdateClaimComponent implements OnInit {
   itemForm: FormGroup;
  
   constructor(
     private formBuilder: FormBuilder,
     private httpService: HttpService,
     private authService: AuthService,
     private router: Router
   ) {
     this.itemForm = this.formBuilder.group({
       description: ['', Validators.required],
       date: ['', Validators.required],
       status: ['', Validators.required]
     });
   }
  
   ngOnInit() {
     // Load initial data if needed
   }
  
   onSubmit() {
     if (this.itemForm.valid) {
       // Assuming we have a claimId from route params or some other source
       const claimId = 1; // This should be dynamically set based on your application's needs
       this.httpService.updateClaims(this.itemForm.value, claimId).subscribe({
         next: () => {
           this.router.navigate(['/claims']);
         },
         error: (error) => {
           console.error('Error updating claim:', error);
         }
       });
     }
   }
 }