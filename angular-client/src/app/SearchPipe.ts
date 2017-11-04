import {Type} from './model/type';
import {Pipe, PipeTransform} from '@angular/core';
import {MomentParseFunction} from 'angular-io-datepicker';
import * as moment from 'moment';

@Pipe({
  name: 'NamePipe'
})
export class NamePipe implements PipeTransform {
  transform(value: any, input: string) {
    if (input) {
      input = input.toLowerCase();
      return value.filter(function(el: any) {
        return el.name.toLowerCase().indexOf(input) > -1;
      });
    }
    return value;
  }
}

@Pipe({
  name: 'LocationPipe'
})
export class LocationPipe implements PipeTransform {
  transform(value: any, input: string) {
    if (input) {
      input = input.toLowerCase();
      return value.filter(function(el: any) {
        if (el.location.name == null) {
          return false;
        }
        return el.location.name.toLowerCase().indexOf(input) > -1;
      });
    }
    return value;
  }
}

@Pipe({
  name: 'CityPipe'
})
export class CityPipe implements PipeTransform {
  transform(value: any, input: string) {
    if (input) {
      input = input.toLowerCase();
      return value.filter(function(el: any) {
        return el.location.city.toLowerCase().indexOf(input) > -1;
      });
    }
    return value;
  }
}

@Pipe({
  name: 'StartDatePipe'
})
export class StartDatePipe implements PipeTransform {
  transform(value: any, input: any) {
    if (input) {
      const inputDate = new Date(input.format('YYYY-MM-DDTHH:mm:ssZ'));
      return value.filter(el => new Date(el.endsAt).getTime() >= inputDate.getTime());
    }
    return value;
  }
}

@Pipe({
  name: 'EndDatePipe'
})
export class EndDatePipe implements PipeTransform {
  transform(value: any, input: any) {
    if (input) {
      const inputDate = new Date(input.format('YYYY-MM-DDTHH:mm:ssZ'));
      return value.filter(el => new Date(el.endsAt).getTime() <= inputDate.getTime());
    }
    return value;
  }
}

@Pipe({
  name: 'OnlySavedPipe'
})
export class OnlySavedPipe implements PipeTransform {
  transform(events: any, savedEvents: any, input: boolean) {
    const result: Event[] = [];
    if (savedEvents == null) {
      return events;
    }
    if (input) {
      for (let i = 0; i < events.length; i++) {
        for (let j = 0; j < savedEvents.length; j++) {
          if (savedEvents[j].id === events[i].id) {
            result.push(events[i]);
          }
        }
      }
    } else {
      return events;
    }
    return result;
  }
}

@Pipe({
  name: 'TypePipe',
  pure: false
})
export class TypePipe implements PipeTransform {
  transform(events: any, types: Type[]) {
    if (!types || types.length === 0) {
      return events;
    }

    const typeNames = [];
    for (let i = 0; i < types.length; i++) {
      if (types[i].checked) {
        typeNames.push(types[i].name);
      }
    }

    return events.filter(event => this.checkIncludes(typeNames, event.types));
  }

  private checkIncludes(typeNames: string[], eventTypes: string[]) {
    let result = false;
    for (let i = 0; i < eventTypes.length; i++) {
      for (let j = 0; j < typeNames.length; j++) {
        if (eventTypes[i] === typeNames[j]) {
          result = true;
        }
      }
    }
    return result;
  }
}


