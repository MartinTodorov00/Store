package products;

import java.time.LocalDate;

public class Beverage extends Product {

    private final LocalDate expiresDate;

    public Beverage(String name, String brand, Double price, LocalDate expiresDate) {
        super(name, brand, price);
        this.expiresDate = expiresDate;
    }

    public LocalDate getExpiresDate() {
        return expiresDate;
    }
}
