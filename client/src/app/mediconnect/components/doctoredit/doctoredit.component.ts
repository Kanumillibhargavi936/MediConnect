import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Doctor } from '../../models/Doctor';
import { DoctorDTO } from '../../models/DoctorDTO';
import { User } from '../../models/User';
import { MediConnectService } from '../../services/mediconnect.service';

@Component({
    selector: 'app-doctoredit',
    templateUrl: './doctoredit.component.html',
    styleUrls: ['./doctoredit.component.scss']
})
export class DoctorEditComponent implements OnInit {
    doctorForm!: FormGroup;
    successMessage: string | null = null;
    errorMessage: string | null = null;

    doctorId!: number;
    userId!: number;
    doctor!: Doctor;
    user!: User;

    constructor(
        private formBuilder: FormBuilder,
        private mediconnectService: MediConnectService,
        private route: ActivatedRoute
    ) { }

    ngOnInit(): void {
        this.userId = Number(localStorage.getItem("user_id"));
        this.initializeForm();
        this.doctorId = Number(this.route.snapshot.paramMap.get('id'));
        this.loadDoctorDetails();
    }

    initializeForm(): void {
        this.doctorForm = this.formBuilder.group({
            fullName: ['', [Validators.required, Validators.minLength(2)]],
            username: ['', [Validators.required, Validators.pattern(/^[a-zA-Z0-9]+$/)]],
            password: ['', [Validators.required, Validators.minLength(8), Validators.pattern(/^(?=.*[A-Z])(?=.*\d).+$/)]],
            specialty: ['', [Validators.required]],
            contactNumber: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
            email: [{ value: '', disabled: true }],
            yearsOfExperience: [null, [Validators.required, Validators.min(1)]]
        });
    }

    loadDoctorDetails(): void {
        this.mediconnectService.getDoctorById(this.doctorId).subscribe({
            next: (response) => {
                this.doctor = response;
                this.doctorForm.patchValue({
                    fullName: this.doctor.fullName,
                    contactNumber: this.doctor.contactNumber,
                    email: this.doctor.email,
                    specialty: this.doctor.specialty,
                    yearsOfExperience: this.doctor.yearsOfExperience
                });
            },
            error: (error) => console.error('Error loading doctor details:', error)
        });
        this.mediconnectService.getUserById(this.userId).subscribe({
            next: (response) => {
                this.user = response;
                this.doctorForm.patchValue({
                    username: this.user.username,
                    password: this.user.password
                });
            },
            error: (error) => console.error('Error loading user details:', error)
        });
    }

    onSubmit(): void {
        if (this.doctorForm.valid) {
            const doctor: DoctorDTO = {
                ...this.doctorForm.getRawValue(),
                doctorId: this.doctorId,
            };
            this.mediconnectService.updateDoctor(doctor).subscribe({
                next: (response) => {
                    this.errorMessage = null;
                    console.log(response);
                    this.doctorForm.reset();
                    this.successMessage = 'Doctor updated successfully!';
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
        console.error('An error occurred:', this.errorMessage);
    }
}