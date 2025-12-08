package Models;

import java.sql.Date;

public class RentalPeriod {
    public int id;
    public int userId;
    public int carId;
    public Date startDate;
    public Date endDate;

    public RentalPeriod(int id, int userId, int carId, Date startDate, Date endDate) {
        this.id = id;
        this.userId = userId;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String toString() {
        return id + "\n" + userId + "\n" + carId + "\n" + startDate + "\n" + endDate + "\n";
    }
}
