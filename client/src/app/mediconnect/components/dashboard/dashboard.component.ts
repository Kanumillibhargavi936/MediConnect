import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Appointment } from '../../models/Appointment';
import { Clinic } from '../../models/Clinic';
import { Doctor } from '../../models/Doctor';
import { Patient } from '../../models/Patient';
import { MediConnectService } from '../../services/mediconnect.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  doctorDetails?: Doctor;
  patientDetails?: Patient;

  doctors: Doctor[] = [];
  clinics: Clinic[] = [];
  patients: Patient[] = [];
  appointments: Appointment[] = [];

  role: string | null = null;
  userId: number = 0;
  doctorId: number = 0;
  patientId: number = 0;

  selectedClinicId: number | undefined;
  selectClinicAppointments: Appointment[] = [];

  errorMessage: string | null = null;
  successMessage: string | null = null;

  constructor(
    private mediconnectService: MediConnectService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.role = localStorage.getItem('role');
    this.userId = Number(localStorage.getItem('user_id') || 0);
    this.doctorId = Number(localStorage.getItem('doctor_id') || 0);
    this.patientId = Number(localStorage.getItem('patient_id') || 0);

    if (this.doctorId > 0 && this.role === 'DOCTOR') {
      this.loadDoctorData();
    }
    if (this.patientId > 0 && this.role !== 'DOCTOR') {
      this.loadPatientData();
    }
  }

  // ------------- DOCTOR VIEW -------------

  loadDoctorData(): void {
    this.errorMessage = null;

    this.mediconnectService.getDoctorById(this.doctorId).subscribe({
      next: (d: Doctor) => (this.doctorDetails = d),
      error: () => (this.errorMessage = 'Failed to fetch doctor details'),
    });

    this.mediconnectService.getClinicsByDoctorId(this.doctorId).subscribe({
      next: (c: Clinic[]) => (this.clinics = c),
      error: () => (this.errorMessage = 'Failed to fetch clinics'),
    });

    this.mediconnectService.getAllPatients().subscribe({
      next: (p: Patient[]) => (this.patients = p),
      error: () => (this.errorMessage = 'Failed to fetch patients'),
    });
  }

  onClinicSelect(clinic: Clinic): void {
    this.selectedClinicId = clinic.clinicId;
    this.loadAppointments(this.selectedClinicId);
  }

  loadAppointments(clinicId: number): void {
    this.errorMessage = null;
    this.mediconnectService.getAppointmentsByClinic(clinicId).subscribe({
      next: (a: Appointment[]) => {
        this.selectClinicAppointments = a;
        this.appointments = a;
      },
      error: () => (this.errorMessage = 'Failed to fetch appointments'),
    });
  }

  // Called by template button: <button (click)="deleteDoctor()">Delete</button>
  deleteDoctor(): void {
    if (!this.doctorId) { return; }
    this.successMessage = null;
    this.errorMessage = null;

    this.mediconnectService.deleteDoctor(this.doctorId).subscribe({
      next: () => {
        this.successMessage = 'Doctor deleted successfully.';
        // clear doctor UI data
        this.doctorDetails = undefined;
        this.clinics = [];
        this.selectClinicAppointments = [];
        this.appointments = [];
      },
      error: () => (this.errorMessage = 'Failed to delete doctor')
    });
  }

  // Called by template button: (click)="deleteClinic(clinic.clinicId)"
  deleteClinic(clinicId: number): void {
    if (!clinicId) { return; }
    this.successMessage = null;
    this.errorMessage = null;

    this.mediconnectService.deleteClinic(clinicId).subscribe({
      next: () => {
        this.successMessage = 'Clinic deleted successfully.';
        // Remove from local list
        this.clinics = this.clinics.filter(c => c.clinicId !== clinicId);
        // If the deleted clinic was selected, clear selection & appointments
        if (this.selectedClinicId === clinicId) {
          this.selectedClinicId = undefined;
          this.selectClinicAppointments = [];
          this.appointments = [];
        }
      },
      error: () => (this.errorMessage = 'Failed to delete clinic')
    });
  }

  // Called by template: (click)="cancelAppointment(appointment)"
  cancelAppointment(appointment: Appointment): void {
    if (!appointment) { return; }
    this.successMessage = null;
    this.errorMessage = null;

    // Update appointment status to match Day-10 spec
    const updated: Appointment = { ...appointment, status: 'Canceled' } as Appointment;

    this.mediconnectService.updateAppointment(updated as any).subscribe({
      next: () => {
        this.successMessage = 'Appointment canceled.';
        // update local collections
        this.selectClinicAppointments = this.selectClinicAppointments.map(a =>
          a.appointmentId === updated.appointmentId ? updated : a
        );
        this.appointments = this.appointments.map(a =>
          a.appointmentId === updated.appointmentId ? updated : a
        );
      },
      error: () => (this.errorMessage = 'Failed to cancel appointment')
    });
  }

  navigateToEditDoctor(): void {
    // If you have a route like /mediconnect/doctor/edit/:doctorId
    // this.router.navigate(['/mediconnect/doctor/edit', this.doctorId]);
  }

  navigateToEditClinic(id: number): void {
    // If you have a route like /mediconnect/clinic/edit/:clinicId
    // this.router.navigate(['/mediconnect/clinic/edit', id]);
  }

  // ------------- PATIENT VIEW -------------

  loadPatientData(): void {
    this.errorMessage = null;

    this.mediconnectService.getPatientById(this.patientId).subscribe({
      next: (p: Patient) => (this.patientDetails = p),
      error: () => {
        this.patientDetails = undefined;
        this.errorMessage = 'Failed to fetch patient details';
      },
    });

    this.mediconnectService.getAllClinics().subscribe({
      next: (c: Clinic[]) => (this.clinics = c),
      error: () => (this.errorMessage = 'Failed to fetch clinics'),
    });

    this.mediconnectService.getAllDoctors().subscribe({
      next: (d: Doctor[]) => (this.doctors = d),
      error: () => (this.errorMessage = 'Failed to fetch doctors'),
    });

    this.mediconnectService.getAppointmentsByPatient(this.patientId).subscribe({
      next: (a: Appointment[]) => { this.appointments = a; },
      error: () => (this.errorMessage = 'Failed to fetch appointments'),
    });
  }

  // Called by template button: <button (click)="navigateToEditPatient()">Edit</button>
  navigateToEditPatient(): void {
    // If you have a route like /mediconnect/patient/edit
    this.router.navigate(['/mediconnect/patient/edit']);
  }

  // Called by template button: <button (click)="deletePatient()">Delete</button>
  deletePatient(): void {
    if (!this.patientId) { return; }
    this.successMessage = null;
    this.errorMessage = null;

    this.mediconnectService.deletePatient(this.patientId).subscribe({
      next: () => {
        this.successMessage = 'Patient deleted successfully.';
        this.patientDetails = undefined;
        this.appointments = [];
      },
      error: () => (this.errorMessage = 'Failed to delete patient')
    });
  }
}