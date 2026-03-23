import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Patient } from '../../models/Patient';
import { PatientDTO } from '../../models/PatientDTO';
import { User } from '../../models/User';
import { MediConnectService } from '../../services/mediconnect.service';

@Component({
    selector: 'app-patientedit',
    templateUrl: './patientedit.component.html',
    styleUrls: ['./patientedit.component.scss']
})
export class PatientEditComponent {
    patientForm!: FormGroup;
    successMessage: string | null = null;
    errorMessage: string | null = null;

    patientId!: number;
    userId!: number;
    patient!: Patient;
    user!: User;

    constructor(
        private formBuilder: FormBuilder,
        private mediconnectService: MediConnectService,
        private route: ActivatedRoute
    ) { }

    ngOnInit(): void {
        this.initializeForm();
    }

    initializeForm(): void {
        this.userId = Number(localStorage.getItem("user_id"));
        this.patientForm = this.formBuilder.group({
            fullName: ['', [Validators.required, Validators.minLength(2)]],
            dateOfBirth: ['', [Validators.required]],
            contactNumber: [
                '',
                [Validators.required, Validators.pattern('^[0-9]{10}$')]
            ],
            username: ['', [Validators.required, Validators.pattern(/^[a-zA-Z0-9]+$/)]],
            password: ['', [Validators.required, Validators.minLength(8), Validators.pattern(/^(?=.*[A-Z])(?=.*\d).+$/)]],
            email: [{ value: '', disabled: true }],
            address: ['', [Validators.required, Validators.minLength(5)]]
        });
        this.patientId = Number(this.route.snapshot.paramMap.get('id'));
        this.loadPatientDetails();
    }

    loadPatientDetails(): void {
        this.mediconnectService.getPatientById(this.patientId).subscribe({
            next: (response) => {
                this.patient = response;
                const formattedDate = new Date(response.dateOfBirth).toISOString().split('T')[0];
                this.patientForm.patchValue({
                    fullName: this.patient.fullName,
                    dateOfBirth: formattedDate,
                    contactNumber: this.patient.contactNumber,
                    email: this.patient.email,
                    address: this.patient.address
                });
            },
            error: (error) => console.error('Error loading patient details:', error)
        });
        this.mediconnectService.getUserById(this.userId).subscribe({
            next: (response) => {
                this.user = response;
                this.patientForm.patchValue({
                    username: this.user.username,
                    password: this.user.password
                });
            },
            error: (error) => console.error('Error loading user details:', error)
        });
    }

    onSubmit(): void {
        if (this.patientForm.valid) {
            const patient: PatientDTO = {
                ...this.patientForm.getRawValue(),
                patientId: this.patientId,
            };
            this.mediconnectService.updatePatient(patient).subscribe({
                next: (response) => {
                    this.errorMessage = null;
                    console.log(response);
                    this.patientForm.reset();
                    this.successMessage = 'Patient updated successfully!';
                },
                error: (error) => {
                    this.handleError(error);
                }
            });
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
    }
}
