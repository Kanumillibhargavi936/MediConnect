export class User {
  userId: number;
  username: string;
  password: string;
  role: string;
  patient?: any;
  doctor?: any;
 
  constructor(
    userId: number,
    username: string,
    password: string,
    role: string,
    patient?: any,
    doctor?: any
  ) {
    this.userId = userId;
    this.username = username;
    this.password = password;
    this.role = role;
    this.patient = patient;
    this.doctor = doctor;
  }
 
  logAttributes?(): void {}
}