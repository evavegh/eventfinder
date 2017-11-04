import {Event} from './event';
import {Location} from './location';
import {UserSettings} from './userSettings';

export class User {

  constructor(
    public id: number,
    public name: string,
    public email: string,
    public password: string,
    public roles: string[],
    public savedEvents: Event[],
    public savedLocations: Location[],
    public savedAdvertisers: User[],
    public memberSince: Date,
    public eventCount: number,
    public settings: UserSettings
  ) {}

}
