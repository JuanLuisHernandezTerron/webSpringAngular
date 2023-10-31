import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FooterNotesForMeComponent } from './footer-notes-for-me.component';

describe('FooterNotesForMeComponent', () => {
  let component: FooterNotesForMeComponent;
  let fixture: ComponentFixture<FooterNotesForMeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FooterNotesForMeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FooterNotesForMeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
