import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomePageComponent } from './home-page/home-page.component';
import { FormsModule } from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import { TopBarComponent } from './top-bar/top-bar.component';
import { AdminLoginPageComponent } from './admin-login-page/admin-login-page.component';
import { AdminPageComponent } from './admin-page/admin-page.component';
import { UserDetailsComponent } from './user-details/user-details.component';
import { RestaurantDetailsAdminComponent } from './restaurant-details-admin/restaurant-details-admin.component';
import { GuestTopBarComponent } from './guest-top-bar/guest-top-bar.component';
import { GuestProfilePageComponent } from './guest-profile-page/guest-profile-page.component';
import { RestaurantListComponent } from './restaurant-list/restaurant-list.component';
import { GuestRestaurantsPageComponent } from './guest-restaurants-page/guest-restaurants-page.component';
import { RestaurantDetailsGuestComponent } from './restaurant-details-guest/restaurant-details-guest.component';
import { GuestReservationsPageComponent } from './guest-reservations-page/guest-reservations-page.component';
import { GuestDeliveryPageComponent } from './guest-delivery-page/guest-delivery-page.component';
import { WaiterTopBarComponent } from './waiter-top-bar/waiter-top-bar.component';
import { WaiterProfilePageComponent } from './waiter-profile-page/waiter-profile-page.component';
import { WaiterDeliveryPageComponent } from './waiter-delivery-page/waiter-delivery-page.component';
import { WaiterReservationPageComponent } from './waiter-reservation-page/waiter-reservation-page.component';
import { WaiterStatisticsPageComponent } from './waiter-statistics-page/waiter-statistics-page.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DeliveryTimeDialogComponent } from './delivery-time-dialog/delivery-time-dialog.component';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatRadioModule } from '@angular/material/radio';
@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    TopBarComponent,
    AdminLoginPageComponent,
    AdminPageComponent,
    UserDetailsComponent,
    RestaurantDetailsAdminComponent,
    GuestTopBarComponent,
    GuestProfilePageComponent,
    RestaurantListComponent,
    GuestRestaurantsPageComponent,
    RestaurantDetailsGuestComponent,
    GuestReservationsPageComponent,
    GuestDeliveryPageComponent,
    WaiterTopBarComponent,
    WaiterProfilePageComponent,
    WaiterDeliveryPageComponent,
    WaiterReservationPageComponent,
    WaiterStatisticsPageComponent,
    DeliveryTimeDialogComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatDialogModule,
    MatButtonModule,
    MatRadioModule,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
