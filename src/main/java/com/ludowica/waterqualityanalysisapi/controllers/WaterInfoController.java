package com.ludowica.waterqualityanalysisapi.controllers;

import com.ludowica.waterqualityanalysisapi.forms.ChartColumn;
import com.ludowica.waterqualityanalysisapi.forms.ChartColumnFilter;
import com.ludowica.waterqualityanalysisapi.forms.ChartLine;
import com.ludowica.waterqualityanalysisapi.forms.ChartPie;
import com.ludowica.waterqualityanalysisapi.models.WaterInfo;
import com.ludowica.waterqualityanalysisapi.repository.WaterInfoRepo;
import com.ludowica.waterqualityanalysisapi.services.WaterInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/water-info")
public class WaterInfoController {

    @Autowired
    WaterInfoRepo repo;

    @Autowired
    WaterInfoService waterInfoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        Optional<WaterInfo> waterInfo = repo.findById(id);
        return new ResponseEntity<>(waterInfo, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<WaterInfo> waterInfoList = waterInfoService.getAll();
        return new ResponseEntity<>(waterInfoList, HttpStatus.OK);
    }

    @GetMapping("/init-map")
    public ResponseEntity<?> initMap() {
        waterInfoService.initMap();
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addOrUpdate(@RequestBody WaterInfo waterInfo) {
        WaterInfo savedWaterInfo = waterInfoService.addOrUpdate(waterInfo);
        return new ResponseEntity<>(savedWaterInfo, HttpStatus.OK);
    }


    @PostMapping("/chart-column")
    public ResponseEntity<?> findByCityAndDate(@RequestBody ChartColumnFilter chartColumnFilter) {
        ChartColumn chartColumn = waterInfoService.getChartColumn(chartColumnFilter);
        return new ResponseEntity<>(chartColumn, HttpStatus.OK);
    }

    @PostMapping("/chart-pie")
    public ResponseEntity<?> createPieChart(@RequestBody ChartColumnFilter chartColumnFilter) {
        ChartPie chartPie = waterInfoService.getChartPie(chartColumnFilter);
        return new ResponseEntity<>(chartPie, HttpStatus.OK);
    }

    @PostMapping("/chart-line")
    public ResponseEntity<?> createLineChart(@RequestBody ChartColumnFilter chartColumnFilter) {
        ChartLine chartLine = waterInfoService.getChartLine(chartColumnFilter);
        return new ResponseEntity<>(chartLine, HttpStatus.OK);
    }

    @PostMapping("/water-quality")
    public ResponseEntity<?> calcWaterQuality(@RequestBody ChartColumnFilter filter) {
        double column = waterInfoService.calculateWaterQuality(filter);
        return new ResponseEntity<>(column, HttpStatus.OK);
    }

    @PostMapping("/water-quality-list")
    public ResponseEntity<?> findWaterInfoByDateAndCity(@RequestBody ChartColumnFilter filter) {
        List<WaterInfo> waterInfoList = waterInfoService.getWaterInfoByDateAndCity(filter);
        return new ResponseEntity<>(waterInfoList, HttpStatus.OK);
    }


}
