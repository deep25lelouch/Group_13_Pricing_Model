/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TheBusiness;
//08-11-2025_RijurikSaha

import TheBusiness.Business.Business;
import TheBusiness.CustomerManagement.CustomerDirectory;
import TheBusiness.CustomerManagement.CustomerProfile;
import TheBusiness.OrderManagement.Order;
import TheBusiness.Personnel.Person;
import TheBusiness.Personnel.PersonDirectory;
import TheBusiness.ProductManagement.Product;
import TheBusiness.ProductManagement.ProductCatalog;
import TheBusiness.SalesManagement.SalesPersonDirectory;
import TheBusiness.SalesManagement.SalesPersonProfile;
import TheBusiness.Supplier.Supplier;
import TheBusiness.Supplier.SupplierDirectory;
import com.github.javafaker.Faker;
import java.util.ArrayList;

/**
 *
 * @author RIO
 */
public class DataGenerator {

    //08-11-2025_RijurikSaha
    
    
    private Faker faker;
    private Business business;

    //08-11-2025_RijurikSaha
    public DataGenerator(Business b) {
        this.business = b;
        this.faker = new Faker();
    }

    //08-11-2025_RijurikSaha
    public void generateSuppliers() {
        SupplierDirectory supplierDirectory = business.getSupplierDirectory();

        System.out.println("Generating 50 suppliers with 50 products each...");

        for (int i = 1; i <= 50; i++) {
            // Using Java Faker for company names
            String supplierName = faker.company().name();
            Supplier supplier = supplierDirectory.newSupplier(supplierName);

            // To Generate 50 products for supplier
            ProductCatalog catalog = supplier.getProductCatalog();

            for (int j = 1; j <= 50; j++) {
                // Using Java Faker for product names
                String productName = faker.commerce().productName();

                // Using Math.random() for prices as required
                int floorPrice = 1000 + (int) (Math.random() * 9000);  // 1000-10000
                int ceilingPrice = floorPrice + 2000 + (int) (Math.random() * 8000); // floor+2000 to floor+10000
                int targetPrice = floorPrice + (int) ((ceilingPrice - floorPrice) * 0.6); // 60% markup

                catalog.newProduct(productName, floorPrice, ceilingPrice, targetPrice);
            }

            if (i % 10 == 0) {
                System.out.println("  Generated " + i + " suppliers...");
            }
        }
    }

    //08-11-2025_RijurikSaha
    public void generateCustomers() {
        PersonDirectory personDirectory = business.getPersonDirectory();
        CustomerDirectory customerDirectory = business.getCustomerDirectory();

        System.out.println("Generating 300 customers...");

        for (int i = 1; i <= 300; i++) {
            // Using Java Faker for realistic names
            String personName = faker.name().fullName();
            Person person = personDirectory.newPerson(personName);
            CustomerProfile customer = customerDirectory.newCustomerProfile(person);

            if (i % 50 == 0) {
                System.out.println("  Generated " + i + " customers...");
            }
        }
    }

    //08-11-2025_RijurikSaha
    public void generateSalesPersons() {
        PersonDirectory personDirectory = business.getPersonDirectory();
        SalesPersonDirectory salesDirectory = business.getSalesPersonDirectory();

        System.out.println("Generating sales persons...");

        // Create 10 sales persons
        for (int i = 1; i <= 10; i++) {
            String personName = faker.name().fullName();
            Person person = personDirectory.newPerson(personName);
            salesDirectory.newSalesPersonProfile(person);
        }
    }
    //08-11-2025_RijurikSaha
    public void generateOrders() {
        CustomerDirectory customerDirectory = business.getCustomerDirectory();
        SalesPersonDirectory salesDirectory = business.getSalesPersonDirectory();
        SupplierDirectory supplierDirectory = business.getSupplierDirectory();

        ArrayList<CustomerProfile> customers = customerDirectory.getCustomerList();
        ArrayList<SalesPersonProfile> salesPersons = salesDirectory.getSalesPersonList();
        ArrayList<Supplier> suppliers = supplierDirectory.getSuplierList();

        System.out.println("Generating orders for 300 customers...");

        int totalOrders = 0;

        for (CustomerProfile customer : customers) {
            // Each customer gets 1-3 orders as required
            int orderCount = 1 + (int) (Math.random() * 3);

            for (int o = 0; o < orderCount; o++) {
                // Random sales person for each order
                SalesPersonProfile salesPerson = salesPersons.get(
                        (int) (Math.random() * salesPersons.size())
                );

                Order order = business.getMasterOrderList().newOrder(customer, salesPerson);

                // Each order has up to 10 items as required
                int itemCount = 1 + (int) (Math.random() * 10);

                for (int i = 0; i < itemCount; i++) {
                    // Random supplier and product
                    Supplier supplier = suppliers.get((int) (Math.random() * suppliers.size()));
                    ArrayList<Product> products = supplier.getProductCatalog().getProductList();

                    if (!products.isEmpty()) {
                        Product product = products.get((int) (Math.random() * products.size()));

                        // Generate actual price around target to create realistic data
                        int targetPrice = product.getTargetPrice();

                        // 50% chance to be above or below target
                        int actualPrice;
                        if (Math.random() < 0.5) {
                            // Below target - product underperforming
                            actualPrice = product.getFloorPrice()
                                    + (int) (Math.random() * (targetPrice - product.getFloorPrice()));
                        } else {
                            // Above target - product overperforming  
                            actualPrice = targetPrice
                                    + (int) (Math.random() * (product.getCeilingPrice() - targetPrice));
                        }

                        // Quantity between 1-5
                        int quantity = 1 + (int) (Math.random() * 5);

                        order.newOrderItem(product, actualPrice, quantity);
                    }
                }
                totalOrders++;
            }
        }

        System.out.println("  Generated " + totalOrders + " total orders");
    }
}
