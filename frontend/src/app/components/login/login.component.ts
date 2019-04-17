import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {validationConfigs} from '../../configs/conf';
import {createHasError, HasErrorFunction} from '../../util/has-error';
import {AuthService} from '../../services/auth.service';
import {UserAuthData} from '../../models/user';
import {ActivatedRoute, Router, RouterState} from '@angular/router';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  validationConfigs = validationConfigs;
  hasError: HasErrorFunction;
  errorText = '';

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router,
              private activatedRoute: ActivatedRoute) {
    this.loginForm = this.formBuilder.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(validationConfigs.password.minlength),
        Validators.maxLength(validationConfigs.password.maxlength)]),
      rememberPassword: true
    });
    this.hasError = createHasError(this.loginForm);
  }

  ngOnInit() {
    const state$: Observable<any> = this.activatedRoute.paramMap.pipe(
      map(() => window.history.state)
    );
    state$.subscribe((state: any) => {
      this.errorText = state.reason;
    });
  }

  submitForm() {
    const formValue = this.loginForm.value;
    const rememberPassword: boolean = formValue.rememberPassword;
    const credentials = new UserAuthData(formValue.email, formValue.password);
    this.authService.login(credentials, rememberPassword).subscribe(() => {
      this.router.navigate(['']);
    }, (e: HttpErrorResponse) => {
      console.log(e);
      if (e.status === 401) {
        this.errorText = 'Wrong email or password';
      }
    });
  }

}
