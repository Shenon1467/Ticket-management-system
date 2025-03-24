package com.oop.cw.shenon3.Threads;

import com.oop.cw.shenon3.Ticket.Ticket;
import com.oop.cw.shenon3.Ticket.TicketPool;

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
     * and waits for a specified interval between each retrieval. Also interrupts the customer thread when the system is stopped.
     */

    @Override
    public void run() {
        for (int i = 0; i < Quantity; i++) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Customer (" + customerID + ") is interrupted. Stop thread.");
                logger.info("Customer (" + customerID + ") is interrupted. Stop thread.");
                break;
            }
            Ticket ticket;
            synchronized (ticketPool) {
                ticket = ticketPool.buyTicket();
                System.out.println("Customer (" + customerID + ") bought ticket: " + ticket.getTicketID());
                logger.info("Customer (" + customerID + ") bought ticket: " + ticket.getTicketID());
                System.out.println("Ticket removed from pool. Current Size of Queue: " + ticketPool.getRemainingCapacity());
                logger.info("Ticket removed from pool. Current Size of Queue: " + ticketPool.getRemainingCapacity());
                if (ticket == null) {
                    System.out.println("Customer (" + customerID + ") did not get a ticket. Exiting.");
                    logger.info("Customer (" + customerID + ") did not get a ticket. Exiting.");
                    break;
                }
            }
            //System.out.println("Customer (" + customerID + ") bought ticket: " + ticket.getTicketID());

            try {
                Thread.sleep(customerretrievalRate * 1000);
            }catch (InterruptedException e) {
                System.out.println("Customer (" + customerID + ") was interrupted during sleep. Stopping thread..");
                logger.info("Customer (" + customerID + ") was interrupted during sleep. Stopping thread..");
                Thread.currentThread().interrupt(); //Reset interrupt flag
                break;
            }
        }
        System.out.println("Ticket retrieving stopped");
        logger.info("Ticket retrieving stopped");
    }
}
