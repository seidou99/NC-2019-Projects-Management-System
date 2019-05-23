import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {validationConfigs} from '../../configuration/config';
import {createHasError, HasErrorFunction} from '../../util/has-error';
import {AuthService} from '../../services/auth.service';
import {UserAuthData} from '../../models/user';
import {ActivatedRoute, Router, RouterState} from '@angular/router';
import {Observable, Subscription} from 'rxjs';
import {map} from 'rxjs/operators';
import {HttpErrorResponse} from '@angular/common/http';
import {Ng4LoadingSpinnerService} from 'ng4-loading-spinner';
import {Alert} from "../../util/alert";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  loginForm: FormGroup;
  validationConfigs = validationConfigs;
  hasError: HasErrorFunction;
  alert: Alert;
  subscriptions: Subscription[] = [];

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router,
              private activatedRoute: ActivatedRoute, private spinner: Ng4LoadingSpinnerService) {
    this.loginForm = this.formBuilder.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required,
        Validators.minLength(validationConfigs.password.minlength),
        Validators.maxLength(validationConfigs.password.maxlength)]),
      rememberPassword: true
    });
    this.hasError = createHasError(this.loginForm);
    this.alert = new Alert();
  }

  ngOnInit() {
    if (this.authService.getToken()) {
      this.router.navigate(['already-logged-in']);
      return;
    }
    const state$: Observable<any> = this.activatedRoute.paramMap.pipe(
      map(() => window.history.state)
    );
    const sub = state$.subscribe((state: any) => {
      this.alert.showAlert(state.reason, 'danger');
    });
    const credentials = this.authService.getUserCredentials();
    if (credentials) {
      this.loginForm.patchValue({
        email: credentials.email,
        password: credentials.password
      });
    }
  }

  submitForm() {
    const formValue = this.loginForm.value;
    const rememberPassword: boolean = formValue.rememberPassword;
    const credentials = new UserAuthData(formValue.email, formValue.password);
    this.spinner.show();
    const sub = this.authService.login(credentials, rememberPassword).subscribe(() => {
      this.spinner.hide();
      this.router.navigate(['']);
    }, (e: HttpErrorResponse) => {
      this.alert.showHttpError(e);
      this.spinner.hide();
    });
    this.subscriptions.push(sub);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(v => v.unsubscribe());
  }
}
