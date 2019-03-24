import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {Role} from '../../models/Role';
import {validationConfigs} from '../../configs/validation-config';
import {hasError} from '../../util/has-error';
import {HasError} from '../../models/HasError';

@Component({
  selector: 'app-new-user',
  templateUrl: './new-user.component.html',
  styleUrls: ['./new-user.component.css']
})
export class NewUserComponent implements OnInit, HasError {

  newUserForm: FormGroup;
  availableRoles = [];
  validationConfigs = validationConfigs;
  isPasswordConfirmed = true;
  hasError;

  constructor(public activeModal: NgbActiveModal, private formBuilder: FormBuilder) {
    for (const role in Role) {
      if (role !== Role.Admin) {
        this.availableRoles.push(Role[role]);
      }
    }
    this.newUserForm = this.formBuilder.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required,
        Validators.minLength(validationConfigs.password.minlength), Validators.maxLength(validationConfigs.password.maxlength)]),
      passwordConfirmation: new FormControl('', [Validators.required]),
      name: new FormControl('', [Validators.required,
        Validators.minLength(validationConfigs.name.minlength), Validators.maxLength(validationConfigs.name.maxlength)]),
      surname: new FormControl('', [Validators.required,
        Validators.minLength(validationConfigs.surname.minlength), Validators.maxLength(validationConfigs.surname.maxlength)]),
      role: new FormControl(this.availableRoles[0], [Validators.required])
    });
    this.hasError = hasError(this.newUserForm);
  }

  ngOnInit() {

  }

  passwordConfirmation() {
    const password = this.newUserForm.get('password');
    const passwordConfirmation = this.newUserForm.get('passwordConfirmation');
    console.log('password ' + password.value + '\nconf ' + passwordConfirmation.value);
    if (passwordConfirmation.value !== password.value && (password.dirty || password.touched)
      && (passwordConfirmation.dirty || passwordConfirmation.touched)) {
      this.isPasswordConfirmed = false;
    } else {
      this.isPasswordConfirmed = true;
    }
  }

  submitForm() {
    this.activeModal.close(this.newUserForm.value);
  }

}
