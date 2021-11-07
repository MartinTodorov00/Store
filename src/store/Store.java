package store;

import products.*;
import products.constants.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Store {

    private Cashier cashier;
    private Cart cart;
    private final LocalDateTime purchaseDateAndTime = LocalDateTime.of(2021, 6, 14
            , 12, 34, 56);

    public Store() {
        cashier = new Cashier();
        cart = new Cart();
    }

    public void cartFill() {
        cart.addProduct(new Food("apples", "BrandA", 1.50, LocalDate.of(2021, 6, 14), 2.45));
        cart.addProduct(new Beverage("milk", "BrandM", 0.99, LocalDate.of(2022, 2, 2)));
        cart.addProduct(new Beverage("milk", "BrandM", 0.99, LocalDate.of(2022, 2, 2)));
        cart.addProduct(new Beverage("milk", "BrandM", 0.99, LocalDate.of(2022, 2, 2)));
        cart.addProduct(new Clothes("T-shirt", "BrandT", 15.99, Size.M, "violet"));
        cart.addProduct(new Clothes("T-shirt", "BrandT", 15.99, Size.M, "violet"));
        cart.addProduct(new Appliance("laptop", "BrandL", 2345.0, "ModelL", LocalDate.of(2021, 3, 3), 1.125));

        cashier.purchase(cart, purchaseDateAndTime);
    }
}
