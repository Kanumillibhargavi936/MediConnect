 
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { PatientCreateComponent } from "./components/patientcreate/patientcreate.component";
import { ClinicCreateComponent } from "./components/cliniccreate/cliniccreate.component";
import { DoctorCreateComponent } from "./components/doctorcreate/doctorcreate.component";
import { AppointmentComponent } from "./components/appointment/appointment.component";
 
@NgModule({
  declarations: [
    PatientCreateComponent,
    DoctorCreateComponent,
    ClinicCreateComponent,
    AppointmentComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule
  ],
  exports: [
  ]
})
export class MediconnectModule {}