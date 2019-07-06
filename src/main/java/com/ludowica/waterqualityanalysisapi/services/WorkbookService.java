package com.ludowica.waterqualityanalysisapi.services;

import com.ludowica.waterqualityanalysisapi.models.Location;
import com.ludowica.waterqualityanalysisapi.models.WaterInfo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class WorkbookService {

    @Autowired
    LocationService locationService;

    @Autowired
    WaterInfoService waterInfoService;

    public void readExcel() {

        File file = null;

        try {
            file = new ClassPathResource("water_data.xlsx").getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (file.exists()) {
            System.out.println("File exists");
            populateData(file);
        } else {
            System.out.println("File doesn't exist");
        }

    }

    public void populateData(File file) {

        Workbook workbook = null;

        try {
            workbook = WorkbookFactory.create(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();


        for (Row row : sheet) {
            WaterInfo waterInfo = new WaterInfo();

            if (row.getRowNum() != 0) {

                String cellArea = dataFormatter.formatCellValue(row.getCell(1));
                String cellCity = dataFormatter.formatCellValue(row.getCell(2));
                String cellLocation = dataFormatter.formatCellValue(row.getCell(3));
                String cellDate = dataFormatter.formatCellValue(row.getCell(4));
                String cellRCL = dataFormatter.formatCellValue(row.getCell(5));
                String cellColour = dataFormatter.formatCellValue(row.getCell(6));
                String cellTurbidity = dataFormatter.formatCellValue(row.getCell(7));
                String cellPH = dataFormatter.formatCellValue(row.getCell(8));
                String cellEC = dataFormatter.formatCellValue(row.getCell(9));

                Location location = getLocation(cellLocation);
                location.setName(cellLocation);
                location.setCity(cellCity);

                waterInfo.setRCL(Double.parseDouble(cellRCL));
                waterInfo.setColour(Double.parseDouble(cellColour));
                waterInfo.setTurbidity(Double.parseDouble(cellTurbidity));
                waterInfo.setpH(Double.parseDouble(cellPH));
                waterInfo.setEC(Double.parseDouble(cellEC));

                waterInfo.setLocation(location);

                if (location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
                    waterInfoService.addFromWorkBook(waterInfo);
                } else {
                    System.out.println("Not Found " + location.getName());
                }
            }

        }

        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Location getLocation(String name) {
        Location location = new Location();
        location.setName(name);

        return locationService.getLatLong(location);
    }

}
