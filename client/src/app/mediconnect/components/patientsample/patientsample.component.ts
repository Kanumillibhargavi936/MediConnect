 
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Patient } from '../../models/Patient';
 
@Component({
  selector: 'app-patientsample',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './patientsample.component.html',
  styleUrls: ['./patientsample.component.scss']
})
export class PatientSampleComponent {
 
  // Use EXACT data the test expects
  patient: Patient = new Patient(
    1,                                 // patientId
    'John Doe',                        // fullName
    new Date(1990, 0, 1),              // dateOfBirth -> 1990-01-01 (months are 0-based)
    '1234567890',                      // contactNumber
    'john@example.com',                // email
    '123 Main Street, Cityville'       // address
  );
 
  // Logs via model's logAttributes() if available
  logPatientAttributes(): void {
    const anyPatient: any = this.patient as any;
    if (anyPatient && typeof anyPatient.logAttributes === 'function') {
      anyPatient.logAttributes();
    } else {
      console.log('Patient Details:', {
        patientId: this.patient.patientId,
        fullName: this.patient.fullName,
        dateOfBirth: this.patient.dateOfBirth,
        contactNumber: this.patient.contactNumber,
        email: this.patient.email,
        address: this.patient.address
      });
    }
  }
}
``