package org.example.Configure;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * The Appconfiguration class manages application settings for a ticketing system.
 * It provides functionality to save and load configuration data to and from a JSON file.
 */

public class Appconfiguration {

    private int totalTickets;
    private int ticketReleaseRate;
    private int CustomerRetrievalRate;
    private int maxTicketCapacity;
    private int NumberOfVendors;
    private int NumberOfCustomers;
    private int BuyLimit;

    private static final Logger logger = Logger.getLogger(Appconfiguration.class.getName());

    private static final String filename = "CLIconfig.json";

    /**
     *Constructs an Appconfiguration object with the specified parameters.
     * @param totalTickets the total number of tickets available.
     * @param ticketReleaseRate the rate at which tickets are released by vendors in seconds.
     * @param customerRetrievalRate the rate at which customers retrieve tickets (in seconds)
     * @param maxTicketCapacity the maximum capacity of the ticket pool
     * @param numberOfVendors the number of vendors in the system
     * @param numberOfCustomers the number of customers in the system
     * @param buyLimit the maximum number of tickets a customer can buy
     */

    public Appconfiguration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity, int numberOfVendors, int numberOfCustomers, int buyLimit) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        CustomerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
        NumberOfVendors = numberOfVendors;
        NumberOfCustomers = numberOfCustomers;
        BuyLimit = buyLimit;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return CustomerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        CustomerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public int getNumberOfVendors() {
        return NumberOfVendors;
    }

    public void setNumberOfVendors(int numberOfVendors) {
        NumberOfVendors = numberOfVendors;
    }

    public int getNumberOfCustomers() {
        return NumberOfCustomers;
    }

    public void setNumberOfCustomers(int numberOfCustomers) {
        NumberOfCustomers = numberOfCustomers;
    }

    public int getBuyLimit() {
        return BuyLimit;
    }

    public void setBuyLimit(int buyLimit) {
        BuyLimit = buyLimit;
    }

    /**
     * Saves the current configuration to a JSON file.
     * The file is saved with the name defined in the `filename` variable.
     */

    public void saveConfiguration() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(filename)){
            gson.toJson(this, writer);
            System.out.println("Configuration saved successfully");
            logger.info("Configuration saved successfully");
        } catch (IOException e) {
            System.out.println("Configuration save failed");
            logger.info("Configuration save failed");
        }
    }

    /**
     * Loads the configuration from the JSON file.
     *
     * @return an Appconfiguration object loaded from the JSON file, or null if loading fails
     */
    public static Appconfiguration loadCnfiguration() {
        try (FileReader reader = new FileReader(filename)){
            Gson gson = new Gson();
            logger.info("Configuration loaded successfully");
            return gson.fromJson(reader, Appconfiguration.class);
        } catch (IOException e) {
            System.out.println("Configuration load failed");
            logger.info("Configuration load failed");
            return null;
        }
    }

    /**
     * Returns a string representation of the Appconfiguration object.
     *
     * @return a string representation of the configuration, including all key parameters
     */

    @Override
    public String toString() {
        return "Appconfiguration{" +
                "totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", CustomerRetrievalRate=" + CustomerRetrievalRate +
                ", maxTicketCapacity=" + maxTicketCapacity +
                ", NumberOfVendors=" + NumberOfVendors +
                ", NumberOfCustomers=" + NumberOfCustomers +
                '}';
    }
}

