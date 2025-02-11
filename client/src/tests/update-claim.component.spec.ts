import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { UpdateClaimComponent } from '../app/update-claim/update-claim.component';
import { HttpService } from '../services/http.service';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { of } from 'rxjs';

describe('UpdateClaimComponent', () => {
  let component: UpdateClaimComponent;
  let fixture: ComponentFixture<UpdateClaimComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UpdateClaimComponent],
      imports: [ReactiveFormsModule],
      providers: [
        { provide: HttpService, useValue: { getAllClaims: () => of([]) } },
        { provide: AuthService, useValue: {} },
        { provide: Router, useValue: {} },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateClaimComponent);
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
});
