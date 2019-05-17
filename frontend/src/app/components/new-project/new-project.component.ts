import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {validationConfigs} from '../../configuration/config';
import {createHasError, HasErrorFunction} from '../../util/has-error';
import {Project} from '../../models/project';
import {ProjectService} from '../../services/project.service';

@Component({
  selector: 'app-new-project',
  templateUrl: './new-project.component.html',
  styleUrls: ['./new-project.component.css']
})
export class NewProjectComponent implements OnInit {

  newProjectForm: FormGroup;
  validationConfigs = validationConfigs;
  hasError: HasErrorFunction;

  constructor(private projectService: ProjectService, public activeModal: NgbActiveModal, private formBuilder: FormBuilder) {
    this.newProjectForm = this.formBuilder.group({
      code: new FormControl('', [Validators.required, Validators.minLength(validationConfigs.projectCode.minlength),
        Validators.maxLength(validationConfigs.projectCode.maxlength)]),
      summary: new FormControl('', [Validators.required, Validators.minLength(validationConfigs.projectSummary.minlength),
        Validators.maxLength(validationConfigs.projectSummary.maxlength)])
    });
    this.hasError = createHasError(this.newProjectForm);
  }

  ngOnInit() {
  }

  submitForm() {
    const formValue = this.newProjectForm.value;
    const project = new Project(formValue.code, formValue.summary);
    this.activeModal.close(project);
  }
}
