import {FormGroup} from '@angular/forms';

export function hasError(form: FormGroup) {
  return (controlName: string, errorType: string): boolean => {
    const control = form.get(controlName);
    return (control.dirty || control.touched) && control.invalid && control.errors[errorType];
  };
}
