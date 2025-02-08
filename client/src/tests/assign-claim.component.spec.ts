import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { AssignClaimComponent } from '../app/assign-claim/assign-claim.component';
import { HttpService } from '../services/http.service';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { of } from 'rxjs';

describe('AssignClaimComponent', () => {
  let component: AssignClaimComponent;
  let fixture: ComponentFixture<AssignClaimComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AssignClaimComponent],
      imports: [ReactiveFormsModule],
      providers: [
        { provide: HttpService, useValue: { getAllClaims: () => of([]), GetAllUnderwriter: () => of([]), AssignClaim: () => of({}) } },
        { provide: AuthService, useValue: {} },
        { provide: Router, useValue: {} },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignClaimComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should be invalid when the form is empty', () => {
    expect(component.itemForm.valid).toBeFalsy();
    expect(component.itemForm.controls['claimId'].valid).toBeFalsy();
    expect(component.itemForm.controls['underwriterId'].valid).toBeFalsy();
  });
  it('should be valid when the form is filled correctly', () => {
    component.itemForm.controls['claimId'].setValue(1); // Assuming claimId is a number
    component.itemForm.controls['underwriterId'].setValue(2); // Assuming underwriterId is a number

    expect(component.itemForm.valid).toBeTruthy();
    expect(component.itemForm.controls['claimId'].valid).toBeTruthy();
    expect(component.itemForm.controls['underwriterId'].valid).toBeTruthy();
  });
});