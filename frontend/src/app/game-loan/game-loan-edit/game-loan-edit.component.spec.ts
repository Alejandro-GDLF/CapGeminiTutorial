import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GameLoanEditComponent } from './game-loan-edit.component';

describe('GameLoanEditComponent', () => {
  let component: GameLoanEditComponent;
  let fixture: ComponentFixture<GameLoanEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GameLoanEditComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GameLoanEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
