 
import { Component } from '@angular/core';
 
@Component({
  selector: 'app-patientcreate',
  templateUrl: './patientcreate.component.html',
  styleUrls: ['./patientcreate.component.scss']
})
export class PatientCreateComponent {
  // Patient model attributes
  patient = {
    patientId: 0,
    fullName: '',
    dateOfBirth: '',
    contactNumber: '',
    email: '',
    address: ''
  };
 
  successMessage: string | null = null;
  errorMessage: string | null = null;
 
  // Handle form submission
  onSubmit(): void {
    if (this.isFormValid()) {
      this.successMessage = 'Patient has been successfully created!';
      this.errorMessage = null;
      console.log('Patient Created: ', this.patient);
      this.resetForm(); // Optional, clear the form after submission
    } else {
      this.errorMessage = 'Please fill out all required fields correctly.';
      this.successMessage = null;
    }
  }
 
  // Validate the form manually
  isFormValid(): boolean {
    const { patientId, fullName, dateOfBirth, contactNumber, email, address } = this.patient;
 
    if (
      !patientId ||
      patientId < 1 ||
      !fullName ||
      fullName.length < 2 ||
      !dateOfBirth ||
      !contactNumber ||
      !/^\d{10}$/.test(contactNumber) ||
      !email ||
      !/^\S+@\S+\.\S+$/.test(email) ||
      !address ||
      address.length < 5
    ) {
      return false;
    }
    return true;
  }
 
  // Reset the form data
  resetForm(): void {
    this.patient = {
      patientId: 0,
      fullName: '',
      dateOfBirth: '',
      contactNumber: '',
      email: '',
      address: ''
    };
  }
}