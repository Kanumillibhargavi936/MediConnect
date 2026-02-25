import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule, FormBuilder } from '@angular/forms';
import { PatientCreateComponent } from 'src/app/mediconnect/components/patientcreate/patientcreate.component';
import { DoctorCreateComponent } from 'src/app/mediconnect/components/doctorcreate/doctorcreate.component';
import { ClinicCreateComponent } from 'src/app/mediconnect/components/cliniccreate/cliniccreate.component';
import { By } from '@angular/platform-browser';

describe('PatientCreateComponent', () => {
    let component: PatientCreateComponent;
    let fixture: ComponentFixture<PatientCreateComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [PatientCreateComponent],
            imports: [ReactiveFormsModule],
            providers: [FormBuilder]
        }).compileComponents();

        fixture = TestBed.createComponent(PatientCreateComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create the component', () => {
        expect(component).toBeTruthy();
    });

    it('should initialize the form with default values', () => {
        const form = component.patientForm;
        expect(form).toBeDefined();
        expect(form.controls['patientId'].value).toBeNull();
        expect(form.controls['fullName'].value).toBe('');
        expect(form.controls['dateOfBirth'].value).toBe('');
        expect(form.controls['contactNumber'].value).toBe('');
        expect(form.controls['email'].value).toBe('');
        expect(form.controls['address'].value).toBe('');
    });

    it('should reset the form when resetForm is called', () => {
        component.patientForm.patchValue({
            patientId: 1,
            fullName: 'John Doe',
            dateOfBirth: '1990-01-01',
            contactNumber: '9876543210',
            email: 'john@example.com',
            address: '123 Main Street'
        });

        component.resetForm();
        fixture.detectChanges();

        const form = component.patientForm;
        expect(form.controls['patientId'].value).toBeNull();
        expect(form.controls['fullName'].value).toBe('');
        expect(form.controls['dateOfBirth'].value).toBe('');
        expect(form.controls['contactNumber'].value).toBe('');
        expect(form.controls['email'].value).toBe('');
        expect(form.controls['address'].value).toBe('');
    });
});


describe('DoctorCreateComponent Tests', () => {
    let doctorFixture: ComponentFixture<DoctorCreateComponent>;
    let doctorComponent: DoctorCreateComponent;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [DoctorCreateComponent],
            imports: [ReactiveFormsModule],
            providers: [FormBuilder]
        }).compileComponents();

        doctorFixture = TestBed.createComponent(DoctorCreateComponent);
        doctorComponent = doctorFixture.componentInstance;
        doctorFixture.detectChanges();
    });
    it('should create the doctor form', () => {
        expect(doctorComponent).toBeTruthy();
        expect(doctorComponent.doctorForm).toBeTruthy();
    });

    it('should validate doctor form inputs', () => {
        const form = doctorComponent.doctorForm;

        const fullNameControl = form.controls['fullName'];
        fullNameControl.setValue('');
        expect(fullNameControl.valid).toBeFalsy();

        const contactNumberControl = form.controls['contactNumber'];
        contactNumberControl.setValue('12345'); // Invalid contact number
        expect(contactNumberControl.valid).toBeFalsy();

        contactNumberControl.setValue('1234567890'); // Valid contact number
        expect(contactNumberControl.valid).toBeTruthy();
    });

    it('should display success message on valid doctor form submission', () => {
        const form = doctorComponent.doctorForm;

        form.setValue({
            doctorId: 1,
            fullName: 'Dr. John Doe',
            specialty: 'Cardiology',
            contactNumber: '9876543210',
            email: 'john.doe@example.com',
            yearsOfExperience: 5
        });

        const submitButton = doctorFixture.debugElement.query(By.css('button[type="submit"]')).nativeElement;
        submitButton.click();
        doctorFixture.detectChanges();

        const successMessage = doctorFixture.debugElement.query(By.css('.success-message'));
        expect(successMessage.nativeElement.textContent).toContain('Doctor has been successfully created!');
    });
});



describe('ClinicCreateComponent Tests', () => {
    let clinicFixture: ComponentFixture<ClinicCreateComponent>;
    let clinicComponent: ClinicCreateComponent;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [ClinicCreateComponent],
            imports: [ReactiveFormsModule],
            providers: [FormBuilder]
        }).compileComponents();

        clinicFixture = TestBed.createComponent(ClinicCreateComponent);
        clinicComponent = clinicFixture.componentInstance;
        clinicFixture.detectChanges();
    });

    it('should create the clinic form', () => {
        expect(clinicComponent).toBeTruthy();
        expect(clinicComponent.clinicForm).toBeTruthy();
    });

    it('should validate clinic form inputs', () => {
        const form = clinicComponent.clinicForm;

        const clinicNameControl = form.controls['clinicName'];
        clinicNameControl.setValue('');
        expect(clinicNameControl.valid).toBeFalsy();

        const contactNumberControl = form.controls['contactNumber'];
        contactNumberControl.setValue('987654'); // Invalid contact number
        expect(contactNumberControl.valid).toBeFalsy();

        contactNumberControl.setValue('9876543210'); // Valid contact number
        expect(contactNumberControl.valid).toBeTruthy();
    });

    it('should display success message on valid clinic form submission', () => {
        const form = clinicComponent.clinicForm;

        form.setValue({
            clinicId: 1,
            clinicName: 'City Health Clinic',
            location: 'Downtown',
            contactNumber: '1234567890',
            establishedYear: 2015
        });

        const submitButton = clinicFixture.debugElement.query(By.css('button[type="submit"]')).nativeElement;
        submitButton.click();
        clinicFixture.detectChanges();

        const successMessage = clinicFixture.debugElement.query(By.css('.success-message'));
        expect(successMessage.nativeElement.textContent).toContain('Clinic has been successfully created!');
    });
});