package com.ludowica.waterqualityanalysisapi.forms;

public class ChartPie {

    private double RCL;
    private double colour;
    private double turbidity;
    private double pH;
    private double EC;
    private double total;

    public double getRCL() {
        return RCL;
    }

    public void setRCL(double RCL) {
        this.RCL = RCL;
    }

    public double getColour() {
        return colour;
    }

    public void setColour(double colour) {
        this.colour = colour;
    }

    public double getTurbidity() {
        return turbidity;
    }

    public void setTurbidity(double turbidity) {
        this.turbidity = turbidity;
    }

    public double getpH() {
        return pH;
    }

    public void setpH(double pH) {
        this.pH = pH;
    }

    public double getEC() {
        return EC;
    }

    public void setEC(double EC) {
        this.EC = EC;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
