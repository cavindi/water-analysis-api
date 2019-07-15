package com.ludowica.waterqualityanalysisapi.services;

import com.ludowica.waterqualityanalysisapi.exception.ResourceNotFoundException;
import com.ludowica.waterqualityanalysisapi.forms.*;
import com.ludowica.waterqualityanalysisapi.models.WaterInfo;
import com.ludowica.waterqualityanalysisapi.repository.LocationRepo;
import com.ludowica.waterqualityanalysisapi.repository.WaterInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WaterInfoService {

    @Autowired
    WaterInfoRepo waterInfoRepo;

    @Autowired
    LocationRepo locationRepo;

    @Autowired
    MapService mapService;

    public WaterInfo addOrUpdate(WaterInfo waterInfo) {
        waterInfo.setDate(retrieveDate());
        return waterInfoRepo.save(waterInfo);
    }

    public WaterInfo addFromWorkBook(WaterInfo waterInfo) {
        return waterInfoRepo.save(waterInfo);
    }

    public List<WaterInfo> getAll() {
        return waterInfoRepo.findAll();
    }

    public void initMap() {
        List<WaterInfo> waterInfoList = getAll();
        mapService.initMap(waterInfoList);
    }

    public ChartColumn getChartColumn(ChartColumnFilter chartColumnFilter) {
        List<WaterInfo> waterInfoList = waterInfoRepo.
                findAllByLocationCityAndDateBetween(chartColumnFilter.getCity(), chartColumnFilter.getDateStart(), chartColumnFilter.getDateEnd())
                .orElseThrow(() -> new ResourceNotFoundException("Data not found for this City and Date :: " + chartColumnFilter.getCity()));

        int total = waterInfoList.size();
        ChartColumn chartColumn = new ChartColumn();

        for (WaterInfo waterInfo : waterInfoList) {

            if (6.5 <= waterInfo.getpH() && waterInfo.getpH() <= 8.5) {
                double current = chartColumn.getpH();
                current++;
                chartColumn.setpH(current);
            }

            if (waterInfo.getColour() <= 15) {
                double current = chartColumn.getColour();
                current++;
                chartColumn.setColour(current);
            }

            if (waterInfo.getTurbidity() <= 2) {
                double current = chartColumn.getTurbidity();
                current++;
                chartColumn.setTurbidity(current);
            }

            if (waterInfo.getRCL() <= 1) {
                double current = chartColumn.getRCL();
                current++;
                chartColumn.setRCL(current);
            }

        }

        double ph = (chartColumn.getpH() / total) * 100;
        double colour = (chartColumn.getColour() / total) * 100;
        double turbidity = (chartColumn.getTurbidity() / total) * 100;
        double rcl = (chartColumn.getRCL() / total) * 100;

        chartColumn.setpH(ph);
        chartColumn.setColour(colour);
        chartColumn.setTurbidity(turbidity);
        chartColumn.setRCL(rcl);

        return chartColumn;
    }

    public double calculateWaterQuality(ChartColumnFilter filter) {

        List<WaterInfo> list = waterInfoRepo.
                findAllByLocationCityAndDateBetween(filter.getCity(), filter.getDateStart(), filter.getDateEnd())
                .orElseThrow(() -> new ResourceNotFoundException("Data not found for this City and Date :: " + filter.getCity()));

        int total = list.size();
        ChartColumn chartColumn = new ChartColumn();
        ChartWaterQuality chartWaterQuality = new ChartWaterQuality();

        for (WaterInfo waterInfo : list) {

            if (6.5 <= waterInfo.getpH() && waterInfo.getpH() <= 8.5) {
                double current = chartColumn.getpH();
                current++;
                chartColumn.setpH(current);
            }

            if (waterInfo.getColour() <= 15) {
                double current = chartColumn.getColour();
                current++;
                chartColumn.setColour(current);
            }

            if (waterInfo.getTurbidity() <= 2) {
                double current = chartColumn.getTurbidity();
                current++;
                chartColumn.setTurbidity(current);
            }

            if (waterInfo.getRCL() <= 1) {
                double current = chartColumn.getRCL();
                current++;
                chartColumn.setRCL(current);
            }
        }

        double ph = (chartColumn.getpH() / total) * 100;
        double colour = (chartColumn.getColour() / total) * 100;
        double turbidity = (chartColumn.getTurbidity() / total) * 100;
        double rcl = (chartColumn.getRCL() / total) * 100;

        double waterQuality = (ph + colour + turbidity + rcl) / 4;

        return waterQuality;
    }

    public ChartLine getChartLine(ChartColumnFilter chartFilter) {

        List<WaterInfo> waterInfoList = waterInfoRepo.
                findAllByLocationNameAndDateBetween(chartFilter.getCity(), chartFilter.getDateStart(), chartFilter.getDateEnd())
                .orElseThrow(() -> new ResourceNotFoundException("Data not found for this City and Date :: " + chartFilter.getCity()));

        ChartLine chartLine = new ChartLine();

        List<Integer> RCL = new ArrayList<>();
        List<Integer> colour = new ArrayList<>();
        List<Integer> turbidity = new ArrayList<>();
        List<Integer> pH = new ArrayList<>();
        List<Date> date = new ArrayList<>();

        for (WaterInfo waterInfo : waterInfoList) {

            if (6.5 <= waterInfo.getpH() && waterInfo.getpH() <= 8.5) {
                pH.add(1);
            } else {
                pH.add(0);
            }


            if (waterInfo.getColour() <= 15) {
                colour.add(1);
            } else {
                colour.add(0);
            }


            if (waterInfo.getTurbidity() <= 2) {
                turbidity.add(1);
            } else {
                turbidity.add(0);
            }


            if (waterInfo.getRCL() <= 1) {
                RCL.add(1);
            } else {
                RCL.add(0);
            }

            chartLine.setName(waterInfo.getLocation().getName());
            date.add(waterInfo.getDate());
        }

        chartLine.setDate(date);
        chartLine.setpH(pH);
        chartLine.setColour(colour);
        chartLine.setRCL(RCL);
        chartLine.setTurbidity(turbidity);

        return chartLine;
    }

    public List<WaterInfo> getWaterInfoByDateAndCity(ChartColumnFilter chartColumnFilter) {
        List<WaterInfo> waterInfoList = waterInfoRepo.
                findAllByLocationCityAndDateBetween(chartColumnFilter.getCity(), chartColumnFilter.getDateStart(), chartColumnFilter.getDateEnd())
                .orElseThrow(() -> new ResourceNotFoundException("Data not found for this City and Date :: " + chartColumnFilter.getCity()));
        return waterInfoList;
    }

    public List<WaterInfo> getWaterDateByLocationAndDate(ChartColumnFilter chartColumnFilter) {
        List<WaterInfo> waterInfoList = waterInfoRepo.
                findAllByLocationNameAndDate(chartColumnFilter.getName(), chartColumnFilter.getDate())
                .orElseThrow(() -> new ResourceNotFoundException("Data not found for this Location and Date :: " + chartColumnFilter.getName()));
        return waterInfoList;
    }

    private Date retrieveDate() {

        Date date = new Date();

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String strDate = df.format(date);

        Date newDate = null;
        try {
            newDate = df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newDate;
    }
}
