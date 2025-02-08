import { TestBed, ComponentFixture } from '@angular/core/testing';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegistrationComponent } from '../app/registration/registration.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpService } from '../services/http.service';
import { AuthService } from '../services/auth.service';
import { RouterTestingModule } from '@angular/router/testing'; // Add this import

describe('RegistrationComponent', () => {
  let component: RegistrationComponent;
  let fixture: ComponentFixture<RegistrationComponent>;
  let formBuilder: FormBuilder;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegistrationComponent],
      imports: [ReactiveFormsModule, HttpClientTestingModule, RouterTestingModule], // Update imports
      providers: [HttpService, AuthService] 
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistrationComponent);
    component = fixture.componentInstance;
    formBuilder = TestBed.inject(FormBuilder);

    // Create a FormGroup with the form controls defined in your component
    component.itemForm = formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      role: [null, Validators.required],
      username: ['', Validators.required]
    });

    fixture.detectChanges();
  });

  it('should have invalid form if any field is empty', () => {
    const form = component.itemForm;
    expect(form.valid).toBeFalsy();

    const emailControl = form.controls['email'];
    expect(emailControl.valid).toBeFalsy();

    const passwordControl = form.controls['password'];
    expect(passwordControl.valid).toBeFalsy();

    const roleControl = form.controls['role'];
    expect(roleControl.valid).toBeFalsy();

    const usernameControl = form.controls['username'];
    expect(usernameControl.valid).toBeFalsy();
  });

  it('should have invalid form if email is invalid', () => {
    const form = component.itemForm;
    const emailControl = form.controls['email'];

    // Set invalid email
    emailControl.setValue('invalid_email');

    expect(emailControl.valid).toBeFalsy();
  });

  it('should have valid form if all fields are filled correctly', () => {
    const form = component.itemForm;
    const emailControl = form.controls['email'];
    const passwordControl = form.controls['password'];
    const roleControl = form.controls['role'];
    const usernameControl = form.controls['username'];

    // Set valid values
    emailControl.setValue('test@example.com');
    passwordControl.setValue('password');
    roleControl.setValue('user');
    usernameControl.setValue('testuser');

    expect(form.valid).toBeTruthy();
  });
});
