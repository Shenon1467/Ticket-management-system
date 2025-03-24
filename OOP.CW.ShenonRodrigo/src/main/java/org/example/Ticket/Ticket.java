package org.example.Ticket;
import java.math.BigDecimal;

/**
 * Ticket class represents a ticket with a unique identifier and price.
 */
public class Ticket {

    private String ticketID;
    private BigDecimal price;

    /**
     *Constructs a Ticket with the specified ticket ID and price
     * @param ticketID ticketID the unique identifier for the ticket
     * @param price the price of the ticket
     *
     */

    public Ticket(String ticketID, BigDecimal price) {
        this.ticketID = ticketID;
        this.price = price;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     *Returns a string representation of the Ticket object, including its ticket ID and price.
     * @return a string representation of the Ticket object
     */

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketID='" + ticketID + '\'' +
                ", price=" + price +
                '}';
    }
}
