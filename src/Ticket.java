import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    // initializing variables
    private String row;
    private int seat;
    private double price;
    private Person person;

    // constructor for class Ticket
    public Ticket(String row,int seat,double price,Person person){
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;

    }

    // all the getters and setters for class Ticket
    public String getRow() {
        return row;
    }

    public int getSeat() {
        return seat;
    }

    public double getPrice() {
        return price;
    }

    public Person getPerson() {
        return person;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    // method for printing ticket information
    public void print(){
        System.out.println("Ticket\n");
        System.out.println("********************\n");
        System.out.println("Seat: "+row+seat);
        System.out.println("Total = £"+price);
        System.out.println("********************\n");
        person.print_info();
        System.out.println("********************\n");

    }

    // method for saving the ticket file
    public void save() {
        try {
            File ticketsDir = new File("Tickets");

            // checking if the file exists
            if (!ticketsDir.exists()) {
                // if it doesn't exist create a folder
                ticketsDir.mkdirs();
            }
            String fileName = row + seat + ".txt";
            File ticketFile = new File(ticketsDir, fileName);
            FileWriter file = new FileWriter(ticketFile);
            file.write("TICKET\n");
            file.write("\n********************\n");
            file.write("\nName: "+person.getName()+" "+person.getSurname());
            file.write("\nEmail: "+person.getEmail());
            file.write("\nSeat: "+row+seat);
            file.write("\n\n********************");
            file.write("\n\nPrice: £"+price);
            file.write("\n\n********************");
            file.close();
        } catch (IOException e) {
            System.out.println("Error Creating Ticket");
        }

    }
}
