export class Appointment {
  appointmentId: number;
  patientId: number;
  clinicId: number;
  appointmentDate: Date;
  status: string;
  purpose: string;
 
  constructor(
    appointmentId: number,
    patientId: number,
    clinicId: number,
    appointmentDate: Date,
    status: string,
    purpose: string
  ) {
    this.appointmentId = appointmentId;
    this.patientId = patientId;
    this.clinicId = clinicId;
    this.appointmentDate = appointmentDate;
    this.status = status;
    this.purpose = purpose;
  }
 
  logAttributes(): void {
    console.log('appointmentId:', this.appointmentId);
    console.log('patientId:', this.patientId);
    console.log('clinicId:', this.clinicId);
    console.log('appointmentDate:', this.appointmentDate); // pass Date object
    console.log('status:', this.status);
    console.log('purpose:', this.purpose);
  }
}    