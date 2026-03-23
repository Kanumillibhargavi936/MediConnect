import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Appointment } from '../../models/Appointment';
import { Clinic } from '../../models/Clinic';
import { Patient } from '../../models/Patient';
import { MediConnectService } from '../../services/mediconnect.service';

@Component({
    selector: 'app-appointment',
    templateUrl: './appointment.component.html',
    styleUrls: ['./appointment.component.scss']
})
export class AppointmentCreateComponent implements OnInit {
    appointmentForm!: FormGroup;
    successMessage: string | null = null;
    errorMessage: string | null = null;

    clinics!: Clinic[];
    selectedPatient!: Patient;
    patientId!: number;

    constructor(private formBuilder: FormBuilder, private mediconnectService: MediConnectService) { }

    ngOnInit(): void {
        this.patientId = Number(localStorage.getItem("patient_id"));
        this.mediconnectService.getPatientById(this.patientId).subscribe({
            next: (response) => {
                this.selectedPatient = response;
            },
            error: (error) => console.log('Error loading selectedPatient', error)
        });
        this.appointmentForm = this.formBuilder.group({
            patientId: [{value: this.patientId , disabled: true}],
            clinic: ["", [Validators.required]],
            appointmentDate: ['', [Validators.required]],
            status: ['', [Validators.required]],
            purpose: ['', [Validators.required, Validators.minLength(5)]]
        });
        this.mediconnectService.getAllClinics().subscribe({
            next: (response) => {
                this.clinics = response;
            },
            error: (error) => console.log('Error loading clinics', error)
        });
    }

    onSubmit(): void {
        if (this.appointmentForm.valid) {
            const appointment: Appointment = {
                ...this.appointmentForm.getRawValue(),
                patient: this.selectedPatient,
            };
            this.mediconnectService.createAppointment(appointment).subscribe({
                next: (response) => {
                    this.errorMessage = null;
                    console.log(response);
                    this.appointmentForm.reset();
                    this.successMessage = 'Appointment created successfully!';
                },
                error: (error) => {
                    this.handleError(error);
                }
            });
        } else {
            this.errorMessage = 'Please fill out all required fields correctly.';
            this.successMessage = null;
        }
    }

    private handleError(error: HttpErrorResponse): void {
        if (error.error instanceof ErrorEvent) {
            this.errorMessage = `Client-side error: ${error.error.message}`;
        } else {
            this.errorMessage = `Server-side error: ${error.status} ${error.message}`;
            if (error.status === 400) {
                this.errorMessage = 'Bad request. Please check your input.';
            }
        }
        this.successMessage = null;
        console.error('An error occurred:', this.errorMessage);
    }
}