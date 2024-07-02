package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Restaurant;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.User;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.WaiterRestaurant;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.WaiterRestaurantId;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.RestaurantNotFoundException;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.UserNotFoundException;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.repository.WaiterRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WaiterRestaurantService {


    private final WaiterRestaurantRepository waiterRestaurantRepository;


    public void addDependency(User user, Restaurant restaurant) throws UserNotFoundException, RestaurantNotFoundException {
        if(user == null){
            throw new UserNotFoundException();
        }
        if (restaurant == null){
            throw new RestaurantNotFoundException();
        }


        List<WaiterRestaurant> waiterRestaurantList = waiterRestaurantRepository.findAll();
        for(WaiterRestaurant w: waiterRestaurantList){
            if(Objects.equals(w.getUser().getId(), user.getId())){
                waiterRestaurantRepository.delete(w);
                break;
            }
        }

        WaiterRestaurantId waiterRestaurantId = new WaiterRestaurantId();
        waiterRestaurantId.setRestaurantId(restaurant.getId());
        waiterRestaurantId.setUserId(user.getId());
        WaiterRestaurant waiterRestaurant = new WaiterRestaurant();
        waiterRestaurant.setId(waiterRestaurantId);
        waiterRestaurant.setRestaurant(restaurant);
        waiterRestaurant.setUser(user);
        waiterRestaurantRepository.save(waiterRestaurant);
    }

    public Long getByUser(User user) throws UserNotFoundException {
        List<WaiterRestaurant> waiterRestaurantsList = waiterRestaurantRepository.findAll();
        for (WaiterRestaurant w : waiterRestaurantsList){
            if(Objects.equals(w.getUser().getId(), user.getId())){
                return w.getRestaurant().getId();
            }
        }
        throw new UserNotFoundException();
    }

    public List<WaiterRestaurant> getAll(){
        return waiterRestaurantRepository.findAll();
    }

}
