package com.ludowica.waterqualityanalysisapi.forms;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ChartColumnFilter {

    private String city;
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "Asia/Colombo")
    private Date dateStart;
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "Asia/Colombo")
    private Date dateEnd;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
}
