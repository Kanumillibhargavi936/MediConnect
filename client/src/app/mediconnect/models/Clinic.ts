import { Doctor } from "./Doctor";
export class Clinic {
    clinicId: number;
    clinicName: string;
    location: string;
    doctor: Doctor;
    contactNumber: string;
    establishedYear: number;

    constructor(clinicId: number,clinicName: string,location: string,doctor: Doctor,contactNumber: string,establishedYear: number)
    {
        this.clinicId=clinicId;
        this.clinicName=clinicName;
        this.location=location;
        this.doctor=doctor;
        this.contactNumber=contactNumber;
        this.establishedYear=establishedYear;
    }

    // logAttributes():void{
    //     console.log("clinicId:",this.clinicId);
    //     console.log("clinicName:",this.clinicName);
    //     console.log("location:",this.location);
    //     console.log("doctorId:",this.doctor);
    //     console.log("contactNumber:",this.contactNumber);
    //     console.log("establishedYear:",this.establishedYear);
        
        
        
        
        
        
    // }

}