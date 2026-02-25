import { TestBed, ComponentFixture } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { ClinicCreateComponent } from 'src/app/mediconnect/components/cliniccreate/cliniccreate.component';
import { DashboardComponent } from 'src/app/mediconnect/components/dashboard/dashboard.component';
import { MediConnectService } from 'src/app/mediconnect/services/mediconnect.service';
import { SharedModule } from 'src/app/shared/shared.module';

const mockDoctor = {
    doctorId: 1, fullName: 'Dr. John', email: 'john@example.com', specialty: 'Cardiology', yearsOfExperience: 10, contactNumber: '9876543210',
    logAttributes() { }
};
const mockPatient = {
    patientId: 1, fullName: 'Jane', email: 'jane@example.com', dateOfBirth: new Date('2000-12-06'), address: "texas", contactNumber: '9876543210',
    logAttributes() { }
};
const mockClinics = [{ clinicId: 1, clinicName: 'Clinic A', doctor: mockDoctor, location: "texas", contactNumber: '9876543210', establishedYear: 2000 }];
const mockAppointments = [{ appointmentId: 1, appointmentDate: new Date('2024-12-06'), patient: mockPatient, clinic: mockClinics[0], status: "Scheduled", purpose: "checkup" }];
const mockResponse = { message: 'Clinic created successfully' };
const mockClinic = {
    doctor: mockDoctor,
    clinicId: 1,
    clinicName: 'Clinic A',
    location: 'City A',
    contactNumber: '1234567890',
    establishedYear: 2020,
};

describe('DashboardComponent', () => {
    let dashboardFixture: ComponentFixture<DashboardComponent>;
    let dashboardComponent: DashboardComponent;
    let mockService: MediConnectService;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [DashboardComponent],
            imports: [HttpClientTestingModule, ReactiveFormsModule, RouterTestingModule, SharedModule],
            providers: [MediConnectService],
        }).compileComponents();

        mockService = TestBed.inject(MediConnectService);

        // Setup Dashboard Component
        dashboardFixture = TestBed.createComponent(DashboardComponent);
        dashboardComponent = dashboardFixture.componentInstance;
        dashboardFixture.detectChanges();
    });

    it("should create the component", () => {
        expect(dashboardComponent).toBeTruthy();
    });

    it('should fetch doctor details on initialization', () => {

        spyOn(mockService, 'getDoctorById').and.returnValue(of(mockDoctor));

        dashboardComponent.doctorId = 1;
        dashboardComponent.loadDoctorData();

        expect(mockService.getDoctorById).toHaveBeenCalledWith(1);
        expect(dashboardComponent.doctorDetails).toEqual(mockDoctor);
    });

    it('should fetch clinics and appointments for the selected doctor', () => {
        spyOn(mockService, 'getClinicsByDoctorId').and.returnValue(of(mockClinics));
        spyOn(mockService, 'getAppointmentsByClinic').and.returnValue(of(mockAppointments));

        dashboardComponent.doctorId = 1;
        dashboardComponent.loadDoctorData();

        expect(mockService.getClinicsByDoctorId).toHaveBeenCalledWith(1);
        expect(dashboardComponent.clinics).toEqual(mockClinics);

        dashboardComponent.loadAppointments(1);
        expect(mockService.getAppointmentsByClinic).toHaveBeenCalledWith(1);
        expect(dashboardComponent.selectClinicAppointments).toEqual(mockAppointments);
    });

    it('should handle error when fetching doctor details', () => {
        spyOn(mockService, 'getDoctorById').and.returnValue(throwError({ status: 404 }));
        dashboardComponent.doctorId = 1;

        dashboardComponent.loadDoctorData();

        expect(mockService.getDoctorById).toHaveBeenCalledWith(1);
        expect(dashboardComponent.doctorDetails).toBeUndefined();
    });
});

describe('ClinicCreateComponent', () => {
    let clinicCreateFixture: ComponentFixture<ClinicCreateComponent>;
    let clinicCreateComponent: ClinicCreateComponent;
    let mockService: MediConnectService;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [ClinicCreateComponent],
            imports: [HttpClientTestingModule, ReactiveFormsModule, RouterTestingModule],
            providers: [MediConnectService],
        }).compileComponents();

        mockService = TestBed.inject(MediConnectService);

        clinicCreateFixture = TestBed.createComponent(ClinicCreateComponent);
        clinicCreateComponent = clinicCreateFixture.componentInstance;

        clinicCreateFixture.detectChanges();
    });

    it("should create the component", () => {
        expect(clinicCreateComponent).toBeTruthy();
    });

    it('should initialize the form', () => {
        spyOn(mockService, 'getDoctorById').and.returnValue(of(mockDoctor));

        clinicCreateComponent.ngOnInit();

        expect(mockService.getDoctorById).toHaveBeenCalledWith(Number(localStorage.getItem('doctor_id')));
    });

    it('should submit the form and call addClinic API', () => {
      
        clinicCreateComponent.clinicForm.patchValue(mockClinic);
        spyOn(mockService, 'addClinic').and.returnValue(of(mockClinic));
      
        clinicCreateComponent.onSubmit();
      
        expect(clinicCreateComponent.successMessage).toBe('Clinic created successfully!');
      });
      

    it('should handle error when adding a clinic', () => {
        const mockError = new HttpErrorResponse({
            error: { message: 'Bad request' },
            status: 400,
            statusText: 'Bad Request',
        });

        spyOn(mockService, 'addClinic').and.returnValue(throwError(mockError));

        clinicCreateComponent.clinicForm.patchValue({
            doctor: 'Dr. John',
            clinicId: 1,
            clinicName: 'Clinic A',
            location: 'City A',
            contactNumber: '1234567890',
            establishedYear: 2020,
        });
        clinicCreateComponent.onSubmit();

        expect(mockService.addClinic).toHaveBeenCalled();
        expect(clinicCreateComponent.errorMessage).toBe('Bad request. Please check your input.');
    });
});