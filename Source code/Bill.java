package com.example.assignment1;

public class Bill {
    private int id;
    private String month;
    private int units;
    private double totalCharges;
    private int rebate;
    private double finalCost;

    // Constructor with ID (for data from DB)
    public Bill(int id, String month, int units, double totalCharges, int rebate, double finalCost) {
        this.id = id;
        this.month = month;
        this.units = units;
        this.totalCharges = totalCharges;
        this.rebate = rebate;
        this.finalCost = finalCost;
    }

    // Constructor without ID (for inserting new data)
    public Bill(String month, int units, double totalCharges, int rebate, double finalCost) {
        this.month = month;
        this.units = units;
        this.totalCharges = totalCharges;
        this.rebate = rebate;
        this.finalCost = finalCost;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public int getUnits() { return units; }
    public void setUnits(int units) { this.units = units; }

    public double getTotalCharges() { return totalCharges; }
    public void setTotalCharges(double totalCharges) { this.totalCharges = totalCharges; }

    public int getRebate() { return rebate; }
    public void setRebate(int rebate) { this.rebate = rebate; }

    public double getFinalCost() { return finalCost; }
    public void setFinalCost(double finalCost) { this.finalCost = finalCost; }
}
