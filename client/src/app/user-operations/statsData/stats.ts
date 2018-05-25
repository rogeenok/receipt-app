export class Stats {
  totalChecks : String;
  minTotalSum : String;
  maxTotalSum : String;
  avgTotalSum : String;
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
