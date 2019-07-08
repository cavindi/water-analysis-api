package com.ludowica.waterqualityanalysisapi.services;

import com.ludowica.waterqualityanalysisapi.exception.ResourceNotFoundException;
import com.ludowica.waterqualityanalysisapi.forms.ChartColumn;
import com.ludowica.waterqualityanalysisapi.forms.ChartColumnFilter;
import com.ludowica.waterqualityanalysisapi.models.WaterInfo;
import com.ludowica.waterqualityanalysisapi.repository.LocationRepo;
import com.ludowica.waterqualityanalysisapi.repository.WaterInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public double calculateWaterQuality(ChartColumnFilter filter){
        List<WaterInfo> list = waterInfoRepo.
                findAllByLocationCityAndDateBetween(filter.getCity(), filter.getDateStart(), filter.getDateEnd())
                .orElseThrow(() -> new ResourceNotFoundException("Data not found for this City and Date :: " + filter.getCity()));

        int total = list.size();
        ChartColumn chartColumn = new ChartColumn();

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

        double waterQuality = (ph + colour + turbidity + rcl)/4;

        return waterQuality;

    }

    public void populateChartWQ(ChartColumnFilter filter){

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
