import { TestBed, ComponentFixture } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { DashboardComponent } from 'src/app/mediconnect/components/dashboard/dashboard.component';
import { MediConnectService } from 'src/app/mediconnect/services/mediconnect.service';
import { SharedModule } from 'src/app/shared/shared.module';
import { DoctorEditComponent } from 'src/app/mediconnect/components/doctoredit/doctoredit.component';
import { ActivatedRoute, Router } from '@angular/router';
import { DoctorDTO } from 'src/app/mediconnect/models/DoctorDTO';
import { User } from 'src/app/mediconnect/models/User';
import { Doctor } from 'src/app/mediconnect/models/Doctor';

const mockDoctor: Doctor = {
    doctorId: 1,
    fullName: 'Dr. John',
    specialty: 'Cardiology',
    contactNumber: '9876543210',
    email: 'dr.john@example.com',
    yearsOfExperience: 10,
    logAttributes: function (): void {
        throw new Error('Function not implemented.');
    }
};

const mockUser: User = {
    userId: 1,
    username: 'doctorjohn',
    password: 'Password@123',
    role: 'DOCTOR',
    doctor: mockDoctor
};

const mockPatient = {
    patientId: 1, fullName: 'Jane', email: 'jane@example.com', dateOfBirth: new Date('2000-12-06'), address: "texas", contactNumber: '9876543210',
    logAttributes() { }
};
const mockClinics = [{ clinicId: 1, clinicName: 'Clinic A', doctor: mockDoctor, location: "texas", contactNumber: '9876543210', establishedYear: 2000 },
{ clinicId: 2, clinicName: 'Clinic B', location: 'Location B', contactNumber: '9876543210', establishedYear: 2010, doctor: mockDoctor }];
const mockAppointments = [{ appointmentId: 1, appointmentDate: new Date('2024-12-06'), patient: mockPatient, clinic: mockClinics[0], status: "Scheduled", purpose: "checkup" }];


describe('DoctorEditComponent', () => {
    let component: DoctorEditComponent;
    let fixture: ComponentFixture<DoctorEditComponent>;
    let service: MediConnectService;
    let route: ActivatedRoute;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [DoctorEditComponent],
            imports: [HttpClientTestingModule, ReactiveFormsModule, FormsModule],
            providers: [
                MediConnectService,
                {
                    provide: ActivatedRoute,
                    useValue: {
                        snapshot: { paramMap: { get: () => '1' } },
                    },
                },
            ],
        }).compileComponents();

        service = TestBed.inject(MediConnectService);
        route = TestBed.inject(ActivatedRoute);
        fixture = TestBed.createComponent(DoctorEditComponent);
        component = fixture.componentInstance;

        // Mock localStorage
        spyOn(localStorage, 'getItem').and.callFake((key: string) => {
            if (key === 'user_id') return '1';
            return null;
        });
    });

    it('should create the component', () => {
        expect(component).toBeTruthy();
    });

    it('should initialize the form and load doctor details', () => {
        spyOn(service, 'getDoctorById').and.returnValue(of(mockDoctor));
        spyOn(service, 'getUserById').and.returnValue(of(mockUser));

        component.ngOnInit();

        expect(service.getDoctorById).toHaveBeenCalledWith(1);
        expect(service.getUserById).toHaveBeenCalledWith(1);

        expect(component.doctorForm.getRawValue()).toEqual({
            fullName: 'Dr. John',
            username: 'doctorjohn',
            password: 'Password@123',
            specialty: 'Cardiology',
            contactNumber: '9876543210',
            email: 'dr.john@example.com',
            yearsOfExperience: 10,
        });
    });

    it('should submit valid form and call updateDoctor API', () => {
        spyOn(service, 'updateDoctor').and.returnValue(of({ message: 'Doctor updated successfully!' }));

        component.ngOnInit();
        component.doctorForm.patchValue({
            fullName: 'Dr. John Updated',
            username: 'doctorjohn',
            password: 'UpdatedPassword@123',
            specialty: 'Neurology',
            contactNumber: '9876543211',
            yearsOfExperience: 15,
        });

        component.onSubmit();

        expect(component.successMessage).toBe('Doctor updated successfully!');
        expect(component.errorMessage).toBeNull();
    });

    it('should handle error when loading doctor details fails', () => {
        spyOn(service, 'getDoctorById').and.returnValue(throwError({ status: 404 }));
        spyOn(service, 'getUserById').and.returnValue(throwError({ status: 404 }));

        component.ngOnInit();

        expect(service.getDoctorById).toHaveBeenCalledWith(1);
        expect(service.getUserById).toHaveBeenCalledWith(1);

        expect(component.doctor).toBeUndefined();
        expect(component.user).toBeUndefined();
    });
});

describe('DashboardComponent', () => {
    let component: DashboardComponent;
    let fixture: ComponentFixture<DashboardComponent>;
    let service: MediConnectService;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [DashboardComponent],
            imports: [HttpClientTestingModule, RouterTestingModule, SharedModule],
            providers: [MediConnectService],
        }).compileComponents();

        service = TestBed.inject(MediConnectService);
        fixture = TestBed.createComponent(DashboardComponent);
        component = fixture.componentInstance;

        spyOn(localStorage, 'getItem').and.callFake((key: string) => {
            if (key === 'user_id') return '1';
            if (key === 'role') return 'DOCTOR';
            return null;
        });
    });

    it('should create the component', () => {
        expect(component).toBeTruthy();
    });

    it('should delete a doctor', () => {
        spyOn(window, 'confirm').and.returnValue(true);
        spyOn(service, 'deleteDoctor').and.returnValue(of(null));

        component.doctorId = 1;
        component.deleteDoctor();

        expect(service.deleteDoctor).toHaveBeenCalledWith(1);
    });

    it('should delete a clinic', () => {
        spyOn(window, 'confirm').and.returnValue(true);
        spyOn(service, 'deleteClinic').and.returnValue(of(null));

        component.deleteClinic(mockClinics[0].clinicId);

        expect(service.deleteClinic).toHaveBeenCalledWith(1);
    });

    it('should cancel an appointment', () => {
        spyOn(window, 'confirm').and.returnValue(true);
        spyOn(service, 'updateAppointment').and.returnValue(of(null));

        component.patientId = 1;
        component.cancelAppointment(mockAppointments[0]);

        expect(service.updateAppointment).toHaveBeenCalledWith(mockAppointments[0]);
    });
});
