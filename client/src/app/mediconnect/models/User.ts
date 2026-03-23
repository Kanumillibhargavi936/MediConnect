import { Doctor } from "./Doctor";
import { Clinic } from "./Clinic";
import { Patient } from "./Patient";
export class User {
    userId: number;
    username: string;
    password: string;
    role: string;
    clinic?: Clinic;
    doctor?: Doctor;

    constructor(userId:number, username:string, password:string, role:string, doctor?:Doctor, clinic?:Clinic)
    {
        this.userId=userId;
        this.username=username;
        this.password=password;
        this.role=role;
        this.clinic=clinic;
        this.doctor=doctor;
    }

    // logAttributes():void{
    //     console.log("userId:",this.userId);
    //     console.log("username:",this.username);
    //     console.log("password:",this.password);
    //     console.log("role:",this.role);
    //     console.log("patientId",this.patientId);
    //     console.log("doctorId:",this.doctorId);   
    // }
}
