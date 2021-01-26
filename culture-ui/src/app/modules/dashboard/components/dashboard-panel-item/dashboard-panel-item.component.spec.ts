import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardPanelItemComponent } from './dashboard-panel-item.component';

describe('DashboardPanelItemComponent', () => {
  let component: DashboardPanelItemComponent;
  let fixture: ComponentFixture<DashboardPanelItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardPanelItemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardPanelItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
