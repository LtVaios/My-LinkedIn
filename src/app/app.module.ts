import { BrowserModule} from '@angular/platform-browser';
import { RouterModule, Routes} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { NgModule } from '@angular/core';
import { UserService} from './user/user.service';
import { AppComponent } from './app.component';
import { UserComponent } from './user/user.component';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import { HomepageComponent } from './homepage/homepage.component';
import { RegisterComponent } from './register/register.component';
import {LoginService} from "./login/login.service";
import { AdminHomepageComponent } from './admin-homepage/admin-homepage.component';
import { WelcomeComponent } from './welcome/welcome.component';
import {AuthGuard} from "./guards/auth.guard";
import { ErrorInterceptor} from './helpers/error.interceptor';
import { JwtInterceptor} from './helpers/jwt.interceptor';
import {RegisterService} from "./register/register.service";
import {HomepageService} from "./homepage/homepage.service";
import { InfoComponent } from './info/info.component';
import { SettingsComponent } from './settings/settings.component';
import { EditComponent } from './info/edit/edit.component';

const appRoutes: Routes = [
  {path: '', redirectTo: '/welcome', pathMatch: 'full'},
  {path: 'login', children: [{path: '', component: LoginComponent},{path: 'register', redirectTo: '/register', pathMatch: 'full'},], data: {header:false}},
  {path: 'home', component: HomepageComponent/**, canActivate: [AuthGuard]*/},
  {path: 'welcome', component: WelcomeComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'admin', component: AdminHomepageComponent/**, canActivate: [AuthGuard]*/},
  {path: 'users', component: UserComponent/**, canActivate: [AuthGuard]*/},
  {path: 'info', children: [{path: '', component: InfoComponent}, {path: 'edit', component: EditComponent}]},
  {path: 'settings', component: SettingsComponent}]

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    LoginComponent,
    LoginComponent,
    HomepageComponent,
    RegisterComponent,
    AdminHomepageComponent,
    WelcomeComponent,
    InfoComponent,
    SettingsComponent,
    EditComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes),
    HttpClientModule
  ],
  providers: [
    UserService,
    LoginService,
    RegisterService,
    HomepageService,
/**{ provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }*/
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
