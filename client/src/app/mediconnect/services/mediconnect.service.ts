// import { Injectable } from "@angular/core";
// import { environment } from "src/environments/environment";
// import { HttpClient } from "@angular/common/http";
// import { Observable } from "rxjs";
// import { Patient } from "../models/Patient";
// import { Doctor } from "../models/Doctor";
// import { Clinic } from "../models/Clinic";
// import { Appointment } from "../models/Appointment";
// import { User } from "../models/User";
// import { DoctorDTO } from "../models/DoctorDTO";
// import { PatientDTO } from "../models/PatientDTO";

// @Injectable({
//   providedIn: "root",
// })
// export class MediConnectService {
//   private baseUrl = `${environment.apiUrl}`;

//   constructor(private http: HttpClient) { }

//   // Backend API calls for Patient

//   addPatient(patient: Patient): Observable<any> {
//     return this.http.post<Patient>(`${this.baseUrl}/patient`, patient);
//   }

//   updatePatient(patient: PatientDTO): Observable<Patient> {
//     return this.http.put<Patient>(`${this.baseUrl}/patient/${patient.patientId}`, patient);
//   }

//   deletePatient(patientId: number): Observable<any> {
//     return this.http.delete<any>(`${this.baseUrl}/patient/${patientId}`);
//   }

//   getAllPatients(): Observable<Patient[]> {
//     return this.http.get<Patient[]>(`${this.baseUrl}/patient`);
//   }

//   getPatientById(patientId: number): Observable<Patient> {
//     return this.http.get<Patient>(`${this.baseUrl}/patient/${patientId}`);
//   }

//   // Backend API calls for Doctor

//   addDoctor(doctor: Doctor): Observable<any> {
//     return this.http.post<Doctor>(`${this.baseUrl}/doctor`, doctor);
//   }

//   updateDoctor(doctor: DoctorDTO): Observable<Doctor> {
//     return this.http.put<Doctor>(`${this.baseUrl}/doctor/${doctor.doctorId}`, doctor);
//   }

//   deleteDoctor(doctorId: number): Observable<any> {
//     return this.http.delete<any>(`${this.baseUrl}/doctor/${doctorId}`);
//   }

//   getAllDoctors(): Observable<Doctor[]> {
//     return this.http.get<Doctor[]>(`${this.baseUrl}/doctor`);
//   }

//   getDoctorById(doctorId: number): Observable<Doctor> {
//     return this.http.get<Doctor>(`${this.baseUrl}/doctor/${doctorId}`);
//   }

//   // Backend API calls for Clinic

//   addClinic(clinic: Clinic): Observable<any> {
//     return this.http.post<Clinic>(`${this.baseUrl}/clinic`, clinic);
//   }

//   updateClinic(clinic: Clinic): Observable<Clinic> {
//     return this.http.put<Clinic>(`${this.baseUrl}/clinic/${clinic.clinicId}`, clinic);
//   }

//   deleteClinic(clinicId: number): Observable<any> {
//     return this.http.delete<any>(`${this.baseUrl}/clinic/${clinicId}`);
//   }

//   getAllClinics(): Observable<Clinic[]> {
//     return this.http.get<Clinic[]>(`${this.baseUrl}/clinic`);
//   }

//   getClinicById(clinicId: number): Observable<Clinic> {
//     return this.http.get<Clinic>(`${this.baseUrl}/clinic/${clinicId}`);
//   }

//   getClinicsByLocation(location: string): Observable<Clinic[]> {
//     return this.http.get<Clinic[]>(`${this.baseUrl}/clinic/location/${location}`);
//   }

//   getClinicsByDoctorId(doctorId: number): Observable<Clinic[]> {
//     return this.http.get<Clinic[]>(`${this.baseUrl}/clinic/doctor/${doctorId}`);
//   }

//   // Backend API calls for Appointment

//   createAppointment(appointment: Appointment): Observable<any> {
//     return this.http.post<Appointment>(`${this.baseUrl}/appointment`, appointment);
//   }

//   updateAppointment(appointment: Appointment): Observable<Appointment> {
//     return this.http.put<Appointment>(`${this.baseUrl}/appointment/${appointment.appointmentId}`, appointment);
//   }

//   deleteAppointment(appointmentId: number): Observable<any> {
//     return this.http.delete<any>(`${this.baseUrl}/appointment/${appointmentId}`);
//   }

//   getAllAppointments(): Observable<Appointment[]> {
//     return this.http.get<Appointment[]>(`${this.baseUrl}/appointment`);
//   }

//   getAppointmentById(appointmentId: number): Observable<Appointment> {
//     return this.http.get<Appointment>(`${this.baseUrl}/appointment/${appointmentId}`);
//   }

//   getAppointmentsByClinic(clinicId: number): Observable<Appointment[]> {
//     return this.http.get<Appointment[]>(`${this.baseUrl}/appointment/clinic/${clinicId}`);
//   }

//   getAppointmentsByPatient(patientId: number): Observable<Appointment[]> {
//     return this.http.get<Appointment[]>(`${this.baseUrl}/appointment/patient/${patientId}`);
//   }

//   getAppointmentsByStatus(status: string): Observable<Appointment[]> {
//     return this.http.get<Appointment[]>(`${this.baseUrl}/appointment/status/${status}`);
//   }

//   // Backend API calls for User

//   getUserById(userId: number): Observable<User> {
//     return this.http.get<User>(`${this.baseUrl}/user/${userId}`);
//   }
// }


import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
 
import { environment } from 'src/environments/environment';
 
import { Patient } from '../models/Patient';
import { PatientDTO } from '../models/PatientDTO';
import { Doctor } from '../models/Doctor';
import { DoctorDTO } from '../models/DoctorDTO';
import { Clinic } from '../models/Clinic';
import { Appointment } from '../models/Appointment';
import { User } from '../models/User';
 
@Injectable({
  providedIn: 'root'
})
export class MediConnectService {
  cancelAppointment(appointmentId: number) {
    throw new Error('Method not implemented.');
  }
 
  private baseUrl = environment.apiUrl;
 
  constructor(private http: HttpClient) {}
 
  // ---------------- PATIENT API -------------------
  getAllPatients(): Observable<Patient[]> {
    return this.http.get<Patient[]>(`${this.baseUrl}/patient`);
  }
 
  getPatientById(patientId: number): Observable<Patient> {
    return this.http.get<Patient>(`${this.baseUrl}/patient/${patientId}`);
  }
 
  addPatient(patient: Patient): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/patient`, patient);
  }
 
  updatePatient(patient: PatientDTO): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/patient/${patient.patientId}`, patient);
  }
 
  deletePatient(patientId: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/patient/${patientId}`);
  }
 
  getAppointmentsByPatient(patientId: number): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.baseUrl}/appointment/patient/${patientId}`);
  }
 
  // ---------------- DOCTOR API -------------------
  getDoctorById(doctorId: number): Observable<Doctor> {
    return this.http.get<Doctor>(`${this.baseUrl}/doctor/${doctorId}`);
  }
 
  getAllDoctors(): Observable<Doctor[]> {
    return this.http.get<Doctor[]>(`${this.baseUrl}/doctor`);
  }
 
  addDoctor(doctor: Doctor): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/doctor`, doctor);
  }
 
  updateDoctor(doctor: Doctor): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/doctor/${doctor.doctorId}`, doctor);
  }
 
  deleteDoctor(doctorId: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/doctor/${doctorId}`);
  }
 
  // ---------------- CLINIC API -------------------
  getAllClinics(): Observable<Clinic[]> {
    return this.http.get<Clinic[]>(`${this.baseUrl}/clinic`);
  }
 
  getClinicById(clinicId: number): Observable<Clinic> {
    return this.http.get<Clinic>(`${this.baseUrl}/clinic/${clinicId}`);
  }
 
  getClinicsByDoctorId(doctorId: number): Observable<Clinic[]> {
    return this.http.get<Clinic[]>(`${this.baseUrl}/clinic/doctor/${doctorId}`);
  }
 
  addClinic(clinic: Clinic): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/clinic`, clinic);
  }
 
  updateClinic(clinic: Clinic): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/clinic/${clinic.clinicId}`, clinic);
  }
 
  deleteClinic(clinicId: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/clinic/${clinicId}`);
  }
 
  // ---------------- APPOINTMENT API -------------------
  createAppointment(appointment: Appointment): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/appointment`, appointment);
  }
 
  getAppointmentsByClinic(clinicId: number): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.baseUrl}/appointment/clinic/${clinicId}`);
  }
 
  getAppointmentById(appointmentId: number): Observable<Appointment> {
    return this.http.get<Appointment>(`${this.baseUrl}/appointment/${appointmentId}`);
  }
 
  updateAppointment(appointment: Appointment): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/appointment/${appointment.appointmentId}`, appointment);
  }
 
  deleteAppointment(appointmentId: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/appointment/${appointmentId}`);
  }
 
  // ---------------- USER API -------------------
  getUserById(userId: number): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/user/${userId}`);
  }
}
 

