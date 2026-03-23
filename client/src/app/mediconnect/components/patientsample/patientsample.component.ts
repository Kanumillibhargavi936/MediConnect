import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Patient } from 'src/app/mediconnect/models/Patient';

@Component({
    selector: 'app-patientsample',
    standalone: true,
    templateUrl: './patientsample.component.html',
    styleUrls: ['./patientsample.component.scss'],
    imports: [CommonModule]
})
export class PatientSampleComponent {
    patient: Patient = new Patient(
        1,
        'John Doe',
        new Date('1990-01-01'),
        '1234567890',
        'john@example.com',
        '123 Main Street, Cityville'
    );

    logPatientAttributes() {
        this.patient.logAttributes();
    }
}
