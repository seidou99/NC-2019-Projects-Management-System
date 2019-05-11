import {Injectable} from '@angular/core';
import {HttpClient, HttpClientJsonpModule, HttpResponse} from '@angular/common/http';
import {saveAs} from 'file-saver';
import {projectsURI} from '../configs/conf';

@Injectable({
  providedIn: 'root'
})
export class AttachmentService {

  constructor(private httpClient: HttpClient) {
  }

  uploadAttachments(projectId: number, taskId: number, attachments: File[]) {
    const formData = new FormData();
    attachments.forEach((file) => formData.append('files', file, file.name));
    console.log(attachments);
    return this.httpClient.post(`http://localhost:8080/api/projects/${projectId}/tasks/${taskId}/attachments`, formData);
  }

  downloadAttachment(projectId: number, taskId: number, attachmentId: number) {
    this.httpClient.get(`http://localhost:8080/api/projects/${projectId}/tasks/${taskId}/attachments/${attachmentId}`, {
      observe: 'response',
      responseType: 'blob'
    }).subscribe((data: HttpResponse<Blob>) => {
      let fileName = data.headers.get('content-disposition').split(';')[1].split('=')[1];
      fileName = fileName.substr(1, fileName.length - 2);
      saveAs(data.body, fileName);
    });
  }
}
