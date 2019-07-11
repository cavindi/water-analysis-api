package com.ludowica.waterqualityanalysisapi.forms;

import java.util.Date;
import java.util.List;

public class ChartLine {

    private String name;
    private List<Integer> RCL;
    private List<Integer> colour;
    private List<Integer> turbidity;
    private List<Integer> pH;
    private List<Date> date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getRCL() {
        return RCL;
    }

    public void setRCL(List<Integer> RCL) {
        this.RCL = RCL;
    }

    public List<Integer> getColour() {
        return colour;
    }

    public void setColour(List<Integer> colour) {
        this.colour = colour;
    }

    public List<Integer> getTurbidity() {
        return turbidity;
    }

    public void setTurbidity(List<Integer> turbidity) {
        this.turbidity = turbidity;
    }

    public List<Integer> getpH() {
        return pH;
    }

    public void setpH(List<Integer> pH) {
        this.pH = pH;
    }

    public List<Date> getDate() {
        return date;
    }

    public void setDate(List<Date> date) {
        this.date = date;
    }
}
