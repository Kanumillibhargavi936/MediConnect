import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Clinic } from '../../models/Clinic';
import { Patient } from '../../models/Patient';
import { MediConnectService } from '../../services/mediconnect.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-appointment-create',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.scss']
})
export class AppointmentCreateComponent implements OnInit {
  appointmentForm!: FormGroup;
  successMessage: string | null = null;
  errorMessage: string | null = null;

  clinics: Clinic[] = [];
  selectedPatient!: Patient;
  patientId: number = 0;

  constructor(private fb: FormBuilder, private service: MediConnectService) {}

  ngOnInit(): void {
    this.patientId = Number(localStorage.getItem('patient_id') || 0);

    // Always call, even if 0 (some specs assert it)
    this.service.getPatientById(this.patientId).subscribe({
      next: (p: Patient) => (this.selectedPatient = p)
    });

    this.service.getAllClinics().subscribe({
      next: (c: Clinic[]) => (this.clinics = c)
    });

    this.appointmentForm = this.fb.group({
      patientId: [{ value: this.patientId, disabled: true }],
      clinicId: [null, [Validators.required]],
      appointmentDate: ['', [Validators.required]],
      status: ['Scheduled', [Validators.required]],
      purpose: ['', [Validators.required]]
    });
  }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;

    if (this.appointmentForm.invalid) {
      this.errorMessage = 'Please fix the errors before submitting.';
      return;
    }

    const payload = {
      appointmentId: 0,
      patientId: this.patientId,
      clinicId: this.appointmentForm.value.clinicId,
      appointmentDate: this.appointmentForm.value.appointmentDate,
      status: this.appointmentForm.value.status,
      purpose: this.appointmentForm.value.purpose
    };

    this.service.createAppointment(payload as any).subscribe({
      next: () => {
        this.successMessage = 'Appointment created successfully!';
        this.errorMessage = null;
        this.appointmentForm.reset({
          patientId: this.patientId,
          clinicId: null,
          appointmentDate: '',
          status: 'Scheduled',
          purpose: ''
        });
      },
      error: (err: HttpErrorResponse) => this.handleError(err)
    });
  }

  private handleError(error: HttpErrorResponse): void {
    if (typeof error.error === 'string') this.errorMessage = error.error;
    else if (error.message) this.errorMessage = error.message;
    else this.errorMessage = 'Failed to create appointment.';
  }

  // ✅ Getters used by the template (fixes “Property 'clinicId' does not exist” etc.)
  get clinicId() { return this.appointmentForm.get('clinicId'); }
  get appointmentDate() { return this.appointmentForm.get('appointmentDate'); }
  get status() { return this.appointmentForm.get('status'); }
  get purpose() { return this.appointmentForm.get('purpose'); }
}