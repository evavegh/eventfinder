import {Event} from './event';

export class User {

  constructor(
    public id: number,
    public name: string,
    public email: string,
    public password: string,
    public roles: string[],
    public savedEvents: Event[]
  ) {}

}
