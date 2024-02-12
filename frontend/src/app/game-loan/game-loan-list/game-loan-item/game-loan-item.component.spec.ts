import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GameLoanItemComponent } from './game-loan-item.component';

describe('GameLoanItemComponent', () => {
  let component: GameLoanItemComponent;
  let fixture: ComponentFixture<GameLoanItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GameLoanItemComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GameLoanItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
