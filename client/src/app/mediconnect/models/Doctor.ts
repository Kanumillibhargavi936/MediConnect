import e from "cors";

export class Doctor {
    doctorId:number;
    fullName:string;
    contactNumber:string;
    email:string;
    specialty:string;
    yearsOfExperience:number;

    constructor(doctorId:number,fullName:string,contactNumber:string,email:string,specialty:string,yearsOfExperience:number)
    {
        this.doctorId=doctorId;
        this.fullName=fullName;
        this.contactNumber=contactNumber;
        this.email=email;
        this.specialty=specialty;
        this.yearsOfExperience=yearsOfExperience;
    }

    logAttributes(): void {
  console.log('doctorId:', this.doctorId);
  console.log('fullName:', this.fullName);
  console.log('contactNumber:', this.contactNumber);
  console.log('email:', this.email);
  console.log('specialty:', this.specialty);
  console.log('yearsOfExperience:', this.yearsOfExperience);
}
}