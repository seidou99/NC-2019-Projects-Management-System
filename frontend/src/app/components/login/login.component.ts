import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {validationConfigs} from '../../configs/conf';
import {createHasError, HasErrorFunction} from '../../util/has-error';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  validationConfigs = validationConfigs;
  hasError: HasErrorFunction;

  constructor(private formBuilder: FormBuilder) {
    this.loginForm = this.formBuilder.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(validationConfigs.password.minlength),
        Validators.maxLength(validationConfigs.password.maxlength)]),
      rememberPassword: true
    });
    this.hasError = createHasError(this.loginForm);
  }

  ngOnInit() {

  }

  submitForm() {
    console.log(this.loginForm.value);
  }

}
