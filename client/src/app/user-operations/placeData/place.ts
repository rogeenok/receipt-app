import {Coords} from "./coords";

export class Place {
  id: string;
  name: string;
  address: string;
  coords: Coords;
  rating: number;
  numOfChecks: number;
}
