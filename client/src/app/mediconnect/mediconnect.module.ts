import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AppointmentCreateComponent } from './components/appointment/appointment.component';
import { ClinicCreateComponent } from './components/cliniccreate/cliniccreate.component';
import { DoctorCreateComponent } from './components/doctorcreate/doctorcreate.component';
import { PatientCreateComponent } from './components/patientcreate/patientcreate.component';
import { PatientEditComponent } from './components/patientedit/patientedit.component';
import { DoctorEditComponent } from './components/doctoredit/doctoredit.component';

import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [
    DashboardComponent,
    AppointmentCreateComponent,
    ClinicCreateComponent,
    DoctorCreateComponent,
    PatientCreateComponent,
    PatientEditComponent,
    DoctorEditComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    RouterModule,
    HttpClientModule,
    SharedModule
  ],
  providers: [DatePipe]
})
export class MediconnectModule {}