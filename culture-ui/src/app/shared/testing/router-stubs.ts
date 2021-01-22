import { BehaviorSubject } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable()
export class ActivatedRouteStub {

  // ActivatedRoute.params is Observable
  private subject = new BehaviorSubject(this.testParams);
  params = this.subject.asObservable();

  // Test parameters
  private myTestParams: {};
  get testParams(): {} { return this.myTestParams; }
  set testParams(params: {}) {
    this.myTestParams = params;
    this.subject.next(params);
  }

  // ActivatedRoute.snapshot.params
  get snapshot(): {} {
    return { params: this.testParams };
  }
}
