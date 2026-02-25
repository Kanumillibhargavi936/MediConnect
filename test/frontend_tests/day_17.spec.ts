import { TestBed, ComponentFixture } from '@angular/core/testing';
import { PatientSampleComponent } from 'src/app/mediconnect/components/patientsample/patientsample.component';
import { DoctorSampleComponent } from 'src/app/mediconnect/components/doctorsample/doctorsample.component';



describe('PatientSampleComponent', () => {
    let patientFixture: ComponentFixture<PatientSampleComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [PatientSampleComponent]
        });

        patientFixture = TestBed.createComponent(PatientSampleComponent);
    });
    it('should create the patient component', () => {
        const component = patientFixture.componentInstance;
        expect(component).toBeTruthy();
    });

    it('should display patient details', () => {
        patientFixture.detectChanges();

        const compiled = patientFixture.nativeElement;
        expect(compiled.textContent).toContain('patientId: 1');
        expect(compiled.textContent).toContain('fullName: John Doe');
        expect(compiled.textContent).toContain('dateOfBirth: 1990-01-01');
        expect(compiled.textContent).toContain('contactNumber: 1234567890');
        expect(compiled.textContent).toContain('email: john@example.com');
        expect(compiled.textContent).toContain('address: 123 Main Street, Cityville');
    });

});

describe('DoctorSampleComponent', () => {
    let doctorFixture: ComponentFixture<DoctorSampleComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [DoctorSampleComponent]
        });

        doctorFixture = TestBed.createComponent(DoctorSampleComponent);
    });
    it('should create the doctor component', () => {
        const component = doctorFixture.componentInstance;
        expect(component).toBeTruthy();
    });

    it('should display doctor details', () => {
        doctorFixture.detectChanges();

        const compiled = doctorFixture.nativeElement;
        expect(compiled.textContent).toContain('doctorId: 1');
        expect(compiled.textContent).toContain('fullName: Dr. Jane Smith');
        expect(compiled.textContent).toContain('specialty: Cardiology');
        expect(compiled.textContent).toContain('contactNumber: 9876543210');
        expect(compiled.textContent).toContain('email: jane@example.com');
        expect(compiled.textContent).toContain('yearsOfExperience: 15');
    });
});

