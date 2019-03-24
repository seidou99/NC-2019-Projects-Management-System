import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {validationConfigs} from '../../configs/validation-config';
import {hasError} from '../../util/has-error';
import {HasError} from '../../models/HasError';

@Component({
  selector: 'app-new-project',
  templateUrl: './new-project.component.html',
  styleUrls: ['./new-project.component.css']
})
export class NewProjectComponent implements OnInit, HasError {

  newProjectForm: FormGroup;
  validationConfigs = validationConfigs;
  hasError;

  constructor(public activeModal: NgbActiveModal, private formBuilder: FormBuilder) {
    this.newProjectForm = this.formBuilder.group({
      code: new FormControl('', [Validators.required, Validators.minLength(validationConfigs.projectCode.minlength),
        Validators.maxLength(validationConfigs.projectCode.maxlength)]),
      summary: new FormControl('', [Validators.required, Validators.minLength(validationConfigs.projectSummary.minlength),
        Validators.maxLength(validationConfigs.projectSummary.maxlength)])
    });
    this.hasError = hasError(this.newProjectForm);
  }

  ngOnInit() {
  }

  submitForm() {
    this.activeModal.close(this.newProjectForm.value);
  }
}
