import {HttpErrorResponse} from "@angular/common/http";

export class Alert {
  type: string;
  message: string;
  timer: number;
  static showTime = 10000;

  showAlert(message: string, type: string) {
    clearTimeout(this.timer);
    this.message = message;
    this.type = type;
    this.timer = setTimeout(() => {
      this.message = '';
    }, Alert.showTime);
  }

  showHttpError(e: HttpErrorResponse) {
    if (e.error && e.error.message) {
      this.showAlert(e.error.message, 'danger');
    }
  }

  showSuccess(message: string) {
    this.showAlert(message, 'success');
  }
}
