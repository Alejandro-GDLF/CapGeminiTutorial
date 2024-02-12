import { TestBed } from '@angular/core/testing';

import { GameLoanService } from './game-loan.service';

describe('GameLoanService', () => {
  let service: GameLoanService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GameLoanService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
