import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

type UserLogin = { username: string; password: string };
type UserRegistrationDTO = {
  username: string;
  password: string;
  role: string;
  fullName: string;
  contactNumber: string;
  email: string;
  specialty?: string;
  yearsOfExperience?: number;
  dateOfBirth?: string | Date;
  address?: string;
};

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private loginUrl = `${environment.apiUrl}`;
  constructor(private http: HttpClient) {}
  login(user: Partial<UserLogin>): Observable<{ [key: string]: string }> {
    return this.http.post<{ [key: string]: string }>(`${this.loginUrl}/user/login`, user);
  }
  getToken(): string | null {
    return localStorage.getItem('token');
  }
  getRole(): string | null {
    return localStorage.getItem('role');
  }
  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('user_id');
    localStorage.removeItem('doctor_id');
    localStorage.removeItem('patient_id');
  }
  createUser(user: UserRegistrationDTO): Observable<any> {
    return this.http.post<any>(`${this.loginUrl}/user/register`, user);
  }
}