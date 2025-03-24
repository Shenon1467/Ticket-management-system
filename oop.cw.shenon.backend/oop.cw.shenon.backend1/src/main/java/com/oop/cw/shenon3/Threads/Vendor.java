package com.oop.cw.shenon3.Threads;

import com.oop.cw.shenon3.Ticket.Ticket;
import com.oop.cw.shenon3.Ticket.TicketPool;

import java.math.BigDecimal;
import java.util.logging.Logger;

/**
 * The Vendor class represents a vendor that generates and adds tickets to a ticket pool at a specific rate.
 * This class implements the Runnable interface, allowing vendor operations to be run on separate threads.
 */
public class Vendor implements Runnable {
    private final String vendorID;
    private final TicketPool ticketPool;
    private final int totalTickets;
    private final int retreivalrate;
    private static final Logger log = Logger.getLogger(Vendor.class.getName());

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
     * Also when the system is stopped the threads are being interrupted.
     */
    @Override
    public void run() {
        for (int i = 0; i < totalTickets; i++) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Vendor (" + vendorID + ") interrupted. Exiting.");
                log.info("Vendor (" + vendorID + ") interrupted. Exiting.");
                break;
            }

            Ticket ticket = new Ticket(vendorID + "-T" + i,new BigDecimal(1000));
            synchronized (ticketPool) {
                ticketPool.addTicket(ticket);
                System.out.println("Vendor (" + vendorID + ") released ticket: " + ticket.getTicketID());
                log.info("Vendor (" + vendorID + ") released ticket: " + ticket.getTicketID());
                System.out.println("Ticket added to pool. Current Size of Queue: " + ticketPool.getRemainingCapacity());
                log.info("Tickets remaining in the pool. Current Size of Queue: " + ticketPool.getRemainingCapacity());
            }
            try {
                Thread.sleep(retreivalrate * 1000);
            }catch (InterruptedException e) {
                System.out.println("Vendor (" + vendorID + ") interrupted during sleep. Exiting.");
                log.info("Vendor (" + vendorID + ") interrupted during sleep. Exiting.");
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Ticket release stopped");
        log.info("Ticket released stopped");
    }
}
