package com.ludowica.waterqualityanalysisapi.forms;

public class ChartColumn {

    private double RCL;
    private double coliform;
    private double eColi;
    private double colour;
    private double turbidity;
    private double pH;
    private double EC;

    public double getRCL() {
        return RCL;
    }

    public void setRCL(double RCL) {
        this.RCL = RCL;
    }

    public double getColiform() {
        return coliform;
    }

    public void setColiform(double coliform) {
        this.coliform = coliform;
    }

    public double geteColi() {
        return eColi;
    }

    public void seteColi(double eColi) {
        this.eColi = eColi;
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
}
