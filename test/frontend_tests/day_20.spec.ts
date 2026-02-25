import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { AppointmentCreateComponent } from 'src/app/mediconnect/components/appointment/appointment.component';
import { By } from '@angular/platform-browser';

describe('AppointmentCreateComponent', () => {
  let component: AppointmentCreateComponent;
  let fixture: ComponentFixture<AppointmentCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AppointmentCreateComponent],
      imports: [ReactiveFormsModule]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppointmentCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should render the appointment form fields', () => {
    const formElement = fixture.debugElement.query(By.css('form')).nativeElement;
    const inputElements = formElement.querySelectorAll('input, textarea');

    expect(inputElements.length).toBe(6); // Appointment ID, Patient ID, Clinic ID, Date, Status, Purpose
  });

  it('should validate appointment form inputs', () => {
    const form = component.appointmentForm;

    const appointmentIdControl = form.controls['appointmentId'];
    appointmentIdControl.setValue('');
    expect(appointmentIdControl.valid).toBeFalsy();

    const statusControl = form.controls['status'];
    statusControl.setValue('');
    expect(statusControl.valid).toBeFalsy();

    const purposeControl = form.controls['purpose'];
    purposeControl.setValue('Routine'); // Valid purpose
    expect(purposeControl.valid).toBeTruthy();
  });

  it('should reset the form on reset button click', () => {
    const form = component.appointmentForm;

    form.setValue({
      appointmentId: 1,
      patientId: 101,
      clinicId: 202,
      appointmentDate: '2024-12-01',
      status: 'Scheduled',
      purpose: 'Routine Checkup'
    });

    const resetButton = fixture.debugElement.query(By.css('button[type="button"]')).nativeElement;
    resetButton.click();
    fixture.detectChanges();

    expect(form.value).toEqual({
      appointmentId: null,
      patientId: null,
      clinicId: null,
      appointmentDate: '',
      status: '',
      purpose: ''
    });
  });
});

