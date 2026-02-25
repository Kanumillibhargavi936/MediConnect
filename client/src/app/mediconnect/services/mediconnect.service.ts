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

  constructor(private http: HttpClient) {}

  // Backend API calls for Patient

  addPatient(patient: Patient): Observable<Patient> {
    return this.http.post<Patient>();
  }

  updatePatient(patient: PatientDTO): Observable<Patient> {
    return this.http.put<Patient>();
  }

  deletePatient(patientId: number): Observable<any> {
    return this.http.delete<any>();
  }

  getAllPatients(): Observable<Patient[]> {
    return this.http.get<Patient[]>();
  }

  getPatientById(patientId: number): Observable<Patient> {
    return this.http.get<Patient>();
  }

  // Backend API calls for Doctor

  addDoctor(doctor: Doctor): Observable<Doctor> {
    return this.http.post<Doctor>();
  }

  updateDoctor(doctor: DoctorDTO): Observable<Doctor> {
    return this.http.put<Doctor>();
  }

  deleteDoctor(doctorId: number): Observable<any> {
    return this.http.delete<any>();
  }

  getAllDoctors(): Observable<Doctor[]> {
    return this.http.get<Doctor[]>();
  }

  getDoctorById(doctorId: number): Observable<Doctor> {
    return this.http.get<Doctor>();
  }

  // Backend API calls for Clinic

  addClinic(clinic: Clinic): Observable<Clinic> {
    return this.http.post<Clinic>();
  }

  updateClinic(clinic: Clinic): Observable<Clinic> {
    return this.http.put<Clinic>();
  }

  deleteClinic(clinicId: number): Observable<any> {
    return this.http.delete<any>();
  }

  getAllClinics(): Observable<Clinic[]> {
    return this.http.get<Clinic[]>();
  }

  getClinicById(clinicId: number): Observable<Clinic> {
    return this.http.get<Clinic>();
  }

  getClinicsByLocation(location: string): Observable<Clinic[]> {
    return this.http.get<Clinic[]>();
  }

  getClinicsByDoctorId(doctorId: number): Observable<Clinic[]> {
      return this.http.get<Clinic[]>();
    }

  // Backend API calls for Appointment

  createAppointment(appointment: Appointment): Observable<Appointment> {
    return this.http.post<Appointment>();
  }

  updateAppointment(appointment: Appointment): Observable<Appointment> {
    return this.http.put<Appointment>();
  }

  deleteAppointment(appointmentId: number): Observable<any> {
    return this.http.delete<any>();
  }

  getAllAppointments(): Observable<Appointment[]> {
    return this.http.get<Appointment[]>();
  }

  getAppointmentById(appointmentId: number): Observable<Appointment> {
    return this.http.get<Appointment>();
  }

  getAppointmentsByClinic(clinicId: number): Observable<Appointment[]> {
    return this.http.get<Appointment[]>();
  }

  getAppointmentsByPatient(patientId: number): Observable<Appointment[]> {
    return this.http.get<Appointment[]>();
  }

  getAppointmentsByStatus(status: string): Observable<Appointment[]> {
    return this.http.get<Appointment[]>();
  }

  // Backend API calls for User

  getUserById(userId: number): Observable<User> {
      return this.http.get<User>();
  }
}
