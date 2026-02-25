import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { PatientCreateComponent } from 'src/app/mediconnect/components/patientcreate/patientcreate.component';
import { By } from '@angular/platform-browser';
import { DoctorArrayComponent } from 'src/app/mediconnect/components/doctorarray/doctorarray.component';

describe('PatientCreateComponent', () => {
    let component: PatientCreateComponent;
    let fixture: ComponentFixture<PatientCreateComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [FormsModule], // Required for two-way binding with [(ngModel)]
            declarations: [PatientCreateComponent],
        }).compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(PatientCreateComponent);
        component = fixture.componentInstance;
        fixture.detectChanges(); // Trigger initial data binding
    });

    it('should create the component', () => {
        expect(component).toBeTruthy();
    });

    it('should initialize with an empty patient object', () => {
        expect(component.patient).toEqual({
            patientId: 0,
            fullName: '',
            dateOfBirth: '',
            contactNumber: '',
            email: '',
            address: '',
        });
    });

    it('should display an error message if the form is invalid and submitted', () => {
        const submitButton = fixture.debugElement.query(By.css('button[type="submit"]'));

        submitButton.nativeElement.click();
        fixture.detectChanges();

        const errorMessage = fixture.debugElement.query(By.css('.error-message'));
        expect(errorMessage).toBeTruthy();
        expect(errorMessage.nativeElement.textContent).toContain('Please fill out all required fields correctly.');
    });

    it('should display a success message if the form is valid and submitted', () => {
        component.patient = {
            patientId: 1,
            fullName: 'John Doe',
            dateOfBirth: '1990-01-01',
            contactNumber: '1234567890',
            email: 'john.doe@example.com',
            address: '123 Main Street',
        };
        fixture.detectChanges();

        const submitButton = fixture.debugElement.query(By.css('button[type="submit"]'));
        submitButton.nativeElement.click();
        fixture.detectChanges();

        const successMessage = fixture.debugElement.query(By.css('.success-message'));
        expect(successMessage).toBeTruthy();
        expect(successMessage.nativeElement.textContent).toContain('Patient has been successfully created!');
    });

});

describe('DoctorArrayComponent', () => {
    let component: DoctorArrayComponent;
    let fixture: ComponentFixture<DoctorArrayComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [DoctorArrayComponent]
        }).compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(DoctorArrayComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create the component', () => {
        expect(component).toBeTruthy();
    });

    it('should render the list of doctors', () => {
        const doctorElements = fixture.debugElement.queryAll(By.css('li'));
        expect(doctorElements.length).toBe(3); 
        expect(doctorElements[0].nativeElement.textContent).toContain('Dr. Jane Smith');
        expect(doctorElements[0].nativeElement.textContent).toContain('Cardiology');
        expect(doctorElements[1].nativeElement.textContent).toContain('Dr. John Doe');
        expect(doctorElements[1].nativeElement.textContent).toContain('Orthopedics');
    });

});
