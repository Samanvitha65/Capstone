import { TestBed, ComponentFixture } from '@angular/core/testing';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginComponent } from '../app/login/login.component';
import { HttpClientTestingModule } from '@angular/common/http/testing'; // Import HttpClientTestingModule
import { HttpService } from '../services/http.service';
import { AuthService } from '../services/auth.service';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let formBuilder: FormBuilder;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      imports: [ReactiveFormsModule, HttpClientTestingModule], // Add HttpClientTestingModule
      providers: [HttpService, AuthService] // Add providers for HttpService and AuthService
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    formBuilder = TestBed.inject(FormBuilder);

    // Create a FormGroup with the form controls defined in your component
    component.itemForm = formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    fixture.detectChanges();
  });

  it('should have invalid form if username and password are empty', () => {
    const form = component.itemForm;
    expect(form.valid).toBeFalsy();

    const usernameControl = form.controls['username'];
    expect(usernameControl.valid).toBeFalsy();

    const passwordControl = form.controls['password'];
    expect(passwordControl.valid).toBeFalsy();
  });

  // Add more test cases as needed
  it('should have invalid form if username or password is empty', () => {
    const form = component.itemForm;
    const usernameControl = form.controls[`username`];
    const passwordControl = form.controls[`password`];

    usernameControl.setValue('testuser');
    expect(form.valid).toBeFalsy();

    usernameControl.setValue('');
    passwordControl.setValue('testpassword');
    expect(form.valid).toBeFalsy();
  });

  it('should have valid form if username and password are provided', () => {
    const form = component.itemForm;
    const usernameControl = form.controls[`username`];
    const passwordControl = form.controls[`password`];

    usernameControl.setValue('testuser');
    passwordControl.setValue('testpassword');
    expect(form.valid).toBeTruthy();
  });
});
