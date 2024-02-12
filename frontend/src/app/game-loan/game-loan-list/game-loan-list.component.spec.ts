import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GameLoanListComponent } from './game-loan-list.component';

describe('GameLoanListComponent', () => {
  let component: GameLoanListComponent;
  let fixture: ComponentFixture<GameLoanListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GameLoanListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GameLoanListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
