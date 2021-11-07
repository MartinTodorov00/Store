package products;

import java.time.LocalDate;

public class Food extends Product {

    private final LocalDate expiresDate;
    private double weight = 1;

    public Food(String name, String brand, Double price, LocalDate expiresDate, double weight) {
        super(name, brand, price);
        this.expiresDate = expiresDate;
        this.weight = weight;
    }

    public LocalDate getExpiresDate() {
        return expiresDate;
    }

    public double getWeight() {
        return weight;
    }
}
