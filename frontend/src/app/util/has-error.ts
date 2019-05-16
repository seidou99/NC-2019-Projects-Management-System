import {FormGroup} from '@angular/forms';

export type HasErrorFunction = (controlName: string, errorType: string) => boolean;

export function createHasError(form: FormGroup): HasErrorFunction {
  return (controlName: string, errorType: string): boolean => {
    const control = form.get(controlName);
    return (control.dirty || control.touched) && control.invalid && control.errors[errorType];
  };
}
