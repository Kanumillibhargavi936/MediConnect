import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Patient } from "../models/Patient";
import { Doctor } from "../models/Doctor";
import { Clinic } from "../models/Clinic";
import { Appointment } from "../models/Appointment";
import { User } from "../models/User";
import { DoctorDTO } from "../models/DoctorDTO";
import { PatientDTO } from "../models/PatientDTO";

@Injectable({
  providedIn: "root",
})
export class MediConnectService {
  private baseUrl = `${environment.apiUrl}`;

  constructor(private http: HttpClient) { }



  addPatient(patient: Patient): Observable<any> {
    return this.http.post<Patient>(`${this.baseUrl}/patient`, patient);
  }

  updatePatient(patient: PatientDTO): Observable<Patient> {
    return this.http.put<Patient>(`${this.baseUrl}/patient/${patient.patientId}`, patient);
  }

  deletePatient(patientId: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/patient/${patientId}`);
  }

  getAllPatients(): Observable<Patient[]> {
    return this.http.get<Patient[]>(`${this.baseUrl}/patient`);
  }

  getPatientById(patientId: number): Observable<Patient> {
    return this.http.get<Patient>(`${this.baseUrl}/patient/${patientId}`);
  }



  addDoctor(doctor: Doctor): Observable<any> {
    return this.http.post<Doctor>(`${this.baseUrl}/doctor`, doctor);
  }

  updateDoctor(doctor: DoctorDTO): Observable<any> {
    return this.http.put<Doctor>(`${this.baseUrl}/doctor/${doctor.doctorId}`, doctor);
  }

  deleteDoctor(doctorId: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/doctor/${doctorId}`);
  }

  getAllDoctors(): Observable<Doctor[]> {
    return this.http.get<Doctor[]>(`${this.baseUrl}/doctor`);
  }

  getDoctorById(doctorId: number): Observable<Doctor> {
    return this.http.get<Doctor>(`${this.baseUrl}/doctor/${doctorId}`);
  }



  addClinic(clinic: Clinic): Observable<any> {
    return this.http.post<Clinic>(`${this.baseUrl}/clinic`, clinic);
  }

  updateClinic(clinic: Clinic): Observable<Clinic> {
    return this.http.put<Clinic>(`${this.baseUrl}/clinic/${clinic.clinicId}`, clinic);
  }

  deleteClinic(clinicId: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/clinic/${clinicId}`);
  }

  getAllClinics(): Observable<Clinic[]> {
    return this.http.get<Clinic[]>(`${this.baseUrl}/clinic`);
  }

  getClinicById(clinicId: number): Observable<Clinic> {
    return this.http.get<Clinic>(`${this.baseUrl}/clinic/${clinicId}`);
  }

  getClinicsByLocation(location: string): Observable<Clinic[]> {
    return this.http.get<Clinic[]>(`${this.baseUrl}/clinic/location/${location}`);
  }

  getClinicsByDoctorId(doctorId: number): Observable<Clinic[]> {
    return this.http.get<Clinic[]>(`${this.baseUrl}/clinic/doctor/${doctorId}`);
  }



  createAppointment(appointment: Appointment): Observable<any> {
    return this.http.post<Appointment>(`${this.baseUrl}/appointment`, appointment);
  }

  updateAppointment(appointment: Appointment): Observable<any> {
    return this.http.put<Appointment>(`${this.baseUrl}/appointment/${appointment.appointmentId}`, appointment);
  }

  deleteAppointment(appointmentId: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/appointment/${appointmentId}`);
  }

  getAllAppointments(): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.baseUrl}/appointment`);
  }

  getAppointmentById(appointmentId: number): Observable<Appointment> {
    return this.http.get<Appointment>(`${this.baseUrl}/appointment/${appointmentId}`);
  }

  getAppointmentsByClinic(clinicId: number): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.baseUrl}/appointment/clinic/${clinicId}`);
  }

  getAppointmentsByPatient(patientId: number): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.baseUrl}/appointment/patient/${patientId}`);
  }

  getAppointmentsByStatus(status: string): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.baseUrl}/appointment/status/${status}`);
  }

  

  getUserById(userId: number): Observable<any> {
    return this.http.get<User>(`${this.baseUrl}/user/${userId}`);
  }
}