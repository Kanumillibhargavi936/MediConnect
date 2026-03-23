import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MediConnectService } from '../../services/mediconnect.service';

@Component({
    selector: 'app-patientcreate',
    templateUrl: './patientcreate.component.html',
    styleUrls: ['./patientcreate.component.scss']
})
export class PatientCreateComponent implements OnInit {
    patientForm!: FormGroup; 
    successMessage: string | null = null;
    errorMessage: string | null = null;

    constructor(private formBuilder: FormBuilder, private mediconnectService: MediConnectService) { }

    ngOnInit(): void {
        this.initializeForm();
    }

    initializeForm(): void {
        this.patientForm = this.formBuilder.group({
            patientId: [null],
            fullName: ['', [Validators.required, Validators.minLength(2)]],
            dateOfBirth: ['', [Validators.required]],
            contactNumber: [
                '',
                [Validators.required, Validators.pattern('^[0-9]{10}$')]
            ],
            email: ['', [Validators.required, Validators.email]],
            address: ['', [Validators.required, Validators.minLength(5)]]
        });
    }

    onSubmit(): void {
        if (this.patientForm.valid) {
            this.mediconnectService.addPatient(this.patientForm.value).subscribe({
                next: (response) => {
                    this.errorMessage = null;
                    console.log(response);
                    this.patientForm.reset();
                },
                error: (error) => {
                    this.handleError(error);
                },
                complete: () => {
                    this.successMessage = 'Patient created successfully!';
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