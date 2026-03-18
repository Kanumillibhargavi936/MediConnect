import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MediConnectService } from '../../services/mediconnect.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Patient } from '../../models/Patient';
import { User } from '../../models/User';

@Component({
  selector: 'app-patient-edit',
  templateUrl: './patientedit.component.html',
  styleUrls: ['./patientedit.component.scss']
})
export class PatientEditComponent implements OnInit {
  patientForm!: FormGroup;
  successMessage: string | null = null;
  errorMessage: string | null = null;

  patientId: number = 0;
  userId: number = 0;

  patient!: Patient;
  user!: User;

  constructor(private fb: FormBuilder, private service: MediConnectService) {}

  ngOnInit(): void {
    this.patientId = Number(localStorage.getItem('patient_id') || 0);
    this.userId = Number(localStorage.getItem('user_id') || 0);
    this.initializeForm();
    this.loadPatientDetails();
  }

  initializeForm(): void {
    this.patientForm = this.fb.group({
      fullName: ['', [Validators.required, Validators.minLength(2)]],
      username: ['', [Validators.required, Validators.pattern(/^[A-Za-z0-9]+$/)]],
      password: ['', [Validators.required, Validators.pattern(/^(?=.*[A-Z])(?=.*\d).{8,}$/)]],
      dateOfBirth: ['', [Validators.required]],
      contactNumber: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]],
      email: ['', [Validators.required, Validators.email]],
      address: ['', [Validators.required, Validators.minLength(5)]],
    });
  }

  loadPatientDetails(): void {
    if (this.patientId > 0) {
      this.service.getPatientById(this.patientId).subscribe({
        next: (p) => {
          this.patient = p;
          this.patientForm.patchValue({
            fullName: p.fullName,
            dateOfBirth: p.dateOfBirth,
            contactNumber: p.contactNumber,
            email: p.email,
            address: p.address
          });
        }
      });
    }
    if (this.userId > 0) {
      this.service.getUserById(this.userId).subscribe({
        next: (u) => {
          this.user = u;
          this.patientForm.patchValue({
            username: u.username
          });
        }
      });
    }
  }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;
    if (this.patientForm.invalid) {
      this.errorMessage = 'Please fix all errors before submitting.';
      return;
    }
    const dto = {
      patientId: this.patientId,
      username: this.patientForm.value.username,
      password: this.patientForm.value.password,
      fullName: this.patientForm.value.fullName,
      dateOfBirth: this.patientForm.value.dateOfBirth,
      contactNumber: this.patientForm.value.contactNumber,
      email: this.patientForm.value.email,
      address: this.patientForm.value.address
    };
    this.service.updatePatient(dto as any).subscribe({
      next: () => {
        this.successMessage = 'Patient updated successfully.';
      },
      error: (err: HttpErrorResponse) => this.handleError(err)
    });
  }

  private handleError(error: HttpErrorResponse): void {
    if (typeof error.error === 'string') {
      this.errorMessage = error.error;
    } else if (error.message) {
      this.errorMessage = error.message;
    } else {
      this.errorMessage = 'Failed to update patient.';
    }
  }
}