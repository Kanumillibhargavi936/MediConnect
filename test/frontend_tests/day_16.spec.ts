import { Patient } from 'src/app/mediconnect/models/Patient';
import { Doctor } from 'src/app/mediconnect/models/Doctor';
import { Clinic } from 'src/app/mediconnect/models/Clinic';
import { Appointment } from 'src/app/mediconnect/models/Appointment';

describe('Model Classes Test Suite', () => {
  it('should create a Patient object and log its attributes', () => {
    const patient = new Patient(
      1,
      'John Doe',
      new Date('1990-01-01'),
      '1234567890',
      'john@example.com',
      '123 Main Street'
    );

    spyOn(console, 'log');
    patient.logAttributes();

    expect(console.log).toHaveBeenCalledWith('patientId:', 1);
    expect(console.log).toHaveBeenCalledWith('fullName:', 'John Doe');
    expect(console.log).toHaveBeenCalledWith('dateOfBirth:', new Date('1990-01-01'));
    expect(console.log).toHaveBeenCalledWith('contactNumber:', '1234567890');
    expect(console.log).toHaveBeenCalledWith('email:', 'john@example.com');
    expect(console.log).toHaveBeenCalledWith('address:', '123 Main Street');
  });

  it('should create a Doctor object and log its attributes', () => {
    const doctor = new Doctor(
      1,
      'Dr. Jane Smith',
      '9876543210',
      'jane@example.com',
      'Cardiology',
      15
    );

    spyOn(console, 'log');
    doctor.logAttributes();

    expect(console.log).toHaveBeenCalledWith('doctorId:', 1);
    expect(console.log).toHaveBeenCalledWith('fullName:', 'Dr. Jane Smith');
    expect(console.log).toHaveBeenCalledWith('specialty:', 'Cardiology');
    expect(console.log).toHaveBeenCalledWith('contactNumber:', '9876543210');
    expect(console.log).toHaveBeenCalledWith('email:', 'jane@example.com');
    expect(console.log).toHaveBeenCalledWith('yearsOfExperience:', 15);
  });

  it('should create a Clinic object and log its attributes', () => {
    const clinic = new Clinic(
      1,
      'City Health Clinic',
      'Downtown',
      10,
      '9876543210',
      2015
    );

    spyOn(console, 'log');
    clinic.logAttributes();

    expect(console.log).toHaveBeenCalledWith('clinicId:', 1);
    expect(console.log).toHaveBeenCalledWith('clinicName:', 'City Health Clinic');
    expect(console.log).toHaveBeenCalledWith('location:', 'Downtown');
    expect(console.log).toHaveBeenCalledWith('contactNumber:', '9876543210');
    expect(console.log).toHaveBeenCalledWith('establishedYear:', 2015);
  });

  it('should create an Appointment object and log its attributes', () => {
    const appointment = new Appointment(
      1,
      101,
      202,
      new Date('2024-12-01T10:00:00'),
      'Scheduled',
      'Routine Checkup'
    );

    spyOn(console, 'log');
    appointment.logAttributes();

    expect(console.log).toHaveBeenCalledWith('appointmentId:', 1);
    expect(console.log).toHaveBeenCalledWith('patientId:', 101);
    expect(console.log).toHaveBeenCalledWith('clinicId:', 202);
    expect(console.log).toHaveBeenCalledWith('appointmentDate:', new Date('2024-12-01T10:00:00'));
    expect(console.log).toHaveBeenCalledWith('status:', 'Scheduled');
    expect(console.log).toHaveBeenCalledWith('purpose:', 'Routine Checkup');
  });
});
