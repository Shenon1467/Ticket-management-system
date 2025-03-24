package com.oop.cw.shenon3.Controllers;

import com.oop.cw.shenon3.Config.Appconfiguration;
import com.oop.cw.shenon3.Config.FrontLogHandler;
import com.oop.cw.shenon3.Threads.Customer;
import com.oop.cw.shenon3.Threads.Vendor;
import com.oop.cw.shenon3.Ticket.TicketPool;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controller class for managing the ticketing system via RESTful API endpoints.
 * Handles configuration management, system start/stop operations, and log retrieval.
 * Includes functionality to manage customer and vendor threads and their interactions with a ticket pool.
 */

@RestController
@CrossOrigin
@RequestMapping("/api")
public class TicketController {

    private Appconfiguration appconfig = new Appconfiguration(0,0,0,0,0,0,0);
    private static final String filename = "config.json";

    private TicketPool ticketPool;
    private List<Thread> vendorThreads = new ArrayList<>();
    private List<Thread> customerThreads = new ArrayList<>();
    private final List<String> logs = Collections.synchronizedList(new ArrayList<>());
    private boolean systemRunning = false;

    @PostMapping("/configure")
    public String saveConfiguration(@RequestBody Appconfiguration config) {
        this.appconfig = config;
        return "Your Configuration has been saved";
    }

    @GetMapping("/configuration")
    public Appconfiguration getConfiguration() {
        return appconfig;
    }

   /** * Saves the configuration to a JSON file.
 *
            * @param totalTickets The total number of tickets supplied by vendors.
            * @param ticketReleaseRate The rate at which tickets are released by vendors (in seconds).
            * @param customerRetrievalRate The rate at which customers retrieve tickets (in seconds).
            * @param maxTicketCapacity The maximum number of tickets in the pool.
            * @param numberOfVendors The number of vendors in the system.
            * @param numberOfCustomers The number of customers in the system.
            * @param buyLimit The maximum number of tickets a customer can buy.
            * @return A confirmation message indicating the configuration has been saved to a file.
            **/

    @PostMapping("/saveconfig")
    public String saveConfig(
            @RequestParam int totalTickets,
            @RequestParam int ticketReleaseRate,
            @RequestParam int customerRetrievalRate,
            @RequestParam int maxTicketCapacity,
            @RequestParam int numberOfVendors,
            @RequestParam int numberOfCustomers,
            @RequestParam int buyLimit
    ) {
        Appconfiguration config = new Appconfiguration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity, numberOfVendors, numberOfCustomers, buyLimit);
//        if (appconfig == null) {
//            appconfig = new Appconfiguration; // Initialize if null
//        }
        appconfig.saveConfiguration(config);
        appconfig.saveToFile(filename);
        return "Your Configuration has been saved";
    }

    /**
            * Loads the configuration from a JSON file.
 *
         * @return A message indicating whether the configuration was successfully loaded.
 **/

    @GetMapping("/loadconfig")
    public String loadConfig() {
//        if (appconfig == null) {
//            appconfig = new Appconfiguration(); // Initialize if null
//        }
        Appconfiguration config = appconfig.loadFromFile(filename);
        if (config == null) {
            return "Loading configuration failed";
        }
        appconfig.saveConfiguration(config);
        return "Your Configuration has been loaded";
    }

/**
 * Starts the ticketing system by initializing the ticket pool, vendor threads, and customer threads.
 *
 * @return A message indicating whether the system was successfully started or already running to the user.
 **/

    @PostMapping("/start")
    public String start() {
        if (systemRunning) {
            return "System already running";
        }
        if (appconfig.getTotalTickets() == 0 || appconfig.getNumberOfVendors() == 0 || appconfig.getNumberOfCustomers() == 0) {
            return "Configuration is not valid. Load again";
        }

        ticketPool = new TicketPool(appconfig.getMaxTicketCapacity());

        for (int i = 0; i < appconfig.getNumberOfVendors(); i++) {
            Thread vendorThread = new Thread(
                    new Vendor(
                            "Vendor - " + (i + 1),
                            ticketPool, appconfig.getTotalTickets(),
                            appconfig.getTicketReleaseRate()
                    )
            );
            vendorThreads.add(vendorThread);
            vendorThread.start();
        }

        for (int i = 0; i < appconfig.getNumberOfCustomers(); i++) {
            Thread customerThread = new Thread(
                    new Customer(
                            "Customer - " + (i + 1),
                            ticketPool, appconfig.getBuyLimit(),
                            appconfig.getCustomerRetrievalRate()
                    )
            );
            customerThreads.add(customerThread);
            customerThread.start();
        }
        systemRunning = true;
        return "System started running. Check logs.";
    }

/**
 * Stops the ticketing system by interrupting all vendor and customer threads.
 *
 * @return A message indicating whether the system was successfully stopped or was not running.
 **/

    @PostMapping("/stop")
    public String stop() {
        if (!systemRunning) {
            return "System not running";
        }

        vendorThreads.forEach(thread -> {
            if (thread.isAlive()) {
                thread.interrupt();
            }
        });

        customerThreads.forEach(thread -> {
            if (thread.isAlive()) {
                thread.interrupt();
            }
        });

        vendorThreads.clear();
        customerThreads.clear();
        systemRunning = false;
        return "System stopped";
    }


/**
 * Retrieves the log messages generated by the system.
 *
 * @return A list of log messages show to the user..**/

    @GetMapping("/logs")
    public List<String> getLogs() {
        List<String> logs = new ArrayList<>();
        FrontLogHandler.getLogqueue().drainTo(logs);
        return logs;
    }

}
