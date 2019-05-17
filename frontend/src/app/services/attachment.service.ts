import {Injectable} from '@angular/core';
import {HttpClient, HttpClientJsonpModule, HttpResponse} from '@angular/common/http';
import {saveAs} from 'file-saver';
import {projectsURI} from '../configuration/config';
import {map} from 'rxjs/operators';
import {Observable} from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class AttachmentService {

  constructor(private httpClient: HttpClient) {
  }

  uploadAttachments(projectId: number, taskId: number, attachments: File[]) {
    const formData = new FormData();
    attachments.forEach((file) => formData.append('files', file, file.name));
    return this.httpClient.post(`${projectsURI}/${projectId}/tasks/${taskId}/attachments`, formData);
  }

  downloadAttachment(projectId: number, taskId: number, attachmentId: number): Observable<HttpResponse<Blob>> {
    return this.httpClient.get(`${projectsURI}/${projectId}/tasks/${taskId}/attachments/${attachmentId}`, {
      observe: 'response',
      responseType: 'blob'
    }).pipe(
      map((data: HttpResponse<Blob>) => {
        console.log(data);
        let fileName = data.headers.get('content-disposition').split(';')[1].split('=')[1];
        fileName = fileName.substr(1, fileName.length - 2);
        console.log(fileName);
        fileName = decodeURIComponent(fileName);
        console.log(fileName);
        saveAs(data.body, fileName);
        return data;
      })
    );
  }
}
