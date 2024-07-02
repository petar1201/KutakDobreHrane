package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Delivery;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Restaurant;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.User;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.dto.DeliveryDto;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.enums.DeliveryStatus;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public List<DeliveryDto> findActiveDeliveriesForGuest(User guest){
        List<Delivery> deliveryListAccepted = deliveryRepository.findAllByIdGuestEqualsAndStatusEquals(guest, DeliveryStatus.ACCEPTED.name());
        List<Delivery> deliveryListCreated = deliveryRepository.findAllByIdGuestEqualsAndStatusEquals(guest, DeliveryStatus.CREATED.name());
        List<Delivery> deliveryListDelivered = deliveryRepository.findAllByIdGuestEqualsAndStatusEquals(guest, DeliveryStatus.DELIVERED.name());

        List<DeliveryDto> deliveryDtoList = new ArrayList<>();

        for(Delivery delivery: deliveryListAccepted){
            DeliveryDto deliveryDto = new DeliveryDto();

            deliveryDto.setId(delivery.getId());
            deliveryDto.setStatus(delivery.getStatus());
            deliveryDto.setRestaurantName(delivery.getIdRestaurant().getName());
            deliveryDto.setDateTime(delivery.getDateTime());
            deliveryDto.setCart(delivery.getCart());

            if(delivery.getDeliveryTime() != null){
                deliveryDto.setDeliveryTime(delivery.getDeliveryTime());
                Instant now = Instant.now();
                long minutesElapsed = ChronoUnit.MINUTES.between(delivery.getDateTime(), now);
                long minutesLeft = delivery.getDeliveryTime() - minutesElapsed;
                deliveryDto.setExpectedArrival(minutesLeft > 0 ? minutesLeft : 0);

            }


            deliveryDtoList.add(deliveryDto);
        }

        for(Delivery delivery: deliveryListCreated){
            DeliveryDto deliveryDto = new DeliveryDto();
            deliveryDto.setCart(delivery.getCart());

            deliveryDto.setId(delivery.getId());
            deliveryDto.setStatus(delivery.getStatus());
            deliveryDto.setRestaurantName(delivery.getIdRestaurant().getName());


            deliveryDtoList.add(deliveryDto);
        }

        for(Delivery delivery: deliveryListDelivered){
            DeliveryDto deliveryDto = new DeliveryDto();
            deliveryDto.setCart(delivery.getCart());

            deliveryDto.setId(delivery.getId());
            deliveryDto.setStatus(delivery.getStatus());
            deliveryDto.setRestaurantName(delivery.getIdRestaurant().getName());
            deliveryDto.setDateTime(delivery.getDateTime().plusSeconds(delivery.getDeliveryTime()));


            deliveryDtoList.add(deliveryDto);
        }

        for(DeliveryDto deliveryDto: deliveryDtoList){
            if(deliveryDto.getDateTime() == null){
                deliveryDto.setDateTime(Instant.MIN);
            }
        }

        sortDeliveriesByDate(deliveryDtoList);

        return deliveryDtoList;



    }

    public DeliveryDto createDelivery(User guest, Restaurant restaurant, String cart){
        Delivery delivery = new Delivery();
        delivery.setIdGuest(guest);
        delivery.setIdRestaurant(restaurant);
        delivery.setStatus(DeliveryStatus.CREATED.name());
        delivery.setCart(cart);

        delivery.setDateTime(Instant.now());
        Delivery delivery1 = deliveryRepository.save(delivery);

        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setId(delivery1.getId());
        deliveryDto.setStatus(delivery1.getStatus());

        return deliveryDto;
    }

    public List<Delivery> findAll(){
        return deliveryRepository.findAll();
    }

    public void save(Delivery delivery){
        deliveryRepository.save(delivery);
    }

    public void sortDeliveriesByDate(List<DeliveryDto> deliveries) {
        deliveries.sort((r1, r2) -> r2.getDateTime().compareTo(r1.getDateTime()));
    }


    public List<DeliveryDto> deliveriesForRestaurant(Restaurant restaurant){
       List<Delivery> deliveryList = deliveryRepository.findAllByIdRestaurantEqualsAndStatusEquals(restaurant, DeliveryStatus.CREATED.name());
       List<DeliveryDto> deliveryDtoList = new ArrayList<>();
        for(Delivery delivery: deliveryList){


            DeliveryDto deliveryDto = new DeliveryDto();
            deliveryDto.setId(delivery.getId());
            deliveryDto.setStatus(delivery.getStatus());
            deliveryDto.setRestaurantName(delivery.getIdRestaurant().getName());
            deliveryDto.setCart(delivery.getCart());
            deliveryDtoList.add(deliveryDto);
        }
        return deliveryDtoList;
    }

    public DeliveryDto confirmDelivery(Long deliveryTime, Long idDelivery) throws Exception {
        Delivery delivery = deliveryRepository.findById(idDelivery).isPresent() ? deliveryRepository.findById(idDelivery).get() : null;
        if(delivery != null){
            delivery.setDateTime(Instant.now());
            delivery.setDeliveryTime(deliveryTime);
            delivery.setStatus(DeliveryStatus.ACCEPTED.name());
            deliveryRepository.save(delivery);
            DeliveryDto deliveryDto = new DeliveryDto();
            deliveryDto.setDateTime(delivery.getDateTime());
            deliveryDto.setId(delivery.getId());
            deliveryDto.setDeliveryTime(deliveryTime);
            deliveryDto.setStatus(delivery.getStatus());
            deliveryDto.setRestaurantName(delivery.getIdRestaurant().getName());
            return deliveryDto;
        }
        throw new Exception();
    }

    public DeliveryDto refuseDelivery(Long idDelivery) throws Exception {
        Delivery delivery = deliveryRepository.findById(idDelivery).isPresent() ? deliveryRepository.findById(idDelivery).get() : null;
        if(delivery != null){
            delivery.setDateTime(Instant.now());
            delivery.setStatus(DeliveryStatus.REFUSED.name());
            deliveryRepository.save(delivery);
            DeliveryDto deliveryDto = new DeliveryDto();
            deliveryDto.setDateTime(delivery.getDateTime());
            deliveryDto.setId(delivery.getId());
            deliveryDto.setStatus(delivery.getStatus());
            deliveryDto.setRestaurantName(delivery.getIdRestaurant().getName());
            return deliveryDto;
        }
        throw new Exception();
    }

}
