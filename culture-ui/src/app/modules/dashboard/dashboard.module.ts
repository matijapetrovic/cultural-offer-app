import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { TabViewModule } from 'primeng/tabview';
import { DashboardPanelContentComponent } from './components/dashboard-panel-content/dashboard-panel-content.component';


@NgModule({
  declarations: [DashboardComponent, DashboardPanelContentComponent],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    SharedModule,
    TabViewModule
  ]
})
export class DashboardModule { }
