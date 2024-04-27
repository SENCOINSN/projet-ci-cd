import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { AdminTemplateComponent } from './admin-template/admin-template.component';
import {MatToolbar, MatToolbarModule} from "@angular/material/toolbar";
import {MatButton, MatButtonModule, MatIconButton} from "@angular/material/button";
import {MatIcon, MatIconModule} from "@angular/material/icon";
import {MatMenu, MatMenuItem, MatMenuModule, MatMenuTrigger} from "@angular/material/menu";
import { MatSidenavModule} from "@angular/material/sidenav";
import {MatList, MatListItem, MatNavList} from "@angular/material/list";
import {MatCardModule} from "@angular/material/card";
import {MatDividerModule} from "@angular/material/divider";
import {MatTableModule} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort, MatSortHeader} from "@angular/material/sort";
import {MatInput} from "@angular/material/input";
import {MatFormFieldModule} from "@angular/material/form-field";
import {ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { LoadStudentsComponent } from './load-students/load-students.component';
import { LoadPaymentsComponent } from './load-payments/load-payments.component';
import { LoginComponent } from './login/login.component';
import { StudentsComponent } from './students/students.component';
import { PaymentsComponent } from './payments/payments.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import {AuthGuardGuard} from "./guards/auth-guard.guard";
import {AuthorizationGuardGuard} from "./guards/authorization-guard.guard";
import {HttpClientModule} from "@angular/common/http";


@NgModule({
  declarations: [
    AppComponent,
    AdminTemplateComponent,
    HomeComponent,
    ProfileComponent,
    LoadStudentsComponent,
    LoadPaymentsComponent,
    LoginComponent,
    StudentsComponent,
    PaymentsComponent,
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatSidenavModule,
    MatListItem,
    MatNavList,
    MatCardModule,
    MatDividerModule,
    MatTableModule,
    MatPaginator,
    MatSort,
    MatSortHeader,
    MatInput,
    MatFormFieldModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatList,
    HttpClientModule

  ],
  providers: [
    provideAnimationsAsync(),AuthGuardGuard,
    AuthorizationGuardGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
