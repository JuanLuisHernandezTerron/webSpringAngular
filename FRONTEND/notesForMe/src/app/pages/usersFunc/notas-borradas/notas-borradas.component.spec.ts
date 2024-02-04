import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotasBorradasComponent } from './notas-borradas.component';

describe('NotasBorradasComponent', () => {
  let component: NotasBorradasComponent;
  let fixture: ComponentFixture<NotasBorradasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NotasBorradasComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NotasBorradasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
