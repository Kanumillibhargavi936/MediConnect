import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-doctorarray',
    templateUrl: './doctorarray.component.html',
    styleUrls: ['./doctorarray.component.scss']
})
export class DoctorArrayComponent implements OnInit {
    doctors: any[] = [];
    showDetails: boolean = false;

    constructor() { }

    ngOnInit(): void {
        this.doctors = [
            {
                doctorId: 1,
                fullName: 'Dr. Jane Smith',
                specialty: 'Cardiology',
                contactNumber: '1234567890',
                email: 'jane.smith@example.com',
                yearsOfExperience: 15
            },
            {
                doctorId: 2,
                fullName: 'Dr. John Doe',
                specialty: 'Orthopedics',
                contactNumber: '9876543210',
                email: 'john.doe@example.com',
                yearsOfExperience: 10
            },
            {
                doctorId: 3,
                fullName: 'Dr. Alice Johnson',
                specialty: 'Pediatrics',
                contactNumber: '4567891230',
                email: 'alice.johnson@example.com',
                yearsOfExperience: 8
            }
        ];
    }

    toggleDetails(): void {
        this.showDetails = !this.showDetails;
    }
}