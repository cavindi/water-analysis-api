package com.ludowica.waterqualityanalysisapi.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
public class WaterInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "Asia/Colombo")
    private Date date;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("waterInfoSet")
    private Location location;

    private double RCL;
    private double coliform;
    private double eColi;
    private double colour;
    private double turbidity;
    private double pH;
    private double EC;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

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
