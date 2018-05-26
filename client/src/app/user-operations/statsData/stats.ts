export class Stats {
  totalChecks : number;
  minTotalSum : number;
  maxTotalSum : number;
  avgTotalSum : number;
  totalSum: number;
  shopStats : Shop[];
  dateStats : Date[];
}

class Shop {
  id: String;
  min: number;
  max: number;
  avg: number;
  sum: number;
}

class Date {
  id: String;
  sum: number;
}
