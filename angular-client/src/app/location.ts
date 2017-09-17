export class Location {

  constructor(
    public id: number,
    public name: string,
    public country: string,
    public city: string,
    public zipCode: string,
    public address: string,
    public lat: number,
    public lon: number
  ) {}

  toString() {
    return this.zipCode + ' ' + this.city + ', ' + this.address;
  }

}
