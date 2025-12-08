package Models;

import java.sql.Date;

public class RentalPeriod {
    public int id;
    public int userId;
    public int carId;
    public Date startDate;
    public Date endDate;

    public RentalPeriod(int id, int userId, int carId, Date startDate, Date endDate)
    {
        this.id = id;
        this.userId = userId;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setRentalPeriod(int id, int userId, int carId, Date startDate, Date endDate)
    {
        this.id = id;
        this.userId = userId;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void getRentalPeriod()
    {
        System.out.println("Rental Period ID: " + this.id);
        System.out.println("User ID: " + this.userId);
        System.out.println("Car ID: " + this.carId);
        System.out.println("Start Date: " + this.startDate);
        System.out.println("End Date: " + this.endDate);
    }

    public String toString() {
        return id + "\n" + userId + "\n" + carId + "\n" + startDate + "\n" + endDate + "\n";
    }
}
