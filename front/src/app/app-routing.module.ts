import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './home-page/home-page.component';
import { AdminLoginPageComponent } from './admin-login-page/admin-login-page.component';
import { AdminPageComponent } from './admin-page/admin-page.component';
import { UserDetailsComponent } from './user-details/user-details.component';
import { RestaurantDetailsAdminComponent } from './restaurant-details-admin/restaurant-details-admin.component';
import { GuestProfilePageComponent } from './guest-profile-page/guest-profile-page.component';
import { RestaurantListComponent } from './restaurant-list/restaurant-list.component';
import { GuestRestaurantsPageComponent } from './guest-restaurants-page/guest-restaurants-page.component';
import { RestaurantDetailsGuestComponent } from './restaurant-details-guest/restaurant-details-guest.component';
import { GuestReservationsPageComponent } from './guest-reservations-page/guest-reservations-page.component';
import { GuestDeliveryPageComponent } from './guest-delivery-page/guest-delivery-page.component';
import { WaiterProfilePageComponent } from './waiter-profile-page/waiter-profile-page.component';
import { WaiterDeliveryPageComponent } from './waiter-delivery-page/waiter-delivery-page.component';
import { WaiterReservationPageComponent } from './waiter-reservation-page/waiter-reservation-page.component';
import { WaiterStatisticsPageComponent } from './waiter-statistics-page/waiter-statistics-page.component';
import { RestaurantLayoutComponent } from './restaurant-layout/restaurant-layout.component';
import { ReservationDetailsComponent } from './reservation-details/reservation-details.component';


const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'home', component: HomePageComponent},
  { path: 'admin/login', component: AdminLoginPageComponent },
  { path: 'admin', component: AdminPageComponent},
  { path: 'user_details', component: UserDetailsComponent},
  { path: "res_details_admin", component: RestaurantDetailsAdminComponent},
  { path: "guest/profile", component: GuestProfilePageComponent},
  { path: "guest/restaurants", component: GuestRestaurantsPageComponent},
  { path: "res_details_guest", component: RestaurantDetailsGuestComponent},
  { path: "guest/reservations", component: GuestReservationsPageComponent},
  { path: "guest/delivery", component: GuestDeliveryPageComponent},
  { path: "waiter/profile", component:WaiterProfilePageComponent},
  { path: "waiter/delivery", component:WaiterDeliveryPageComponent},
  { path: "waiter/reservations", component:WaiterReservationPageComponent},
  { path: "waiter/stats", component: WaiterStatisticsPageComponent},
  { path: "reservation", component: ReservationDetailsComponent}


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
