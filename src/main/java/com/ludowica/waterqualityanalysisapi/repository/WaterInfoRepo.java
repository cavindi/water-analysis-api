package com.ludowica.waterqualityanalysisapi.repository;

import com.ludowica.waterqualityanalysisapi.models.Location;
import com.ludowica.waterqualityanalysisapi.models.WaterInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaterInfoRepo extends JpaRepository<WaterInfo, Integer> {
    List<WaterInfo> findAllByOrderByDateAsc();
}
