import {Location} from './location';
import {Price} from './price';
import {User} from './user';

export class Event {

  constructor(
    public id: number,
    public name: string,
    public location: Location,
    public types: string[],
    public startsAt: Date,
    public endsAt: Date,
    public prices: string[],
    public summary: string,
    public description: string,
    public fbUrl: string,
    public webUrl: string,
    public createdBy: User
  ) {
  }

  toString() {
    return JSON.stringify(this);
  }

}
