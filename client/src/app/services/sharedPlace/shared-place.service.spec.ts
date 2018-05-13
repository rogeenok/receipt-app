import { TestBed, inject } from '@angular/core/testing';

import { SharedPlaceService } from './shared-place.service';

describe('SharedPlaceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SharedPlaceService]
    });
  });

  it('should be created', inject([SharedPlaceService], (service: SharedPlaceService) => {
    expect(service).toBeTruthy();
  }));
});
