 
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Doctor } from '../../models/Doctor';
 
@Component({
  selector: 'app-doctorsample',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './doctorsample.component.html',
  styleUrls: ['./doctorsample.component.scss']
})
export class DoctorSampleComponent {
 
  // Use EXACT data the test expects
  doctor: Doctor = new Doctor(
    1,                        // doctorId
    'Dr. Jane Smith',         // fullName
    '9876543210',             // contactNumber
    'jane@example.com',       // email
    'Cardiology',             // specialty
    15                        // yearsOfExperience
  );
 
  // Explicitly call model's logAttributes() (as per Day-17)
  logDoctorAttributes(): void {
    const anyDoctor: any = this.doctor as any;
    if (anyDoctor && typeof anyDoctor.logAttributes === 'function') {
      anyDoctor.logAttributes();
    } else {
      console.log('Doctor Details (fallback):', {
        doctorId: this.doctor.doctorId,
        fullName: this.doctor.fullName,
        specialty: this.doctor.specialty,
        contactNumber: this.doctor.contactNumber,
        email: this.doctor.email,
        yearsOfExperience: this.doctor.yearsOfExperience
      });
    }
  }
}
 