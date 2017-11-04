export class Price {

  constructor(
    public id: number,
    public currency: string,
    public amount: number,
    public type: string
  ) {}

  toString() {
    return JSON.stringify(this);
  }

}
