import {Project} from '../models/project';
import {User} from '../models/user';
import {NgbTypeahead} from '@ng-bootstrap/ng-bootstrap';
import {merge, Observable, Subject} from 'rxjs';
import {debounceTime, distinctUntilChanged, filter, map} from 'rxjs/operators';
import {FormControl} from '@angular/forms';

export function projectMapper(obj: Project): string {
  return obj.code;
}

export function userMapper(obj: User): string {
  return obj.surname + ' ' + obj.name + ' (' + obj.authData.email + ') ' + obj.role.name;
}

export function search<T>(instance: NgbTypeahead, focus$: Subject<string>, click$: Subject<string>, rawData: T[],
                          mapper: (obj: T) => string) {
  return (text$: Observable<string>) => {
    const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
    const clickWithClosedPopup$ = click$.pipe(
      filter(() => {
        return !instance.isPopupOpen();
      })
    );
    return merge(debouncedText$, focus$, clickWithClosedPopup$).pipe(
      map((term: string) => {
          let data = [];
          if (term === '' || !instance.isPopupOpen()) {
            data = rawData.map(mapper);
          } else {
            data = rawData.map(mapper).filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 20);
          }
          return data;
        }
      )
    );
  };
}

export function valueContainsValidator<T>(data: T[], mapper: (obj: T) => string) {
  return (control: FormControl) => {
    if (data.map(mapper).find((v: string) => v === control.value) || control.value === '') {
      return null;
    } else {
      return {valueContains: true};
    }
  };
}
