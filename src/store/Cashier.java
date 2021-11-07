package store;

import products.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;

public class Cashier {

    private final LocalDateTime purchaseDateAndTime = LocalDateTime.of(2021, 6, 14
            , 12, 34, 56);
    private final LocalDate purchaseDate = LocalDate.of(2021, 6, 14);
    private List<Product> products;
    private LinkedHashMap<String, Double> quantityProduct = new LinkedHashMap<>();

    private double foodPrice;
    private double foodDiscount;
    private double beveragesPrice;
    private double beveragesDiscount;
    private double clothesPrice;
    private double clothesDiscount;
    private double appliancesPrice;
    private double appliancesDiscount;
    private double subTotal;
    private double discount;
    private Period period;

    public void purchase(Cart cart, LocalDateTime localDateTime) {

        products = cart.getProducts();

        for (Product product : products) {
            switch (product.getClass().getSimpleName()) {
                case "Food", "Beverage" -> purchasePerishable(product, purchaseDate);
                case "Clothes" -> purchaseClothes(product, purchaseDate);
                case "Appliance" -> purchaseAppliances(product, purchaseDate);
            }
        }

        printReceipt(cart, localDateTime);
    }

    public void printReceipt(Cart cart, LocalDateTime purchaseDateAndTime) {

        System.out.println("Date: " + purchaseDateAndTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println();
        System.out.println("---Products---");
        System.out.println();
        System.out.println();

        String lastProductName = "";

        for (Product product : cart.getProducts()) {

            if (product.getClass().getSimpleName().equals(lastProductName)) {
                continue;
            }

            lastProductName = product.getClass().getSimpleName();
            if (product.getClass().getSimpleName().equals("Food")) {
                period = Period.between(((Food) product).getExpiresDate(), purchaseDate);
                System.out.println(product.getName() + " - " + product.getBrand());
                System.out.println();
                System.out.printf("%.2f x $%.2f = $%.2f%n", ((Food) product).getWeight(), product.getPrice(), foodPrice);

                if (period.getYears() == 0 && period.getMonths() == 0 && period.getDays() == 0) {
                    System.out.println();
                    System.out.printf("#discount 50%%  -$%.2f%n", foodDiscount);
                } else if (period.getYears() == 0 && period.getMonths() == 0 && period.getDays() < 5) {
                    System.out.println();
                    System.out.printf("#discount 10%%  -$%.2f%n", foodDiscount);
                }

                System.out.println();
                System.out.println();
            } else if (product.getClass().getSimpleName().equals("Beverage")) {
                period = Period.between(((Beverage) product).getExpiresDate(), purchaseDate);
                System.out.println(product.getName() + " " + product.getBrand());
                int intAmount = quantityProduct.get(product.getName()).intValue();
                System.out.printf("%d x $%.2f = $%.2f%n", intAmount, product.getPrice(), beveragesPrice);


                if (period.getYears() == 0 && period.getMonths() == 0 && period.getDays() == 0) {
                    System.out.printf("#discount 50%%  -$%.2f%n", beveragesDiscount);
                } else if (period.getYears() == 0 && period.getMonths() == 0 && period.getDays() < 5) {
                    System.out.printf("#discount 10%%  -$%.2f%n", beveragesDiscount);
                }

                System.out.println();
                System.out.println();
            } else if (product.getClass().getSimpleName().equals("Clothes")) {
                System.out.println(product.getName() + " " + product.getBrand() + " " + ((Clothes) product).getSize()
                        + " " + ((Clothes) product).getColor());
                int intAmount = quantityProduct.get(product.getName()).intValue();
                System.out.printf("%d x $%.2f = $%.2f%n", intAmount, product.getPrice(), clothesPrice);

                if (!(purchaseDate.getDayOfWeek().toString().equals("SATURDAY"))
                        || !(purchaseDate.getDayOfWeek().toString().equals("SUNDAY"))) {
                    System.out.printf("#discount 10%% -$%.2f%n", clothesDiscount);
                }

                System.out.println();
                System.out.println();
            } else if (product.getClass().getSimpleName().equals("Appliance")) {
                System.out.println(product.getName() + " " + product.getBrand() + " " + ((Appliance) product).getModel());
                int intAmount = quantityProduct.get(product.getName()).intValue();
                System.out.printf("%d x $%.2f = $%.2f%n", intAmount, product.getPrice(), appliancesPrice);

                if (appliancesPrice > 999 && (purchaseDate.getDayOfWeek().toString().equals("SATURDAY")
                        || purchaseDate.getDayOfWeek().toString().equals("SUNDAY"))) {
                    System.out.printf("#discount 5%%  -$%.0f%n", appliancesDiscount);
                }
            }
        }

        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println();

        System.out.printf("SUBTOTAL: $%.2f%n", subTotal);
        System.out.printf("DISCOUNT: -$%.2f%n", discount);
        System.out.println();

        double total = subTotal - discount;

        System.out.printf("TOTAL: $%.2f%n", total);
    }

    public void purchasePerishable(Product product, LocalDate purchaseDate) {

        if (product.getClass().getSimpleName().equals("Food")) {
            if (!quantityProduct.containsKey(product.getName())) {
                quantityProduct.put(product.getName(), ((Food) product).getWeight());
            }
            period = Period.between(((Food) product).getExpiresDate(), purchaseDate);
            foodPrice = ((Food) product).getWeight() * product.getPrice();
            subTotal += foodPrice;

            if (period.getYears() == 0 && period.getMonths() == 0 && period.getDays() == 0) {
                foodDiscount = foodPrice / 2;
                discount += foodDiscount;
            } else if (period.getYears() == 0 && period.getMonths() == 0 && period.getDays() < 5) {
                foodDiscount += product.getPrice() * 0.10;
                discount += product.getPrice() * 0.10;
            }
        } else {
            if (!quantityProduct.containsKey(product.getName())) {
                quantityProduct.put(product.getName(), 0.0);
            }
            quantityProduct.put(product.getName(), quantityProduct.get(product.getName()) + 1);
            period = Period.between(((Beverage) product).getExpiresDate(), purchaseDate);
            beveragesPrice += product.getPrice();
            subTotal += product.getPrice();

            if (period.getYears() == 0 && period.getMonths() == 0 && period.getDays() == 0) {
                beveragesDiscount = beveragesPrice / 2;
                discount += beveragesDiscount;
            } else if (period.getYears() == 0 && period.getMonths() == 0 && period.getDays() < 5) {
                beveragesDiscount += product.getPrice() * 0.10;
                discount += product.getPrice() * 0.10;
            }
        }
    }

    public void purchaseClothes(Product product, LocalDate purchaseDate) {
        if (!quantityProduct.containsKey(product.getName())) {
            quantityProduct.put(product.getName(), 0.0);
        }
        quantityProduct.put(product.getName(), quantityProduct.get(product.getName()) + 1);
        clothesPrice += product.getPrice();
        subTotal += product.getPrice();


        if (!(purchaseDate.getDayOfWeek().toString().equals("SATURDAY"))
                || !(purchaseDate.getDayOfWeek().toString().equals("SUNDAY"))) {
            clothesDiscount += product.getPrice() * 0.10;
            discount += product.getPrice() * 0.10;
        }
    }

    public void purchaseAppliances(Product product, LocalDate purchaseDate) {
        appliancesPrice += product.getPrice();
        subTotal += product.getPrice();
        if (!quantityProduct.containsKey(product.getName())) {
            quantityProduct.put(product.getName(), 0.0);
        }
        quantityProduct.put(product.getName(), quantityProduct.get(product.getName()) + 1);

        if (appliancesPrice > 999 && (purchaseDate.getDayOfWeek().toString().equals("SATURDAY")
                || purchaseDate.getDayOfWeek().toString().equals("SUNDAY"))) {
            appliancesDiscount += product.getPrice() * 0.05;
            discount += product.getPrice() * 0.05;
        }
    }
}
