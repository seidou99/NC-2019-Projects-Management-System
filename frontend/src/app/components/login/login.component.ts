import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {validationConfigs} from '../../configs/validation-config';
import {hasError} from '../../util/has-error';
import {HasError} from '../../models/HasError';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, HasError {

  loginForm: FormGroup;
  validationConfigs = validationConfigs;
  hasError;

  constructor(private formBuilder: FormBuilder) {
    this.loginForm = this.formBuilder.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(validationConfigs.password.minlength),
        Validators.maxLength(validationConfigs.password.maxlength)]),
      rememberPassword: true
    });
    this.hasError = hasError(this.loginForm);
  }

  ngOnInit() {
  }

  submitForm() {
    console.log(this.loginForm.value);
  }

}
