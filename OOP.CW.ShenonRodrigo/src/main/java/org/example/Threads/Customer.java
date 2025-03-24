package org.example.Threads;

import org.example.Ticket.Ticket;
import org.example.Ticket.TicketPool;
import java.util.logging.Logger;

/**
 * The Customer class represents a customer that retrieves tickets from a ticket pool at a specific rate.
 * This class implements the Runnable interface, allowing customer operations to be run on separate threads.
 */

public class Customer implements Runnable {

    private final String customerID;
    private final TicketPool ticketPool;
    private final int Quantity;
    private final int customerretrievalRate;
    private static final Logger logger = Logger.getLogger(Customer.class.getName());

    /**
     *Constructs a Customer with the specified details.
     * @param customerID customerID the unique identifier for the customer
     * @param ticketPool the ticket pool from which tickets will be retrieved
     * @param quantity  the number of tickets the customer wants to retrieve
     * @param customerretrievalRate the rate (in seconds) at which the customer retrieves tickets
     */

    public Customer(String customerID, TicketPool ticketPool, int quantity, int customerretrievalRate) {
        this.customerID = customerID;
        this.ticketPool = ticketPool;
        Quantity = quantity;
        this.customerretrievalRate = customerretrievalRate;
    }

    /**
     * The main task for the customer thread. Retrieves tickets from the ticket pool, logs the operation,
     * and waits for a specified interval between each retrieval.
     */

    @Override
    public void run() {
        for (int i = 0; i < Quantity; i++) {
            Ticket ticket;

            synchronized (ticketPool) {
                ticket = ticketPool.buyTicket();
                System.out.println("Customer (" + customerID + ") bought ticket: " + ticket.getTicketID());
                logger.info("Customer (" + customerID + ") bought ticket: " + ticket.getTicketID());
                System.out.println("Tickets Remaining in pool: " + ticketPool.getRemainingCapacity());
                logger.info("Tickets remaining in pool: " + ticketPool.getRemainingCapacity());
            }
            try {
                Thread.sleep(customerretrievalRate * 1000);
            }catch (InterruptedException e) {
                System.out.println("Customer (" + customerID + ") interrupted");
                logger.info("Customer (" + customerID + ") interrupted");
            }
        }
    }

}

