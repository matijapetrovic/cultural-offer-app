import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardPanelContentComponent } from './dashboard-panel-content.component';

describe('DashboardPanelContentComponent', () => {
  let component: DashboardPanelContentComponent;
  let fixture: ComponentFixture<DashboardPanelContentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardPanelContentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardPanelContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
