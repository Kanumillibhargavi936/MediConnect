import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MediConnectService } from '../../services/mediconnect.service';

@Component({
    selector: 'app-doctorcreate',
    templateUrl: './doctorcreate.component.html',
    styleUrls: ['./doctorcreate.component.scss']
})
export class DoctorCreateComponent implements OnInit {
    doctorForm!: FormGroup;
    successMessage: string | null = null;
    errorMessage: string | null = null;

    constructor(private formBuilder: FormBuilder, private mediconnectService: MediConnectService) { }

    ngOnInit(): void {
        this.doctorForm = this.formBuilder.group({
            doctorId: [null],
            fullName: ['', [Validators.required, Validators.minLength(2)]],
            specialty: ['', [Validators.required]],
            contactNumber: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
            email: ['', [Validators.required, Validators.email]],
            yearsOfExperience: [null, [Validators.required, Validators.min(1)]]
        });
    }

    onSubmit(): void {
        if (this.doctorForm.valid) {
            this.mediconnectService.addDoctor(this.doctorForm.value).subscribe({
                next: (response) => {
                    this.errorMessage = null;
                    console.log(response);
                    this.doctorForm.reset();
                    this.successMessage = 'Doctor created successfully!';
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