package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Reservation;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Restaurant;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.User;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.*;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.dto.ReservationDto;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.dto.ReservationRestaurantDto;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.enums.ReservationStatus;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request.CreateReservationRequest;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.response.ReservationArchive;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.response.ReservationSummary;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.restaurant.RestaurantLayout;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.repository.ReservationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final RestaurantService restaurantService;
    private final TableUsageService tableUsageService;


    public Reservation createReservation(CreateReservationRequest createReservationRequest) throws UserNotFoundException, RestaurantNotFoundException, JsonProcessingException, RestaurantNotWorkingException, AlreadyBookedException {
        User guest = userService.findById(createReservationRequest.getIdGuest());
        Restaurant restaurant = restaurantService.findById(createReservationRequest.getIdRestaurant());
        if(guest == null){
            throw new UserNotFoundException();
        }
        if(restaurant == null){
            throw new RestaurantNotFoundException();
        }


        String[] rs = restaurant.getWorkingHours().split("\\|", 2);
        String d = rs[0];
        String h = rs[1];
        String startDay_ = d.split("-")[0];
        String endDay_ = d.split("-")[1];
        String startHour_ = h.split("-")[0];
        String endHour_ = h.split("-")[1];
        Long startHour = Long.parseLong(startHour_);
        Long endHour = Long.parseLong(endHour_);
        HashMap<String, DayOfWeek> dayOfWeekMap = new HashMap<>();
        dayOfWeekMap.put("MON", DayOfWeek.MONDAY);
        dayOfWeekMap.put("TUE", DayOfWeek.TUESDAY);
        dayOfWeekMap.put("WED", DayOfWeek.WEDNESDAY);
        dayOfWeekMap.put("THU", DayOfWeek.THURSDAY);
        dayOfWeekMap.put("FRI", DayOfWeek.FRIDAY);
        dayOfWeekMap.put("SAT", DayOfWeek.SATURDAY);
        dayOfWeekMap.put("SUN", DayOfWeek.SUNDAY);
        LocalDateTime date = LocalDateTime.ofInstant( createReservationRequest.getDateTime(), ZoneId.systemDefault());
        int dow = date.getDayOfWeek().getValue();
        int hh = date.getHour();
        if(dow < dayOfWeekMap.get(startDay_).getValue() || dow > dayOfWeekMap.get(endDay_).getValue()){
            throw new RestaurantNotWorkingException();

        }
        if(hh < startHour || hh + 3 > endHour){
            throw new RestaurantNotWorkingException();
        }


        ObjectMapper mapper = new ObjectMapper();
        RestaurantLayout restaurantLayout = mapper.readValue(restaurant.getRestaurantLayout(), RestaurantLayout.class);

        if(!tableUsageService.isItFree(restaurant, restaurantLayout, createReservationRequest.getNumOfPersons(),date, (long) hh, false).isEmpty()) {
            Reservation reservation = new Reservation();
            reservation.setStatus(ReservationStatus.CREATED.name());
            reservation.setIdGuest(guest);
            reservation.setIdRestaurant(restaurant);
            reservation.setDescription(createReservationRequest.getDescription());
            reservation.setDateTime(createReservationRequest.getDateTime());
            reservation.setNumOfPeople(createReservationRequest.getNumOfPersons());
            return reservationRepository.save((reservation));
        }
        throw new AlreadyBookedException();
    }

    public Reservation acceptReservation(Long idReservation, Long idWaiter, Long tableNum) throws ReservationNotFoundException, UserNotFoundException {
        Reservation reservation = reservationRepository.findById(idReservation).isPresent() ? reservationRepository.findById(idReservation).get() : null;
        User waiter = userService.findById(idWaiter);
        if(reservation == null){
            throw new ReservationNotFoundException();
        }
        if(waiter == null){
            throw new UserNotFoundException();
        }
        reservation.setIdWaiter(waiter);
        reservation.setStatus(ReservationStatus.ACCEPTED.name());
        Reservation reservation1 = reservationRepository.save(reservation);
        tableUsageService.createNew(reservation, reservation.getIdRestaurant(), tableNum,LocalDateTime.ofInstant( reservation.getDateTime(), ZoneId.systemDefault()));
        return reservation1;
    }

    public Reservation declineReservation(Long idReservation, Long idWaiter, String desc) throws ReservationNotFoundException, UserNotFoundException {
        Reservation reservation = reservationRepository.findById(idReservation).isPresent() ? reservationRepository.findById(idReservation).get() : null;
        User waiter = userService.findById(idWaiter);
        if (reservation == null) {
            throw new ReservationNotFoundException();
        }
        if (waiter == null) {
            throw new UserNotFoundException();
        }
        reservation.setIdWaiter(waiter);
        reservation.setStatus(ReservationStatus.DECLINED.name());
        reservation.setDescription(desc);
        return reservationRepository.save(reservation);
    }

    public Reservation confirmShowingUp(Long idReservation) throws ReservationNotFoundException, UserNotFoundException {
        Reservation reservation = reservationRepository.findById(idReservation).isPresent() ? reservationRepository.findById(idReservation).get() : null;
        if (reservation == null) {
            throw new ReservationNotFoundException();
        }
        reservation.setStatus(ReservationStatus.SHOWED_UP.name());
        return reservationRepository.save(reservation);
    }

    public Reservation confirmNotShowingUp(Long idReservation) throws ReservationNotFoundException, UserNotFoundException {
        Reservation reservation = reservationRepository.findById(idReservation).isPresent() ? reservationRepository.findById(idReservation).get() : null;
        if (reservation == null) {
            throw new ReservationNotFoundException();
        }
        reservation.setStatus(ReservationStatus.DIDNT_SHOW_UP.name());
        tableUsageService.deleteTableUsage(reservation);
        return reservationRepository.save(reservation);
    }

    public Reservation expiredReservation(Long idReservation) throws ReservationNotFoundException, UserNotFoundException {
        Reservation reservation = reservationRepository.findById(idReservation).isPresent() ? reservationRepository.findById(idReservation).get() : null;
        if (reservation == null) {
            throw new ReservationNotFoundException();
        }
        reservation.setStatus(ReservationStatus.EXPIRED.name());
        return reservationRepository.save(reservation);
    }

    public List<Reservation> findAll(){
        return reservationRepository.findAll();
    }

    public List<Reservation> findAllCreated(){
        return reservationRepository.findAllByStatusEquals(ReservationStatus.CREATED.name());
    }


    public Reservation findById(Long idRes){
        return reservationRepository.findById(idRes).isPresent() ? reservationRepository.findById(idRes).get() : null;
    }

    public ReservationSummary getSummary(){
        long countDay = 0;
        long countWeek = 0;
        long countMonth = 0;
        Instant now = Instant.now();

        Instant last24Hours = now.minus(24, ChronoUnit.HOURS);
        Instant last7Days = now.minus(7, ChronoUnit.DAYS);
        Instant lastMonth = now.minus(30, ChronoUnit.DAYS);

        for(Reservation reservation : reservationRepository.findAllByStatusEquals(ReservationStatus.ACCEPTED.name())){
            Instant reservationTime = reservation.getDateTime();

            if (reservationTime.isAfter(last24Hours)) {
                countDay++;
            }

            if (reservationTime.isAfter(last7Days)) {
               countWeek++;
            }

            if (reservationTime.isAfter(lastMonth)) {
                countMonth++;
            }
        }

        ReservationSummary reservationSummary =  new ReservationSummary();
        reservationSummary.setLastDay(countDay);
        reservationSummary.setLastWeek(countWeek);
        reservationSummary.setLastMonth(countMonth);
        return reservationSummary;
    }

    public ReservationArchive getArchive(Long idGuest){
        List<Reservation> activeEntityList = reservationRepository.findAllByStatusEquals(ReservationStatus.ACCEPTED.name());
        List<Reservation> createedEntityList = reservationRepository.findAllByStatusEquals(ReservationStatus.CREATED.name());
        List<Reservation> showedEntityList = reservationRepository.findAllByStatusEquals(ReservationStatus.SHOWED_UP.name());

        List<Reservation> declinedEntityList = reservationRepository.findAllByStatusEquals(ReservationStatus.DECLINED.name());
        List<Reservation> notshowedEntityList = reservationRepository.findAllByStatusEquals(ReservationStatus.DIDNT_SHOW_UP.name());

        List<Reservation> expiredEntityList = reservationRepository.findAllByStatusEquals(ReservationStatus.EXPIRED.name());

        List<ReservationRestaurantDto> activeList = new ArrayList<>();
        List<ReservationDto> expired = new ArrayList<>();
        List<ReservationDto> refused = new ArrayList<>();

        for(Reservation reservation: activeEntityList){
            if(!reservation.getIdGuest().getId().equals(idGuest)){
                continue;
            }
            ReservationRestaurantDto reservationDto = new ReservationRestaurantDto(
                    reservation.getId(),
                    LocalDateTime.ofInstant(reservation.getDateTime(), ZoneId.systemDefault()),
                    reservation.getDescription(),
                    reservation.getStatus(),
                    reservation.getIdRestaurant().getName(),
                    reservation.getIdRestaurant().getAddress(),
                    reservation.getNumOfPeople()
            );
            activeList.add(reservationDto);
        }

        for(Reservation reservation: createedEntityList){
            if(!reservation.getIdGuest().getId().equals(idGuest)){
                continue;
            }
            ReservationRestaurantDto reservationDto = new ReservationRestaurantDto(
                    reservation.getId(),
                    LocalDateTime.ofInstant(reservation.getDateTime(), ZoneId.systemDefault()),
                    reservation.getDescription(),
                    reservation.getStatus(),
                    reservation.getIdRestaurant().getName(),
                    reservation.getIdRestaurant().getAddress(),
                    reservation.getNumOfPeople()
            );
//            activeList.add(reservationDto);
        }

        for(Reservation reservation: showedEntityList){
            if(!reservation.getIdGuest().getId().equals(idGuest)){
                continue;
            }
            ReservationRestaurantDto reservationDto = new ReservationRestaurantDto(
                    reservation.getId(),
                    LocalDateTime.ofInstant(reservation.getDateTime(), ZoneId.systemDefault()),
                    reservation.getDescription(),
                    reservation.getStatus(),
                    reservation.getIdRestaurant().getName(),
                    reservation.getIdRestaurant().getAddress(),
                    reservation.getNumOfPeople()
            );
            activeList.add(reservationDto);
        }

        for(Reservation reservation: declinedEntityList){
            if(!reservation.getIdGuest().getId().equals(idGuest)){
                continue;
            }
            ReservationDto reservationDto = new ReservationDto(
                    reservation.getId(),
                    LocalDateTime.ofInstant(reservation.getDateTime(), ZoneId.systemDefault()),
                    reservation.getDescription(),
                    reservation.getStatus(),
                    reservation.getNumOfPeople()
            );
            refused.add(reservationDto);
        }

        for(Reservation reservation: notshowedEntityList){
            if(!reservation.getIdGuest().getId().equals(idGuest)){
                continue;
            }
            ReservationDto reservationDto = new ReservationDto(
                    reservation.getId(),
                    LocalDateTime.ofInstant(reservation.getDateTime(), ZoneId.systemDefault()),
                    reservation.getDescription(),
                    reservation.getStatus(),
                    reservation.getNumOfPeople()
            );
            refused.add(reservationDto);
        }

        for(Reservation reservation: expiredEntityList){
            if(!reservation.getIdGuest().getId().equals(idGuest)){
                continue;
            }
            ReservationDto reservationDto = new ReservationDto(
                    reservation.getId(),
                    LocalDateTime.ofInstant(reservation.getDateTime(), ZoneId.systemDefault()),
                    reservation.getDescription(),
                    reservation.getStatus(),
                    reservation.getNumOfPeople()
            );
            expired.add(reservationDto);
        }


        sortReservationsWithRestaurantByDate(activeList);
        sortReservationsByDate(expired);
        sortReservationsByDate(refused);

        ReservationArchive reservationArchive = new ReservationArchive();
        reservationArchive.setActive(activeList);
        reservationArchive.setRefused(refused);
        reservationArchive.setExpired(expired);

        return reservationArchive;
    }

    public void sortReservationsByDate(List<ReservationDto> reservations) {
        reservations.sort((r1, r2) -> r2.getDateTime().compareTo(r1.getDateTime()));
    }

    public void sortReservationsWithRestaurantByDate(List<ReservationRestaurantDto> reservations) {
        reservations.sort((r1, r2) -> r2.getDateTime().compareTo(r1.getDateTime()));
    }


    public List<ReservationRestaurantDto> findCreatedForRestaurant(Restaurant restaurant){
        List<Reservation> reservationList = reservationRepository.findAllByIdRestaurantEqualsAndStatusEquals(restaurant, ReservationStatus.CREATED.name());
        List<ReservationRestaurantDto> reservationDtoList= new ArrayList<>();
        reservationList.forEach(reservation ->{
            ReservationRestaurantDto reservationDto = new ReservationRestaurantDto(
                    reservation.getId(),
                    LocalDateTime.ofInstant(reservation.getDateTime(), ZoneId.systemDefault()),
                    reservation.getDescription(),
                    reservation.getStatus(),
                    restaurant.getName(),
                    restaurant.getAddress(),
                    reservation.getNumOfPeople()
            );
            sortReservationsWithRestaurantByDate(reservationDtoList);
            reservationDtoList.add(reservationDto);
        });
        return reservationDtoList;
    }

}
