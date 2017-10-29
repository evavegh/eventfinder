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
  transform(value: any, input: Date) {
    if (input) {
      return value.filter(function(el: any) {
        console.log('now');
        console.log(el.startsAt);
        console.log(input);
        return el.startsAt.getDate > input.getDate;
      });
    }
    return value;
  }
}

@Pipe({
  name: 'EndDatePipe'
})
export class EndDatePipe implements PipeTransform {
  transform(value: any, input: Date) {
    if (input) {
      return value.filter(function(el: any) {
        return el.endsAt < input;
      });
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
