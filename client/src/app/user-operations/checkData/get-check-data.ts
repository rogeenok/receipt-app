import {CheckItems} from './check-items';
import {User} from './user';
import {ShortPlace} from "../placeData/short-place";

export interface GetCheckData {
  fiscalDocumentNumber: string;
  fiscalDriveNumber: string;
  fiscalSign: string;
  nds10: number;
  nds18: number;
  totalSum: number;
  dateTime: string;
  items: CheckItems[];
  username: string;
  selected: boolean;
  shortPlace: ShortPlace;
}
