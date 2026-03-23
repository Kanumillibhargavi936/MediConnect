
export class PatientDTO {
    patientId:number;
    username:string;
    password:string;
    fullName:string;
    dateOfBirth:Date;
    contactNumber:string;
    email:string;
    address:string;

    constructor(patientId:number,username:string,password:string,fullName:string,dateOfBirth:Date,contactNumber:string,email:string,address:string)
    {
        this.patientId=patientId;
        this.username=username;
        this.password=password;
        this.fullName=fullName;
        this.dateOfBirth=dateOfBirth;
        this.contactNumber=contactNumber;
        this.email=email;
        this.address=address;
    }

    logAttributes(): void {
  console.log('patientId:', this.patientId);
  console.log('username:', this.username);
  console.log('password:', this.password);
  console.log('fullName:', this.fullName);
  console.log('dateOfBirth:', this.dateOfBirth);
  console.log('contactNumber:', this.contactNumber);
  console.log('email:', this.email);
  console.log('address:', this.address);
}
}