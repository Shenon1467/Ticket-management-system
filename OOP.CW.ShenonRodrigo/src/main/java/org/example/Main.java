package org.example;

import org.example.Configure.Appconfiguration;
import org.example.Threads.Customer;
import org.example.Threads.Vendor;
import org.example.Ticket.TicketPool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;


public class Main {

    /**
     * The Main class is the entry point for the ticketing system application.
     * It handles user input, configuration management, and initialization of vendors and customers.
     */
    private static Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        configureLogger();
        Scanner sc = new Scanner(System.in);
        String choice = null;

        Appconfiguration config = null;

        //Asks user whether to load saved configuration or a new configuration
        while (!Objects.equals(choice, "yes") && !Objects.equals(choice, "no")) {
            System.out.print("Do you want to load the saved configuration? (yes/no): ");
            choice = sc.nextLine().trim().toLowerCase();
        }
        if (choice.equals("yes")) {
            config = Appconfiguration.loadCnfiguration();
            if (config == null) {
                System.out.println("No valid saved configuration has been found. Please enter new details to create a new one");
                config = createNewConfiguration(sc);
                config.saveConfiguration();
            }else {
                System.out.println("Configuration loaded successfully");
            }
        }if (choice.equals("no")) {
            config = createNewConfiguration(sc);
            config.saveConfiguration();
        }

        //Initialize the Ticket Pool
        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity());

        //create and start vendor thread
        List<Thread> vendorThreads = new ArrayList<>();
        for (int i = 0; i < config.getNumberOfVendors(); i++) {
            Thread vendorThread = new Thread(new Vendor("Vendor- " + (i + 1), ticketPool, config.getTotalTickets(), config.getTicketReleaseRate()));
            vendorThreads.add(vendorThread);
            vendorThread.start();
        }

        //create and start the customer thread.
        List<Thread> customerThreads = new ArrayList<>();
        for (int i = 0; i < config.getNumberOfCustomers(); i++) {
            Thread customerThread = new Thread(new Customer("Customer- " + (i + 1), ticketPool, config.getMaxTicketCapacity(), config.getBuyLimit()));
            customerThreads.add(customerThread);
            customerThread.start();
        }
//Wait threads to complete
        try {
            for (Thread vendorthread : vendorThreads) {
                vendorthread.join();
            }
            for (Thread customerthread : customerThreads) {
                customerthread.join();
            }
        }catch (InterruptedException e) {
            System.out.println("Thread interrupted");
            logger.severe("Thread interrupted");
        }
        System.out.println("Program has terminated.");
        logger.info("Program has terminated.");
    }

    /**
     * Configures the application logger to write logs to a file.
     */

    public static void configureLogger() {
        try {
            FileHandler fileHandler=new FileHandler("CLIApplication.log",true);
            fileHandler.setFormatter(new SimpleFormatter());
            Logger.getLogger("").addHandler(fileHandler);
            Logger.getLogger("").setLevel(Level.INFO);
        }catch (IOException e) {
            System.out.println("Failed to configure logger");
        }
    }

    /**
     * Creates a new application configuration based on user input.
     * @param sc scanner object for the user.
     * @return new appconfiguration object with user defined parameters.
     */

    public static Appconfiguration createNewConfiguration(Scanner sc){
        int totalTickets = Inputtaker_Integer(sc, "Enter total number of tickets Supplied by vendor: ");
        int numOfVendors = Inputtaker_Integer(sc, "Enter number of vendors selling tickets: ");
        int numOfCustomers = Inputtaker_Integer(sc, "Enter number of customers buying tickets: ");
        int buylimit = Inputtaker_Integer(sc, "Enter buy limit for each Customer: ");
        int maxCapacity = Inputtaker_Integer(sc, "Enter maximum number of tickets in ticket pool: ");
        int retreivalrate = Inputtaker_Integer(sc, "Enter Vendor retrieval rate(in seconds): ");
        int customerretrievalRate = Inputtaker_Integer(sc, "Enter customer retrieval rate(in seconds): ");

        return new Appconfiguration(totalTickets,retreivalrate,customerretrievalRate,maxCapacity,numOfVendors,numOfCustomers, buylimit);
    }

    /**
     * Handles user input for integer values, ensuring the input is positive.
     * @param sc the Scanner object for user input
     * @param prompt the prompt message to display to the user
     * @return a positive integer value entered by the user
     */

    public static int Inputtaker_Integer (Scanner sc, String prompt){
        int num;
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                num = sc.nextInt();
                if (num > 0) {
                    return num;
                } else {
                    System.out.println("Value must be positive");
                }
            } else {
                System.out.println("Invalid input. Please enter a positive number");
                sc.next();
            }
        }
    }

}