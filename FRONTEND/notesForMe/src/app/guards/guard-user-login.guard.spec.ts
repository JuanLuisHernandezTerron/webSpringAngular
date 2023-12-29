import { TestBed } from '@angular/core/testing';

import { GuardUserLoginGuard } from './guard-user-login.guard';

describe('GuardUserLoginGuard', () => {
  let guard: GuardUserLoginGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(GuardUserLoginGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
