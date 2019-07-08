package com.ludowica.waterqualityanalysisapi.repository;

import com.ludowica.waterqualityanalysisapi.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LocationRepo extends JpaRepository<Location, Integer> {
    @Query(value = "SELECT * FROM location l INNER JOIN water_info w ON l.id= w.location_id WHERE l.city = ?1 AND w.date BETWEEN ?2 AND ?3", nativeQuery = true)
    List<Location> findByCityAndDate(String city, Date startDate, Date endDate);
}

