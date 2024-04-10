import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../service/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  registerForm!: FormGroup;
  errorMessage: string = '';

  constructor(private formBuilder: FormBuilder,
    private authService: AuthService,
    private router:Router
  ) {
    if (this.authService.isLogin()) {
      this.router.navigate(['/']);
    }
    this.registerForm = this.formBuilder.group({
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }

  register() {
    if (this.registerForm.valid) {
      this.authService.register(this.registerForm.value).subscribe({
        next: (response) => {
          if (response.success === true) {
            alert(response.data.message);
          }
        },
        error: (response) => {
          if (response.error.success === false) {
            this.errorMessage = response.error.error.message;
          }
        }
      });
    }
  }

}
