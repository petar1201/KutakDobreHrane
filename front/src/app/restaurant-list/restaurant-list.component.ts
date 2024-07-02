import { Component, Input, OnInit } from '@angular/core';
import { RestaurantListItem } from '../types/restaurantListItem';
import { RestaurantService } from '../services/restaurant.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-restaurant-list',
  templateUrl: './restaurant-list.component.html',
  styleUrls: ['./restaurant-list.component.css']
})
export class RestaurantListComponent implements OnInit {
  @Input() flag: boolean = false;
  restaurants: RestaurantListItem[] = [];
  searchName: string = '';
  searchAddress: string = '';
  searchType: string = '';
  sortColumn: string = '';
  sortOrder: string = 'asc'; // Default sort order
  restaurantTypes: string[] = ['SERBIAN', 'CHINESE', 'INDIAN', 'JAPANESE', 'ITALIAN', 'THAI'];

  constructor(private restaurantService: RestaurantService, private router: Router) {}

  ngOnInit(): void {
    this.fetchRestaurants();
  }

  fetchRestaurants(): void {
    this.restaurantService.getRestaurants().subscribe(data => {
      this.restaurants = data as RestaurantListItem[];
      this.sort();
    });
  }

  search(): void {
    this.restaurantService.getRestaurants().subscribe(data => {
      this.restaurants = (data as RestaurantListItem[]).filter(restaurant => {
        return (!this.searchName || restaurant.name.toLowerCase().includes(this.searchName.toLowerCase())) &&
               (!this.searchAddress || restaurant.address.toLowerCase().includes(this.searchAddress.toLowerCase())) &&
               (!this.searchType || restaurant.type === this.searchType);
      });
    });
  }

  sort(): void {
    const { sortColumn, sortOrder } = this;
    if (sortColumn) {
      this.restaurants.sort((a, b) => {
        const valueA = a[sortColumn].toLowerCase();
        const valueB = b[sortColumn].toLowerCase();
        if (valueA < valueB) return sortOrder === 'asc' ? -1 : 1;
        if (valueA > valueB) return sortOrder === 'asc' ? 1 : -1;
        return 0;
      });
    }
  }

  onRestaurantClick(restaurant: RestaurantListItem): void {
    localStorage.setItem("back_route", "/guest/restaurants")
    localStorage.setItem("res_details", JSON.stringify(restaurant))
    this.router.navigate(["/res_details_guest"])
  }
}