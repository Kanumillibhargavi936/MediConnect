import { Clinic } from "./Clinic";
import { Patient } from "./Patient";
export class Appointment {
    appointmentId:number;
    patient:Patient;
    clinic:Clinic;
    appointmentDate:Date;
    status:string;
    purpose:string;

    constructor(appointmentId:number,patient:Patient,clinic:Clinic,appointmentDate:Date,status:string,purpose:string)
    {
        this.appointmentId=appointmentId;
        this.patient=patient;
        this.clinic=clinic;
        this.appointmentDate=appointmentDate;
        this.status=status;
        this.purpose=purpose;
    }
    // logAttributes():void{
    //     console.log("appointmentId:",this.appointmentId);
    //     console.log("patientId:",this.patient);
    //     console.log("clinicId:",this.clinic);
    //     console.log("appointmentDate:",this.appointmentDate);
    //     console.log("status:",this.status);
    //     console.log("purpose:",this.purpose);        
    // }
}