import {Component} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {UserRole} from '../../models/user-role';
import {validationConfigs} from '../../configs/conf';
import {createHasError, HasErrorFunction} from '../../util/has-error';
import {User} from '../../models/user';

@Component({
  selector: 'app-new-user',
  templateUrl: './new-user.component.html',
  styleUrls: ['./new-user.component.css']
})
export class NewUserComponent {

  newUserForm: FormGroup;
  availableRoles = [];
  validationConfigs = validationConfigs;
  isPasswordConfirmed = true;
  hasError: HasErrorFunction;

  constructor(public activeModal: NgbActiveModal, private formBuilder: FormBuilder) {
    for (const role in UserRole) {
      if (UserRole[role] !== UserRole.ADMIN) {
        this.availableRoles.push(UserRole[role]);
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
    this.hasError = createHasError(this.newUserForm);
  }

  passwordConfirmation() {
    const password = this.newUserForm.get('password');
    const passwordConfirmation = this.newUserForm.get('passwordConfirmation');
    if (passwordConfirmation.value !== password.value && (password.dirty || password.touched)
      && (passwordConfirmation.dirty || passwordConfirmation.touched)) {
      this.isPasswordConfirmed = false;
    } else {
      this.isPasswordConfirmed = true;
    }
  }

  submitForm() {
    const formValue = this.newUserForm.value;
    const user = new User(formValue.name, formValue.surname, formValue.email, formValue.password, formValue.role);
    this.activeModal.close(user);
  }

}
