package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Restaurant;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.WaiterRestaurant;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.InvalidLayoutException;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.dto.RestaurantsDTO;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request.CreateRestaurantRequest;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.restaurant.RestaurantLayout;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.repository.RestaurantRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantValidationService restaurantValidationService;

    private final RestaurantRepository restaurantRepository;

    public Restaurant addRestaurant(CreateRestaurantRequest createRestaurantRequest) throws InvalidLayoutException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        RestaurantLayout restaurantLayout = mapper.readValue(createRestaurantRequest.getRestaurantLayout(), RestaurantLayout.class);

        if(!restaurantValidationService.isLayoutValid(restaurantLayout)){
            throw new InvalidLayoutException();
        }


        Restaurant restaurant = new Restaurant(
            createRestaurantRequest.getName(),
            createRestaurantRequest.getAddress(),
            createRestaurantRequest.getContactPerson(),
            createRestaurantRequest.getDescription(),
            createRestaurantRequest.getType().name(),
            createRestaurantRequest.getRestaurantLayout()
        );

        return restaurantRepository.save(restaurant);

    }

    public Restaurant setWorkingHours(Long id, String workingHours) throws Exception {
        //TODO CHECK FORMAT?
        Restaurant restaurant = restaurantRepository.findById(id).isPresent() ? restaurantRepository.findById(id).get() : null;
        if (restaurant == null){
            throw new Exception();
        }
        restaurant.setWorkingHours(workingHours);
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAll(){
        return restaurantRepository.findAll();
    }

    public Restaurant findById(Long id){
        return restaurantRepository.findById(id).isPresent() ? restaurantRepository.findById(id).get() : null;
    }

    public RestaurantsDTO toDto(Restaurant restaurant){
        RestaurantsDTO restaurantsDTO = new RestaurantsDTO();
        restaurantsDTO.setId(restaurant.getId());
        restaurantsDTO.setName(restaurant.getName());
        restaurantsDTO.setAddress(restaurant.getAddress());
        restaurantsDTO.setType(restaurant.getType());
        restaurantsDTO.setWorkingHours(restaurant.getWorkingHours());
        return restaurantsDTO;
    }

    //TODO
//    public RestaurantsDTO toRestaurantsDto(Restaurant restaurant){
//        RestaurantsDTO restaurantsDTO = new RestaurantsDTO();
//    }


    public List<RestaurantsDTO> getAllRestaurants(List<WaiterRestaurant> waiterRestaurantList){
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        List<RestaurantsDTO> restaurantsDTOList = new ArrayList<>();
        for(Restaurant restaurant: restaurantList){
            RestaurantsDTO restaurantsDTO = new RestaurantsDTO();
            restaurantsDTO.setAddress(restaurant.getAddress());
            restaurantsDTO.setName(restaurant.getName());
            restaurantsDTO.setContactPerson(restaurant.getContactPerson());
            restaurantsDTO.setWorkingHours(restaurant.getWorkingHours());
            restaurantsDTO.setType(restaurant.getType());
            restaurantsDTO.setId(restaurant.getId());
            restaurantsDTO.setRestaurantLayout(restaurant.getRestaurantLayout());
            StringBuilder waiters = new StringBuilder();
            for (WaiterRestaurant waiterRestaurant: waiterRestaurantList){
                if(Objects.equals(waiterRestaurant.getId().getRestaurantId(), restaurant.getId())){
                    waiters.append(waiterRestaurant.getUser().getName() + " " + waiterRestaurant.getUser().getLastName()).append(",");
                }
            }
            if(waiters.toString().isEmpty()){
                restaurantsDTO.setWaiters("NO WAITERS");
            }
            else{
                restaurantsDTO.setWaiters(waiters.toString());
            }
            restaurantsDTOList.add(restaurantsDTO);
        }
        return restaurantsDTOList;
    }

    public long countAll(){
        return restaurantRepository.count();
    }




}
