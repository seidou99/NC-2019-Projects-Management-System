import {AbstractControl} from '@angular/forms';

export function getNextDay(): Date {
  const date = new Date();
  date.setDate(date.getDate() + 1);
  return date;
}

export function transformDate(date: Date): string {
  return date.toString().substring(0, 10);
}

export function minDateValidator(minDate: Date) {
  minDate.setHours(0, 0, 0, 0);
  return (dateControl: AbstractControl) => {
    if (!dateControl.value || new Date(dateControl.value) >= minDate) {
      return null;
    } else {
      return {minDate: true};
    }
  };
}
