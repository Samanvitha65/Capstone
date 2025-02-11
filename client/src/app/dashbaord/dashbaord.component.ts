  import { Component, OnInit } from '@angular/core';
 import { Router } from '@angular/router';
 import { AuthService } from '../../services/auth.service';
 import { HttpService } from '../../services/http.service';
  
 @Component({
   selector: 'app-dashbaord',
   templateUrl: './dashbaord.component.html',
   styleUrls: ['./dashbaord.component.scss']
 })
 export class DashbaordComponent implements OnInit {
  
  
   claims: any[] = [];
   investigations: any[] = [];
   underwriters: any[] = [];
   constructor(
     private httpService: HttpService,
     private authService: AuthService,
     private router: Router
   ) {}
  
   ngOnInit(): void {
     this.loadDashboardData();
   }
  
   loadDashboardData(): void {
     // Load claims
     this.httpService.getAllClaims().subscribe(
       (data: any) => {
         this.claims = data;
       },
       (      error: any) => {
         console.error('Error loading claims:', error);
       }
     );
  
     // Load investigations
     this.httpService.getInvestigations().subscribe(
       (data: any) => {
         this.investigations = data;
       },
       (      error: any) => {
         console.error('Error loading investigations:', error);
       }
     );
  
     // Load underwriters
     this.httpService.GetAllUnderwriter().subscribe(
       (data: any) => {
         this.underwriters = data;
       },
       (      error: any) => {
         console.error('Error loading underwriters:', error);
       }
     );
   }
  
   navigateToCreateClaim(): void {
     this.router.navigate(['/create-claim']);
   }
  
   navigateToAssignClaim(): void {
     this.router.navigate(['/assign-claim']);
   }
  
   navigateToUpdateClaim(): void {
     this.router.navigate(['/update-claim']);
   }
  
   navigateToCreateInvestigator(): void {
     this.router.navigate(['/create-investigator']);
   }
 }