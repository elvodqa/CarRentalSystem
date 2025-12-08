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

    public void setCar(int id, String name, String model, String plate, String color, double price) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.plate = plate;
        this.color = color;
        this.pricePerDay = price;
    }

    public void getCar()
    {
        System.out.println("Car ID: " + this.id);
        System.out.println("Name: " + this.name);
        System.out.println("Model: " + this.model);
        System.out.println("Plate: " + this.plate);
        System.out.println("Color: " + this.color);
        System.out.println("Price Per Day: " + this.pricePerDay);
    }

    public String toString() {
        return "-------------------\n" +
                id + "\n" +
                name + "\n" +
                model + "\n" +
                plate + "\n" +
                color + "\n" +
                pricePerDay + "\n" +
                "-------------------";
    }
}
