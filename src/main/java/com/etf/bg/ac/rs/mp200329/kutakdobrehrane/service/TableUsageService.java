package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Reservation;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Restaurant;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.TableUsage;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.AlreadyBookedException;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.restaurant.RestaurantLayout;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.restaurant.Table;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.repository.TableUsageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TableUsageService {

    private final TableUsageRepository tableUsageRepository;

    public HashMap<Long, Integer> isItFree(Restaurant restaurant, RestaurantLayout restaurantLayout, Long numOfPersons, LocalDateTime datee,Long start, boolean ret ) throws AlreadyBookedException {
        List<TableUsage> tableUsageList = tableUsageRepository.findAll();
        HashMap<Long, Integer> mapa = new HashMap<>();
        long couner = 0;
        for(Table t : restaurantLayout.getTables()){
            mapa.put(couner, t.getCapacity());
            couner++;
        };
        HashMap<Long, Integer> freeMapa = new HashMap<>();

        for(Long key: mapa.keySet()){
            boolean flag = true;
            for(TableUsage tu: tableUsageList){
                if(Objects.equals(tu.getIdRestaurant().getId(), restaurant.getId())
                   && Objects.equals(tu.getTableNum(), key)
                    && datee.getYear() == tu.getDatum().toLocalDate().getYear()
                     && datee.getDayOfYear() == tu.getDatum().toLocalDate().getDayOfYear()
                      &&(
                        (start < tu.getStartHour() && start + 3 >= tu.getStartHour()) ||
                        (start > tu.getStartHour() && start <= tu.getStartHour() + 3) ||
                        (start.equals(tu.getStartHour()))
                        )
                ){
                    flag = false;
                    break;
                }
            }
            if(flag) {
                freeMapa.put(key, mapa.get(key));
            }
        }

        if(ret){
            return freeMapa;
        }

        if(freeMapa.isEmpty()){
            throw new AlreadyBookedException();
        }
        for(Long key: freeMapa.keySet()){
            if(numOfPersons <= freeMapa.get(key)){
                return freeMapa;
            }
        }
        throw new AlreadyBookedException();
    }

    public TableUsage createNew(Reservation reservation, Restaurant restaurant, Long tableNum, LocalDateTime datum){
        Long startHOur = (long) datum.getHour();
        TableUsage tableUsage = new TableUsage();
        tableUsage.setTableNum(tableNum);
        tableUsage.setIdReservation(reservation);
        tableUsage.setIdRestaurant(restaurant);
        tableUsage.setStartHour(startHOur);
        tableUsage.setDatum(Date.valueOf(datum.toLocalDate()));
        return tableUsageRepository.save(tableUsage);
    }

    public void deleteTableUsage(Reservation reservation){

        TableUsage tableUsage = tableUsageRepository.findTableUsageByIdReservationEquals(reservation);
        tableUsageRepository.delete(tableUsage);

    }

    public HashMap<Long, Integer> getTablesForReservation(Reservation reservation) throws JsonProcessingException, AlreadyBookedException {
        ObjectMapper objectMapper = new ObjectMapper();
        RestaurantLayout restaurantLayout = objectMapper.readValue(reservation.getIdRestaurant().getRestaurantLayout(), RestaurantLayout.class);

        HashMap<Long, Integer> freeMapa = isItFree(
                reservation.getIdRestaurant(),
                restaurantLayout,
                reservation.getNumOfPeople(),

                LocalDateTime.ofInstant(reservation.getDateTime(), ZoneId.systemDefault()),
                (long)  LocalDateTime.ofInstant(reservation.getDateTime(), ZoneId.systemDefault()).getHour(),
                true
        );

        HashMap<Long, Integer> mapa = new HashMap<>();
        long couner = 0;
        for(Table t : restaurantLayout.getTables()){
            mapa.put(couner, t.getCapacity());
            couner++;
        };
        for(Long key: mapa.keySet()){
            if(!freeMapa.containsKey(key)){
                freeMapa.put(key, -1);
            }
        }

        return freeMapa;
    }

}
