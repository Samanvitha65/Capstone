import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { AppComponent } from './app.component';
import { DashbaordComponent } from './dashbaord/dashbaord.component';
import { CreateClaimComponent } from './create-claim/create-claim.component';
import { UpdateClaimComponent } from './update-claim/update-claim.component';
import { AssignClaimComponent } from './assign-claim/assign-claim.component';
import { CreateInvestigationComponent } from './create-investigation/create-investigation.component';
import { UpdateClaimUnderwriterrComponent} from './update-claim-underwriter/update-claim-underwriter.component';
import { UpdateInvestigationComponent } from './update-investigation/update-investigation/update-investigation.component';
import { HomeComponent } from './home/home.component';



const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'registration', component: RegistrationComponent },
  { path: 'dashboard', component: DashbaordComponent },
  { path: 'home', component:HomeComponent },
  { path: 'create-claim', component: CreateClaimComponent },  
  { path: 'update-claim/:id', component: UpdateClaimComponent },  
  { path: 'assign-claim/:id', component: AssignClaimComponent },  
  { path: 'create-investigation', component: CreateInvestigationComponent },  
  { path: 'update-claim-underwriter/:id', component: UpdateClaimUnderwriterrComponent },  
  { path: 'update-investigation/:id', component:UpdateInvestigationComponent},
 

  { path: '', redirectTo: '/home', pathMatch: 'full' },
 
  { path: '**', redirectTo: '/home', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
