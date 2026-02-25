import { TestBed, ComponentFixture } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { DashboardComponent } from 'src/app/mediconnect/components/dashboard/dashboard.component';
import { MediConnectService } from 'src/app/mediconnect/services/mediconnect.service';
import { SharedModule } from 'src/app/shared/shared.module';
import { AppointmentCreateComponent } from 'src/app/mediconnect/components/appointment/appointment.component';
import { Router } from '@angular/router';

const mockDoctor = {
    doctorId: 1, fullName: 'Dr. John', email: 'john@example.com', specialty: 'Cardiology', yearsOfExperience: 10, contactNumber: '9876543210',
    logAttributes() { }
};
const mockPatient = {
    patientId: 1, fullName: 'Jane', email: 'jane@example.com', dateOfBirth: new Date('2000-12-06'), address: "texas", contactNumber: '9876543210',
    logAttributes() { }
};
const mockClinics = [{ clinicId: 1, clinicName: 'Clinic A', doctor: mockDoctor, location: "texas", contactNumber: '9876543210', establishedYear: 2000 },
{ clinicId: 2, clinicName: 'Clinic B', location: 'Location B', contactNumber: '9876543210', establishedYear: 2010, doctor: mockDoctor }];
const mockAppointments = [{ appointmentId: 1, appointmentDate: new Date('2024-12-06'), patient: mockPatient, clinic: mockClinics[0], status: "Scheduled", purpose: "checkup" }];


describe('DashboardComponent', () => {
    let component: DashboardComponent;
    let fixture: ComponentFixture<DashboardComponent>;
    let service: MediConnectService;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [DashboardComponent],
            imports: [HttpClientTestingModule, RouterTestingModule, SharedModule],
            providers: [MediConnectService]
        }).compileComponents();

        fixture = TestBed.createComponent(DashboardComponent);
        component = fixture.componentInstance;
        service = TestBed.inject(MediConnectService);
        fixture.detectChanges();
    });

    it('should create the component', () => {
        expect(component).toBeTruthy();
    });

    it('should fetch patient details on initialization', () => {
        spyOn(service, 'getPatientById').and.returnValue(of(mockPatient));

        component.patientId = 1;
        component.loadPatientData();

        expect(service.getPatientById).toHaveBeenCalledWith(1);
        expect(component.patientDetails).toEqual(mockPatient);
    });

    it('should fetch clinics and appointments for a patient', () => {
        spyOn(service, 'getAppointmentsByPatient').and.returnValue(of(mockAppointments));
        spyOn(service, 'getAllClinics').and.returnValue(of(mockClinics));

        component.patientId = 1;
        component.loadPatientData();

        expect(service.getAppointmentsByPatient).toHaveBeenCalledWith(1);
        expect(component.appointments).toEqual(mockAppointments);

        expect(service.getAllClinics).toHaveBeenCalled();
        expect(component.clinics).toEqual(mockClinics);
    });

    it('should handle error when fetching patient details', () => {
        spyOn(service, 'getPatientById').and.returnValue(throwError({ status: 404 }));

        component.patientId = 1;
        component.loadPatientData();

        expect(service.getPatientById).toHaveBeenCalledWith(1);
        expect(component.patientDetails).toBeUndefined();
    });

    it('should delete a patient', () => {
        spyOn(window, 'confirm').and.returnValue(true);
        spyOn(service, 'deletePatient').and.returnValue(of(null));

        component.patientId = 1;
        component.deletePatient();

        expect(service.deletePatient).toHaveBeenCalledWith(1);
    });
});


describe('AppointmentCreateComponent', () => {
    let component: AppointmentCreateComponent;
    let fixture: ComponentFixture<AppointmentCreateComponent>;
    let service: MediConnectService;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [AppointmentCreateComponent],
            imports: [ReactiveFormsModule, FormsModule, HttpClientTestingModule],
            providers: [MediConnectService]
        }).compileComponents();

        fixture = TestBed.createComponent(AppointmentCreateComponent);
        component = fixture.componentInstance;
        service = TestBed.inject(MediConnectService);
    });

    it('should create the component', () => {
        expect(component).toBeTruthy();
    });

    it('should initialize the form and fetch clinics and patient details on init', () => {
        spyOn(service, 'getPatientById').and.returnValue(of(mockPatient));
        spyOn(service, 'getAllClinics').and.returnValue(of(mockClinics));

        component.ngOnInit();

        expect(service.getPatientById).toHaveBeenCalledWith(component.patientId);
        expect(service.getAllClinics).toHaveBeenCalled();
        expect(component.selectedPatient).toEqual(mockPatient);
        expect(component.clinics).toEqual(mockClinics);
        expect(component.appointmentForm).toBeTruthy();
    });

    it('should submit the form and call createAppointment API', () => {
        spyOn(service, 'createAppointment').and.returnValue(of({ message: 'Appointment created successfully!' }));

        component.ngOnInit();
        component.selectedPatient = mockPatient;
        component.appointmentForm.patchValue({
            clinic: mockClinics[0],
            appointmentDate: '2024-12-19',
            status: 'Scheduled',
            purpose: 'Checkup'
        });

        component.onSubmit();

        expect(component.successMessage).toBe('Appointment created successfully!');
        expect(component.errorMessage).toBeNull();
    });

});
