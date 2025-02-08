import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';

import { Router } from '@angular/router';
import { of } from 'rxjs';
import { CreateInvestigatorComponent } from '../app/create-investigator/create-investigator.component';
import { AuthService } from '../services/auth.service';
import { HttpService } from '../services/http.service';

describe('CreateInvestigatorComponent', () => {
  let component: CreateInvestigatorComponent;
  let fixture: ComponentFixture<CreateInvestigatorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreateInvestigatorComponent],
      imports: [ReactiveFormsModule],
      providers: [
        { provide: HttpService, useValue: { getInvestigations: () => of([]) } },
        { provide: AuthService, useValue: {} },
        { provide: Router, useValue: {} },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateInvestigatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should be invalid when the form is empty', () => {
    expect(component.itemForm.valid).toBeFalsy();
    expect(component.itemForm.controls['report'].valid).toBeFalsy();
    expect(component.itemForm.controls['status'].valid).toBeFalsy();
  });
  it('should be valid when the form is filled correctly', () => {
    component.itemForm.controls['report'].setValue('Detailed report on investigation');
    component.itemForm.controls['status'].setValue('Completed');

    expect(component.itemForm.valid).toBeTruthy();
    expect(component.itemForm.controls['report'].valid).toBeTruthy();
    expect(component.itemForm.controls['status'].valid).toBeTruthy();
  });
});
