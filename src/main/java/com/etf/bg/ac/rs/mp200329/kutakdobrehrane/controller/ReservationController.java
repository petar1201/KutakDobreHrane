package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.controller;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Reservation;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.User;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.*;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.dto.ReservationDto;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.dto.ReservationRestaurantDto;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request.CreateReservationRequest;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request.DeclineReservationRequest;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.response.Histogram;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.response.Pie;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.response.ReservationArchive;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.response.ReservationSummary;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    private final TableUsageService tableUsageService;
    private final ReservationService reservationService;
    private final RestaurantService restaurantService;
    private final UserService userService;
    private final WaiterRestaurantService waiterRestaurantService;

    @PostMapping("/create")
    public ResponseEntity<ReservationDto> createReservation(
            @RequestBody CreateReservationRequest createReservationRequest
            ){
        try{
            Reservation reservation = reservationService.createReservation(createReservationRequest);
            ReservationDto reservationDto = new ReservationDto(
                    reservation.getId(),
                    LocalDateTime.ofInstant(reservation.getDateTime(), ZoneId.systemDefault()),
                    reservation.getDescription(),
                    reservation.getStatus(),
                    reservation.getNumOfPeople()
            );
            return new ResponseEntity<>(reservationDto, HttpStatusCode.valueOf(200));
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(301));
        } catch (RestaurantNotWorkingException e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(302));
        } catch (RestaurantNotFoundException e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(303));
        }  catch (AlreadyBookedException e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(304));
        }
        catch (NoTableWithNeededCapacityException e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(305));
        }
        catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(306));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(307));
        }
    }


    @GetMapping("/accept/{idRes}/{idWaiter}/{tableNum}")
    public ResponseEntity<ReservationRestaurantDto> acceptReservation(
            @PathVariable Long idRes,
            @PathVariable Long idWaiter,
            @PathVariable Long tableNum

            ){
        try {
            Reservation reservation = reservationService.acceptReservation(idRes, idWaiter, tableNum);
            ReservationRestaurantDto reservationDto = new ReservationRestaurantDto(
                    reservation.getId(),
                    LocalDateTime.ofInstant(reservation.getDateTime(), ZoneId.systemDefault()),
                    reservation.getDescription(),
                    reservation.getStatus(),
                    reservation.getIdRestaurant().getName(),
                    reservation.getIdRestaurant().getAddress(),
                    reservation.getNumOfPeople(),
                    reservation.getIdRestaurant().getId(),
                    reservation.getIdRestaurant().getRestaurantLayout()
            );
            return new ResponseEntity<>(reservationDto, HttpStatusCode.valueOf(200));
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(301));
        } catch (ReservationNotFoundException e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(302));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(303));

        }
    }

    @PostMapping("/decline")
    public ResponseEntity<ReservationRestaurantDto> declineReservation(
            @RequestBody DeclineReservationRequest declineReservationRequest
            ){
        try{
            Reservation reservation = reservationService.declineReservation(
                    declineReservationRequest.getIdReservation(),
                    declineReservationRequest.getIdWaiter(),
                    declineReservationRequest.getDescription()
                    );
            ReservationRestaurantDto reservationDto = new ReservationRestaurantDto(
                    reservation.getId(),
                    LocalDateTime.ofInstant(reservation.getDateTime(), ZoneId.systemDefault()),
                    reservation.getDescription(),
                    reservation.getStatus(),
                    reservation.getIdRestaurant().getName(),
                    reservation.getIdRestaurant().getAddress(),
                    reservation.getNumOfPeople(),
                    reservation.getIdRestaurant().getId(),
                    reservation.getIdRestaurant().getRestaurantLayout()
            );
            return new ResponseEntity<>(reservationDto, HttpStatusCode.valueOf(200));
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(301));
        } catch (ReservationNotFoundException e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(302));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(303));
        }
    }

    @GetMapping("/show/confirm/true/{idRes}")
    public ResponseEntity<ReservationDto> confrimShowUp(
            @PathVariable Long idRes
    ){
        try {
            Reservation reservation = reservationService.confirmShowingUp(idRes);
            ReservationDto reservationDto = new ReservationDto(
                    reservation.getId(),
                    LocalDateTime.ofInstant(reservation.getDateTime(), ZoneId.systemDefault()),
                    reservation.getDescription(),
                    reservation.getStatus(),
                    reservation.getNumOfPeople()
            );
            return new ResponseEntity<>(reservationDto, HttpStatusCode.valueOf(200));

        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(301));
        } catch (ReservationNotFoundException e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(302));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(303));
        }
    }

    @GetMapping("/show/confirm/false/{idRes}")
    public ResponseEntity<ReservationDto> confirmNotShowingUp(
            @PathVariable Long idRes
    ){
        try {
            Reservation reservation = reservationService.confirmNotShowingUp(idRes);
            ReservationDto reservationDto = new ReservationDto(
                    reservation.getId(),
                    LocalDateTime.ofInstant(reservation.getDateTime(), ZoneId.systemDefault()),
                    reservation.getDescription(),
                    reservation.getStatus(),
                    reservation.getNumOfPeople()
            );
            return new ResponseEntity<>(reservationDto, HttpStatusCode.valueOf(200));

        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(301));
        } catch (ReservationNotFoundException e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(302));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(303));
        }
    }


    @GetMapping("/tables/{idRes}")
    public ResponseEntity<HashMap<Long, Integer>> getTables(
            @PathVariable Long idRes
    ){
        try {
            Reservation reservation = reservationService.findById(idRes);
            if(reservation == null){
                return new ResponseEntity<>(HttpStatusCode.valueOf(303));
            }
            return new ResponseEntity<>(tableUsageService.getTablesForReservation(reservation), HttpStatusCode.valueOf(200));
        }catch (AlreadyBookedException e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(301));
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(302));
        }
    }

    @GetMapping("/summary")
    public ReservationSummary getSummary(){
        return reservationService.getSummary();
    }

    @GetMapping("/archive/{idGuest}")
    public ReservationArchive getArchiveForGuest(
            @PathVariable Long idGuest
    ){
        return reservationService.getArchive(idGuest);
    }


    @GetMapping("/created/restaurant/{idRes}")
    public List<ReservationRestaurantDto> getCreatedForRestaurant(
            @PathVariable Long idRes
    ){
        return reservationService.findCreatedForRestaurant(restaurantService.findById(idRes));
    }

    @GetMapping("/accepted/waiter/{idWaiter}")
    public List<ReservationRestaurantDto> getAcceptedForWaiter(
            @PathVariable Long idWaiter){
        return reservationService.getAcceptedForWaiter(idWaiter);
    }


    @GetMapping("histogram")
    public Histogram histogram(){
        return reservationService.getHistogram();
    }
    @GetMapping("bar/{idWaiter}")
    public Histogram bar(
            @PathVariable Long idWaiter
    ){
        return reservationService.gett(idWaiter);
    }

    @GetMapping("pie/{idUser}")
    public List<Pie> mapa(@PathVariable Long idUser){

        try {
            return reservationService.guestPerWaiter(waiterRestaurantService.getWaitersIds(waiterRestaurantService.getByUser(userService.findById(idUser))));
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
