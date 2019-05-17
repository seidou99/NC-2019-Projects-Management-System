import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Attachment} from '../../models/attachment';

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

  constructor(private fb: FormBuilder) {
  }

  ngOnInit() {
    this.attachmentForm = this.fb.group({
      files: [null, Validators.required]
    });
  }

  submitForm() {
    const formValue = this.attachmentForm.get('files').value as any[];
    const files: File[] = formValue.map((v) => v.file as File);
    this.attachmentForm.reset();
    this.uploadAttachments.emit(files);
  }

  downloadAttachmentClick(attachmentId: number) {
    this.downloadAttachment.emit(attachmentId);
  }
}
