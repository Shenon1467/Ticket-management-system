package org.example.Threads;

import org.example.Ticket.Ticket;
import org.example.Ticket.TicketPool;
import java.util.logging.Logger;
import java.math.BigDecimal;

/**
 * The Vendor class represents a vendor that generates and adds tickets to a ticket pool at a specific rate.
 * This class implements the Runnable interface, allowing vendor operations to be run on separate threads.
 */
public class Vendor implements Runnable {
    private static final Logger logger = Logger.getLogger(Vendor.class.getName());
    private final String vendorID;
    private final TicketPool ticketPool;
    private final int totalTickets;
    private final int retreivalrate;

    /**
     *Constructs a Vendor with the specified details.
     * @param vendorID the unique identifier for the vendor
     * @param ticketPool the ticket pool to which tickets will be added
     * @param totalTickets the total number of tickets the vendor will generate
     * @param retreivalrate the rate (in seconds) at which tickets will be generated and added to the pool
     */


    public Vendor(String vendorID, TicketPool ticketPool, int totalTickets, int retreivalrate) {
        this.vendorID = vendorID;
        this.ticketPool = ticketPool;
        this.totalTickets = totalTickets;
        this.retreivalrate = retreivalrate;
    }

    /**
     *The main task for the vendor thread. Generates tickets with unique IDs, adds them to the ticket pool,
     * and logs the operation. The thread waits for a specified interval between adding tickets.
     */

    @Override
    public void run() {
        for (int i = 0; i < totalTickets; i++) {
            Ticket ticket = new Ticket(vendorID + "-T" + i,new BigDecimal(1000));

            synchronized (ticketPool) {
                ticketPool.addTicket(ticket);
                System.out.println("Vendor (" + vendorID + ") released ticket: " + ticket.getTicketID());
                logger.info("Vendor (" + vendorID + ") released ticket: " + ticket.getTicketID());
                System.out.println("Tickets Remaining in pool: " + ticketPool.getRemainingCapacity());
                logger.info("Tickets remaining in pool: " + ticketPool.getRemainingCapacity());
            }
            try {
                Thread.sleep(retreivalrate * 1000);
            }catch (InterruptedException e) {
                System.out.println("Thread interrupted");
                logger.warning("Thread interrupted");
            }
        }
    }
}

