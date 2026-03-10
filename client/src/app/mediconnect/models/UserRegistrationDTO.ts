
export class UserRegistrationDTO {
  username: string;
  password: string;
  role: string;
  fullName: string;
  contactNumber: string;
  email: string;
  specialty?: string;
  yearsOfExperience?: number;
  dateOfBirth?: Date;
  address?: string;
 
  constructor(
    username: string,
    password: string,
    role: string,
    fullName: string,
    contactNumber: string,
    email: string,
    specialty?: string,
    yearsOfExperience?: number,
    dateOfBirth?: Date,
    address?: string
  ) {
    this.username = username;
    this.password = password;
    this.role = role;
    this.fullName = fullName;
    this.contactNumber = contactNumber;
    this.email = email;
    this.specialty = specialty;
    this.yearsOfExperience = yearsOfExperience;
    this.dateOfBirth = dateOfBirth;
    this.address = address;
  }
 
  logAttributes(): void {
    console.log('username:', this.username);
    console.log('password:', this.password);
    console.log('role:', this.role);
    console.log('fullName:', this.fullName);
    console.log('contactNumber:', this.contactNumber);
    console.log('email:', this.email);
    console.log('specialty:', this.specialty);
    console.log('yearsOfExperience:', this.yearsOfExperience);
    console.log('dateOfBirth:', this.dateOfBirth);
    console.log('address:', this.address);
  }
}