export class UserSettings {

  constructor(
    public id: number,
    public emailNotificationEnabled: boolean,
    public androidNotificationEnabled: boolean,
    public eventNotificationEnabled: boolean,
    public advertiserNotificationEnabled: boolean,
    public locationNotificationEnabled: boolean

  ) {}

}
