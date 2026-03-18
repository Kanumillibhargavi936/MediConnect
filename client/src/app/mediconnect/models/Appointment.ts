
import { Clinic } from './Clinic';
import { Patient } from './Patient';

export class Appointment {
    appointmentId: number;
    patient: Patient;
    clinic: Clinic;
    appointmentDate: Date;
    status: string;
    purpose: string;

    constructor(
        appointmentId: number,
        patient: Patient,
        clinic: Clinic,
        appointmentDate: Date,
        status: string,
        purpose: string
    ) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.clinic = clinic;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.purpose = purpose;
    }
}
