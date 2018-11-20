package org.spa.vo.common;

import java.io.Serializable;

/**
 * Created by Ivy on 2017/2/22.
 */
public class TotalFigureVO implements Serializable {

    private double totalRevenue;

    private double totalServices;

    private double totalPackages;

    private double totalRetail;

    private long bookings;

    private long waitingList;

    private long walkIns;

    private long noShows;

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getTotalServices() {
        return totalServices;
    }

    public void setTotalServices(double totalServices) {
        this.totalServices = totalServices;
    }

    public double getTotalPackages() {
        return totalPackages;
    }

    public void setTotalPackages(double totalPackages) {
        this.totalPackages = totalPackages;
    }

    public double getTotalRetail() {
        return totalRetail;
    }

    public void setTotalRetail(double totalRetail) {
        this.totalRetail = totalRetail;
    }

    public long getBookings() {
        return bookings;
    }

    public void setBookings(long bookings) {
        this.bookings = bookings;
    }

    public long getWaitingList() {
        return waitingList;
    }

    public void setWaitingList(long waitingList) {
        this.waitingList = waitingList;
    }

    public long getWalkIns() {
        return walkIns;
    }

    public void setWalkIns(long walkIns) {
        this.walkIns = walkIns;
    }

    public long getNoShows() {
        return noShows;
    }

    public void setNoShows(long noShows) {
        this.noShows = noShows;
    }
}
