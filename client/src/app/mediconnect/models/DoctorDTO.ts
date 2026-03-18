export class DoctorDTO {
    doctorId: number;
    username: string;
    password: string;
    fullName: string;
    contactNumber: string;
    email: string;
    specialty: string;
    yearsOfExperience: number;
  
    constructor(
      doctorId: number,
      username: string,
      password: string,
      fullName: string,
      contactNumber: string,
      email: string,
      specialty: string,
      yearsOfExperience: number
    ) {
      this.doctorId = doctorId;
      this.username = username;
      this.password = password;
      this.fullName = fullName;
      this.contactNumber = contactNumber;
      this.email = email;
      this.specialty = specialty;
      this.yearsOfExperience = yearsOfExperience;
    }
  
    logAttributes?(): void {}
  }
