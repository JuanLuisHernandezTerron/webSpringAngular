import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainUserLogginComponent } from './main-user-loggin.component';

describe('MainUserLogginComponent', () => {
  let component: MainUserLogginComponent;
  let fixture: ComponentFixture<MainUserLogginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MainUserLogginComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MainUserLogginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
