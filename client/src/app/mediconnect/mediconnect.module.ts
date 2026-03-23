import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { PatientCreateComponent } from "./components/patientcreate/patientcreate.component";
import { ClinicCreateComponent } from "./components/cliniccreate/cliniccreate.component";
import { DoctorCreateComponent } from "./components/doctorcreate/doctorcreate.component";
import { AppointmentCreateComponent } from "./components/appointment/appointment.component";
import { MediconnectRoutingModule } from "./mediconnect-routing.module";
import { RouterModule } from "@angular/router";
import { SharedModule } from "../shared/shared.module";
import { DashboardComponent } from "./components/dashboard/dashboard.component";
import { PatientEditComponent } from "./components/patientedit/patientedit.component";
import { DoctorEditComponent } from "./components/doctoredit/doctoredit.component";
import { ClinicEditComponent } from "./components/clinicedit/clinicedit.component";

@NgModule({
  declarations: [
    PatientCreateComponent,
    DoctorCreateComponent,
    ClinicCreateComponent,
    AppointmentCreateComponent,
    DashboardComponent,
    PatientEditComponent,
    DoctorEditComponent,
    ClinicEditComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    MediconnectRoutingModule,
    RouterModule,
    SharedModule
  ],
  exports: [
  ]
})
export class MediconnectModule {}