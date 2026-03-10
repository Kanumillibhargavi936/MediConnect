export class Patient {
  
patientId: number;
  fullName: string;
  dateOfBirth: Date;
  contactNumber: string;
  email: string;
  address: string;
 
  constructor(
    patientId: number,
    fullName: string,
    dateOfBirth: Date,
    contactNumber: string,
    email: string,
    address: string
  ) {
    this.patientId = patientId;
    this.fullName = fullName;
    this.dateOfBirth = dateOfBirth;
    this.contactNumber = contactNumber;
    this.email = email;
    this.address = address;
  }
 
  logAttributes(): void {
    console.log('patientId:', this.patientId);
    console.log('fullName:', this.fullName);
    console.log('dateOfBirth:', this.dateOfBirth); // pass Date object
    console.log('contactNumber:', this.contactNumber);
    console.log('email:', this.email);
    console.log('address:', this.address);
  }
   
}





