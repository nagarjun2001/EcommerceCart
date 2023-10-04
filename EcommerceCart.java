import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class EcommerceCart {
    public static void main(String[] args) {
        Product laptop = new Laptop("Laptop", 1000.0, true, "Specs");
        Product headphones = new Headphones("Headphones", 50.0, true, "Brand");

        Cart cart = new Cart();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Add an item to the cart");
            System.out.println("2. Update item quantity in the cart");
            System.out.println("3. Remove an item from the cart");
            System.out.println("4. Calculate total bill");
            System.out.println("5. View products");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter product name: ");
                    String productName = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    int quantityToAdd = scanner.nextInt();
                    scanner.nextLine(); 
                    Product productToAdd = getProductByName(productName, laptop, headphones);
                    if (productToAdd != null) {
                        cart.addItem(productToAdd, quantityToAdd);
                        System.out.println("Item added to the cart.");
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;
                case 2:
                    System.out.print("Enter product name to update quantity: ");
                    String productNameToUpdate = scanner.nextLine();
                    System.out.print("Enter new quantity: ");
                    int newQuantity = scanner.nextInt();
                    scanner.nextLine(); 
                    cart.updateQuantity(productNameToUpdate, newQuantity);
                    System.out.println("Quantity updated.");
                    break;
                case 3:
                    System.out.print("Enter product name to remove: ");
                    String productToRemove = scanner.nextLine();
                    cart.removeItem(productToRemove);
                    System.out.println("Item removed from the cart.");
                    break;
                case 4:
                    double totalBill = cart.calculateTotal();
                    System.out.println("Your total bill is $" + totalBill);
                    break;
                case 5:
                    viewProducts(laptop, headphones);
                    break;
                case 6:
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    static Product getProductByName(String productName, Product... products) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                return product;
            }
        }
        return null;
    }

    static void viewProducts(Product... products) {
        System.out.println("List of Products:");
        for (Product product : products) {
            System.out.println("Name: " + product.getName());
            System.out.println("Price: $" + product.getPrice());
            System.out.println("Available: " + (product.isAvailable() ? "Yes" : "No"));
            System.out.println();
        }
    }

    static class Product {
        private String name;
        private double price;
        private boolean available;
        private int quantity;

        public Product(String name, double price, boolean available) {
            this.name = name;
            this.price = price;
            this.available = available;
            this.quantity = 0;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public boolean isAvailable() {
            return available;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    static class Laptop extends Product {
        private String specifications;

        public Laptop(String name, double price, boolean available, String specifications) {
            super(name, price, available);
            this.specifications = specifications;
        }

        public String getSpecifications() {
            return specifications;
        }

        public void setSpecifications(String specifications) {
            this.specifications = specifications;
        }
    }

    static class Headphones extends Product {
        private String brand;

        public Headphones(String name, double price, boolean available, String brand) {
            super(name, price, available);
            this.brand = brand;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }
    }

    static class Cart {
        private List<Product> items;

        public Cart() {
            items = new ArrayList<>();
        }

        public void addItem(Product product, int quantity) {
            for (int i = 0; i < quantity; i++) {
                Product clonedProduct = new Product(product.getName(), product.getPrice(), product.isAvailable());
                clonedProduct.setQuantity(1);
                items.add(clonedProduct);
            }
        }

        public void updateQuantity(String productName, int newQuantity) {
            Iterator<Product> iterator = items.iterator();
            while (iterator.hasNext()) {
                Product item = iterator.next();
                if (item.getName().equals(productName)) {
                    if (newQuantity > 0) {
                        // Set the new quantity
                        item.setQuantity(newQuantity);
                    } else {
                        // Remove the item if the new quantity is zero or negative
                        iterator.remove();
                    }
                }
            }
        }

        public void removeItem(String productName) {
            Iterator<Product> iterator = items.iterator();
            while (iterator.hasNext()) {
                Product item = iterator.next();
                if (item.getName().equals(productName)) {
                    iterator.remove();
                }
            }
        }

        public double calculateTotal() {
            double total = 0.0;
            for (Product item : items) {
                total += item.getPrice() * item.getQuantity();
            }
            return total;
        }
    }
}
