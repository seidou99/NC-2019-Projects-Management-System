import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Attachment} from '../../models/attachment';
import {createHasError, HasErrorFunction} from "../../util/has-error";
import {validationConfigs} from "../../configuration/config";

@Component({
  selector: 'app-task-dashboard-attachments',
  templateUrl: './task-dashboard-attachments.component.html',
  styleUrls: ['./task-dashboard-attachments.component.css']
})
export class TaskDashboardAttachmentsComponent implements OnInit {

  @Input() attachments: Attachment[] = [];
  @Output() uploadAttachments = new EventEmitter<File[]>();
  @Output() downloadAttachment = new EventEmitter<number>();
  attachmentForm: FormGroup;
  hasError: HasErrorFunction;
  validationConfigs = validationConfigs;

  constructor(private fb: FormBuilder) {
  }

  ngOnInit() {
    this.attachmentForm = this.fb.group({
      files: new FormControl([], [Validators.required,
        this.maxFileSize(validationConfigs.attachment.maxSize)])
    });
    this.hasError = createHasError(this.attachmentForm);
  }

  submitForm() {
    const formValue = this.attachmentForm.get('files').value as any[];
    const files: File[] = formValue.map((v) => v.file as File);
    this.attachmentForm.reset();
    this.uploadAttachments.emit(files);
  }

  maxFileSize(maxSize: number) {
    return (control: FormControl) => {
      const controlValue = control.value as any[];
      const files: File[] = controlValue.map(v => v.file as File);
      for (let file of files) {
        if (file.size > maxSize) {
          return {maxFileSize: true};
        }
      }
      return null;
    }
  }

  downloadAttachmentClick(attachmentId: number) {
    this.downloadAttachment.emit(attachmentId);
  }
}
