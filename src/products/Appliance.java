package products;

import java.time.LocalDate;

public class Appliance extends Product {

    private String model;
    private final LocalDate productionDate;
    private Double weight;

    public Appliance(String name, String brand, Double price, String model, LocalDate productionDate, Double weight) {
        super(name, brand, price);
        this.model = model;
        this.productionDate = productionDate;
        this.weight = weight;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
