import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PatientCreateComponent } from './components/patientcreate/patientcreate.component';
import { ClinicCreateComponent } from './components/cliniccreate/cliniccreate.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { DoctorCreateComponent } from './components/doctorcreate/doctorcreate.component';
import { PatientEditComponent } from './components/patientedit/patientedit.component';
import { DoctorEditComponent } from './components/doctoredit/doctoredit.component';
import { ClinicEditComponent } from './components/clinicedit/clinicedit.component';
import { AppointmentCreateComponent } from './components/appointment/appointment.component';

const routes: Routes = [
  { path: "", component: DashboardComponent },
  { path: 'patient', component: PatientCreateComponent },
  { path: 'doctor', component: DoctorCreateComponent },
  { path: 'clinic', component: ClinicCreateComponent },
  { path: 'patient/edit/:id', component: PatientEditComponent },
  { path: 'doctor/edit/:id', component: DoctorEditComponent },
  { path: 'clinic/edit/:id', component: ClinicEditComponent },
  { path: 'appointment', component: AppointmentCreateComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MediconnectRoutingModule { }
