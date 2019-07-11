package com.ludowica.waterqualityanalysisapi.repository;

import com.ludowica.waterqualityanalysisapi.models.Location;
import com.ludowica.waterqualityanalysisapi.models.WaterInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface WaterInfoRepo extends JpaRepository<WaterInfo, Integer> {
    Optional<List<WaterInfo>> findAllByLocationCityAndDateBetween(String locationCity, Date start, Date end);
    Optional<List<WaterInfo>> findAllByLocationNameAndDateBetween(String locationName, Date start, Date end);
}
