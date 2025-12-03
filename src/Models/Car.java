package Models;

public class Car {
    public int id;
    public String name;
    public String model;
    public String plate;
    public String color;
    public double pricePerDay;

    public Car(int id, String name, String model, String plate, String color, double price) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.plate = plate;
        this.color = color;
        this.pricePerDay = price;
    }
}
