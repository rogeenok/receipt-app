export class Coords {
  longitude: number;
  latitude: number;

  constructor(lat: number=null,lng: number=null) {
    this.longitude = lng;
    this.latitude = lat;
  }

  setLongitude(lng: number){
    this.longitude = lng;
  }
  setLatitude(lat: number){
    this.latitude = lat;
  }
}

