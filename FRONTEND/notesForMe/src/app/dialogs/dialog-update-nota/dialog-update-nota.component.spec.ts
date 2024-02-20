import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogUpdateNotaComponent } from './dialog-update-nota.component';

describe('DialogUpdateNotaComponent', () => {
  let component: DialogUpdateNotaComponent;
  let fixture: ComponentFixture<DialogUpdateNotaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogUpdateNotaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DialogUpdateNotaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
