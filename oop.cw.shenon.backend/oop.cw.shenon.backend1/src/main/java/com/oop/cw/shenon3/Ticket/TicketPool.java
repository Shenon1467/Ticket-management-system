package com.oop.cw.shenon3.Ticket;

import java.util.logging.Logger;

import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private Queue<Ticket> ticketQueue;
    private int maxCapacity;
    private static final Logger logger = Logger.getLogger(TicketPool.class.getName());

    /**
     *Pool of ticket that can be manged in a synchronised manner.
     * @param maxCapacity the number of tickets the pool can hold.
     */

    public TicketPool( int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.ticketQueue = new LinkedList<>();
    }

    /**
     *This method adds the ticket to the pool and holds the threads until the pool is free if it is full.
     * @param ticket the ticket to be added in the pool.
     */

    public synchronized void addTicket(Ticket ticket) {
        while (ticketQueue.size() >= maxCapacity) {
            try {
                System.out.println("Pool is full. Vendor inline to release tickets.");
                logger.info("Pool is full. Vendor inline to release tickets.");
                wait();
            }catch (InterruptedException e) {
                System.out.println("System stopped");
                logger.info("System stopped.");
            }
        }
        ticketQueue.add(ticket);
//        System.out.println("Ticket added to pool. Current Size of Queue: " + ticketQueue.size());
        notifyAll();
    }

    /**
     *Retrieves and removes a ticket from the pool. If the pool is empty, the calling thread will wait until a ticket is available. And
     * interrupts the thread when it is stopped.
     * @return The ticket is retrieved from the pool
     */

    public synchronized Ticket buyTicket() {
        while (ticketQueue.isEmpty()) {
            try {
                System.out.println("Waiting for ticket to be released ...");
                logger.info("Waiting for ticket to be released ...");
                wait();
            }catch (InterruptedException e) {
                System.out.println("Thread was interrupted. Exiting.");
                logger.info("Thread was interrupted. Exiting.");
                Thread.currentThread().interrupt();
                return null;
            }
        }
        Ticket ticket = ticketQueue.poll();
//        System.out.println("Ticket removed from pool. Current Size of Queue: " + ticketQueue.size());
        notifyAll();
        return ticket;
    }
    /**
     * Returns the current size of the ticket pool.
     * @return Number of tickets currently in the pool.
     */

    public synchronized int getRemainingCapacity() {return ticketQueue.size();}
}
