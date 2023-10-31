import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderNotesForMeComponent } from './header-notes-for-me.component';

describe('HeaderNotesForMeComponent', () => {
  let component: HeaderNotesForMeComponent;
  let fixture: ComponentFixture<HeaderNotesForMeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HeaderNotesForMeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderNotesForMeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
