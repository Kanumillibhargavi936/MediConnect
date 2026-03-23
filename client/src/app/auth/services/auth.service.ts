import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { Injectable } from "@angular/core";
import { User } from "src/app/mediconnect/models/User";
import { UserRegistrationDTO } from "src/app/mediconnect/models/UserRegistrationDTO";

@Injectable({
  providedIn: "root",
})
export class AuthService {

  // ✅ WILL RESOLVE TO http://localhost:3000
  private baseUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  private httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json"
    })
  };

  // ✅ LOGIN
  login(user: Partial<User>): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/user/login`,
      user,
      this.httpOptions
    );
  }

  // ✅ REGISTER
  createUser(user: UserRegistrationDTO): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/user/register`,
      user,
      this.httpOptions
    );
  }

  getToken(): string | null {
    return localStorage.getItem("token");
  }

  getRole(): string | null {
    return localStorage.getItem("role");
  }

  logout(): void {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    localStorage.removeItem("user_id");
    localStorage.removeItem("doctor_id");
    localStorage.removeItem("patient_id");
  }
}