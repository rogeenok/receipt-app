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
  id: string;
  min: number;
  max: number;
  avg: number;
  sum: number;
}

class Date {
  id: string;
  sum: number;
}
