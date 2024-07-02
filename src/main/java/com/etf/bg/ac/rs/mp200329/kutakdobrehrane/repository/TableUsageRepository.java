package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.repository;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Reservation;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.TableUsage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableUsageRepository extends JpaRepository<TableUsage, Long> {

    public TableUsage findTableUsageByIdReservationEquals(Reservation idReservation);

}
