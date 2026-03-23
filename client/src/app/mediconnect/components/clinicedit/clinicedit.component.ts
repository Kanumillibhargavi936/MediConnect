import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Clinic } from '../../models/Clinic';
import { Doctor } from '../../models/Doctor';
import { MediConnectService } from '../../services/mediconnect.service';

@Component({
    selector: 'app-clinicedit',
    templateUrl: './clinicedit.component.html',
    styleUrls: ['./clinicedit.component.scss']
})
export class ClinicEditComponent  {
    clinicForm!: FormGroup;
    successMessage: string | null = null;
    errorMessage: string | null = null;

    clinicId!: number;
    clinic!: Clinic;
    doctorId!: number;
    doctor!: Doctor;

    constructor(
        private formBuilder: FormBuilder,
        private mediconnectService: MediConnectService,
        private route: ActivatedRoute
    ) { }
    
    ngOnInit(): void {
        this.doctorId = Number(localStorage.getItem("doctor_id"));
        this.clinicId = Number(this.route.snapshot.paramMap.get('id'));
        this.initializeForm();
        this.loadClinicDetails();
    }
    
    initializeForm(): void {
        this.clinicForm = this.formBuilder.group({
            doctor: [{ value: '', disabled: true }],
            clinicName: ['', [Validators.required, Validators.minLength(2)]],
            location: ['', [Validators.required]],
            contactNumber: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
            establishedYear: [null, [Validators.required, Validators.min(1900), Validators.max(new Date().getFullYear())]],
        });
    }

    loadClinicDetails(): void {
        this.mediconnectService.getDoctorById(this.doctorId).subscribe({
            next: (response) => {
                this.doctor = response;
            },
            error: (error) => console.log('Error loading loggedIn doctor details', error)
        });
        this.mediconnectService.getClinicById(this.clinicId).subscribe({
            next: (response) => {
                this.clinic = response;
                this.clinicForm.patchValue({ 
                    doctor: this.doctor.fullName,
                    clinicName: this.clinic.clinicName,
                    location: this.clinic.location,
                    contactNumber: this.clinic.contactNumber,
                    establishedYear: this.clinic.establishedYear
                 });
            },
            error: (error) => console.log('Error loading clinic details', error),
        });
    }

    onSubmit(): void {
        if (this.clinicForm.valid) {
            const clinic: Clinic = {
                ...this.clinicForm.getRawValue(),
                clinicId: this.clinic.clinicId,
                doctor: this.doctor
            };
            this.mediconnectService.updateClinic(clinic).subscribe({
                next: (response) => {
                    this.errorMessage = null;
                    console.log(response);
                    this.clinicForm.reset();
                    this.successMessage = 'Clinic updated successfully!';
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