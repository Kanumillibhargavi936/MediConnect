export class Doctor {
  doctorId: number;
  fullName: string;
  contactNumber: string;
  email: string;
  specialty: string;
  yearsOfExperience: number;

  constructor(
    doctorId: number,
    fullName: string,
    contactNumber: string,
    email: string,
    specialty: string,
    yearsOfExperience: number
  ) {
    this.doctorId = doctorId;
    this.fullName = fullName;
    this.contactNumber = contactNumber;
    this.email = email;
    this.specialty = specialty;
    this.yearsOfExperience = yearsOfExperience;
  }

  logAttributes?(): void ;

}
