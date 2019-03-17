import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-new-user',
  templateUrl: './new-user.component.html',
  styleUrls: ['./new-user.component.css']
})
export class NewUserComponent implements OnInit {

  newUserForm: FormGroup;

  constructor(public activeModal: NgbActiveModal, private formBuilder: FormBuilder) {
    this.newUserForm = this.formBuilder.group({
      email: '',
      password: '',
      passwordConfirmation: '',
      name: '',
      surname: '',
      role: ''
    });
  }

  ngOnInit() {
  }

  submitForm() {
    this.activeModal.close(this.newUserForm.value);
  }

}
