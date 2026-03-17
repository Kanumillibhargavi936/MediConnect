import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
 
@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss'],
})
export class RegistrationComponent implements OnInit {
 
  registrationForm!: FormGroup;
  successMessage: string | null = null;
  errorMessage: string | null = null;
  selectedRole: string | null = null;
 
  constructor(private formBuilder: FormBuilder) {}
 
  ngOnInit(): void {
    this.registrationForm = this.formBuilder.group({
      username: ['', [
        Validators.required,
        Validators.pattern(/^[a-zA-Z0-9]+$/)
      ]],
      password: ['', [
        Validators.required,
        Validators.pattern(/^(?=.*[A-Z])(?=.*\d).{8,}$/)
      ]],
      role: ['', Validators.required],
      fullName: ['', Validators.required],
      contactNumber: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      specialty: [''],
    yearsOfExperience: [''],
    dateOfBirth: [''],
    address: [''],
    });
  }

 
  onRoleChange(event: Event): void {
    this.selectedRole = (event.target as HTMLSelectElement).value;
  }
 
  onSubmit(): void {
    if (this.registrationForm.invalid) {
      this.errorMessage = 'Please fill out all fields correctly.';
      this.successMessage = null;
      return;
    }
 
    this.successMessage = 'Registration successful!';
    this.errorMessage = null;
  }
 
  resetForm(): void {
    this.registrationForm.reset();
    this.selectedRole = null;
    this.successMessage = null;
    this.errorMessage = null;
  }
}