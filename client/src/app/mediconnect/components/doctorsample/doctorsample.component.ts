import { Component } from '@angular/core';
import { Doctor } from 'src/app/mediconnect/models/Doctor';

@Component({
    selector: 'app-doctorsample',
    standalone: true,
    templateUrl: './doctorsample.component.html',
    styleUrls: ['./doctorsample.component.scss']
})
export class DoctorSampleComponent {
    doctor: Doctor = new Doctor(
        1,
        'Dr. Jane Smith',
        '9876543210',
        'jane@example.com',
        'Cardiology',
        15
    );

    logDoctorAttributes() {
        this.doctor.logAttributes();
    }
}
