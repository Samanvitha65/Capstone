import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';

import { Router } from '@angular/router';
import { of } from 'rxjs';
import { CreateClaimComponent } from '../app/create-claim/create-claim.component';
import { AuthService } from '../services/auth.service';
import { HttpService } from '../services/http.service';

describe('CreateClaimComponent', () => {
  let component: CreateClaimComponent;
  let fixture: ComponentFixture<CreateClaimComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreateClaimComponent],
      imports: [ReactiveFormsModule],
      providers: [
        { provide: HttpService, useValue: { getClaimsByPolicyholder: () => of([]) } },
        { provide: AuthService, useValue: {} },
        { provide: Router, useValue: {} },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateClaimComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should be invalid when the form is empty', () => {
    expect(component.itemForm.valid).toBeFalsy();
    expect(component.itemForm.controls['description'].valid).toBeFalsy();
    expect(component.itemForm.controls['date'].valid).toBeFalsy();
    expect(component.itemForm.controls['status'].valid).toBeFalsy();
  });
  it('should be valid when the form is filled correctly', () => {
    component.itemForm.controls['description'].setValue('Claim for damages');
    component.itemForm.controls['date'].setValue('2024-08-09');
    component.itemForm.controls['status'].setValue('Pending');

    expect(component.itemForm.valid).toBeTruthy();
    expect(component.itemForm.controls['description'].valid).toBeTruthy();
    expect(component.itemForm.controls['date'].valid).toBeTruthy();
    expect(component.itemForm.controls['status'].valid).toBeTruthy();
  });
});

