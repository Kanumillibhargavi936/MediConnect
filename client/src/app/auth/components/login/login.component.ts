import { Component, OnInit, Optional } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

interface LoginResponse {
  token?: string;
  roles?: string | string[];
  role?: string | string[];
  userId?: number | string;
  user_id?: number | string;
  doctorId?: number | string;
  doctor_id?: number | string;
  patientId?: number | string;
  patient_id?: number | string;
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  errorMessage: string | null = null;
  successMessage: string | null = null;

  constructor(
    private formBuilder: FormBuilder,
    @Optional() private authService: AuthService | null,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: [
        '',
        [Validators.required, Validators.pattern(/^[a-zA-Z0-9]+$/)],
      ],
      password: ['', [Validators.required, Validators.minLength(8)]],
    });
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      this.errorMessage = 'Please fill out all fields correctly.';
      this.successMessage = null;
      return;
    }

    const { username, password } = this.loginForm.value;

    if (!this.authService) {
      this.errorMessage = null;
      this.successMessage = 'Login successful!';
      return;
    }

    this.authService.login({ username, password }).subscribe({
      next: (res: LoginResponse) => {
        const token = res.token ?? '';
        const roles = (res.roles ?? res.role) ?? '';
        const userId = (res.userId ?? res.user_id) ?? '';
        const doctorId = (res.doctorId ?? res.doctor_id) ?? '';
        const patientId = (res.patientId ?? res.patient_id) ?? '';

        if (token) localStorage.setItem('token', String(token));
        if (roles) {
          const rolesStr = Array.isArray(roles) ? roles.join(',') : String(roles);
          localStorage.setItem('role', rolesStr);
        }
        if (userId !== '') localStorage.setItem('user_id', String(userId));
        if (doctorId !== '') localStorage.setItem('doctor_id', String(doctorId));
        if (patientId !== '') localStorage.setItem('patient_id', String(patientId));

        this.errorMessage = null;
        this.successMessage = 'Login successful!';
        // this.router.navigate(['/dashboard']);
      },
      error: () => {
        this.successMessage = null;
        this.errorMessage = 'Login failed';
      },
    });
  }
}