package com.example.springbootexamgestionlocationimmeuble.Entity;



import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public class AdminDashboardVM {
    // KPI
    public long totalUsers;
    public long totalOwners;
    public long totalTenants;
    public long totalBuildings;
    public long totalUnits;
    public long unitsAvailable;
    public long unitsOccupied;
    public long activeContracts;
    public long overduePayments;    // nombre de loyers en retard
    public BigDecimal monthlyRevenue; // revenu du mois courant
    public BigDecimal ytdRevenue;     // revenu cumulé année en cours

    // Graphiques
    public List<String> last12Months;                     // étiquettes
    public List<BigDecimal> revenueByMonth;                  // montants/mois
    public Map<String, long[]> occupancyByBuilding;          // "Immeuble A" -> [disponible, occupe]
    public long paidCount;                                   // pour donut payés/impayés
    public long unpaidCount;

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalOwners() {
        return totalOwners;
    }

    public void setTotalOwners(long totalOwners) {
        this.totalOwners = totalOwners;
    }

    public long getTotalTenants() {
        return totalTenants;
    }

    public void setTotalTenants(long totalTenants) {
        this.totalTenants = totalTenants;
    }

    public long getTotalBuildings() {
        return totalBuildings;
    }

    public void setTotalBuildings(long totalBuildings) {
        this.totalBuildings = totalBuildings;
    }

    public long getTotalUnits() {
        return totalUnits;
    }

    public void setTotalUnits(long totalUnits) {
        this.totalUnits = totalUnits;
    }

    public long getUnitsAvailable() {
        return unitsAvailable;
    }

    public void setUnitsAvailable(long unitsAvailable) {
        this.unitsAvailable = unitsAvailable;
    }

    public long getUnitsOccupied() {
        return unitsOccupied;
    }

    public void setUnitsOccupied(long unitsOccupied) {
        this.unitsOccupied = unitsOccupied;
    }

    public long getActiveContracts() {
        return activeContracts;
    }

    public void setActiveContracts(long activeContracts) {
        this.activeContracts = activeContracts;
    }

    public long getOverduePayments() {
        return overduePayments;
    }

    public void setOverduePayments(long overduePayments) {
        this.overduePayments = overduePayments;
    }

    public BigDecimal getMonthlyRevenue() {
        return monthlyRevenue;
    }

    public void setMonthlyRevenue(BigDecimal monthlyRevenue) {
        this.monthlyRevenue = monthlyRevenue;
    }

    public BigDecimal getYtdRevenue() {
        return ytdRevenue;
    }

    public void setYtdRevenue(BigDecimal ytdRevenue) {
        this.ytdRevenue = ytdRevenue;
    }

    public List<String> getLast12Months() {
        return last12Months;
    }

    public void setLast12Months(List<String> last12Months) {
        this.last12Months = last12Months;
    }

    public List<BigDecimal> getRevenueByMonth() {
        return revenueByMonth;
    }

    public void setRevenueByMonth(List<BigDecimal> revenueByMonth) {
        this.revenueByMonth = revenueByMonth;
    }

    public Map<String, long[]> getOccupancyByBuilding() {
        return occupancyByBuilding;
    }

    public void setOccupancyByBuilding(Map<String, long[]> occupancyByBuilding) {
        this.occupancyByBuilding = occupancyByBuilding;
    }

    public long getPaidCount() {
        return paidCount;
    }

    public void setPaidCount(long paidCount) {
        this.paidCount = paidCount;
    }

    public long getUnpaidCount() {
        return unpaidCount;
    }

    public void setUnpaidCount(long unpaidCount) {
        this.unpaidCount = unpaidCount;
    }
}
