//Ticket.java
import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    // Fields to store ticket information
    private String row;
    private int seat;
    private double price;
    private Person person;

    // Constructor to create a ticket object with details
    public Ticket(String row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    // Getter and Setter methods for accessing and modifying tickets

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


    // Print ticket information to console
    public void printTicketInfo(){
        System.out.println("\nRow:" + getRow());
        System.out.println("Seat:" + getSeat());
        System.out.println("Price:" + getPrice());
        System.out.println("Person Information:" );
        person.printPersonInfo(); // Delegate person information printing to person object
    }

    // Save ticket information to file

    public void save(){
        String row = getRow();
        int seat = getSeat();
        String fileName = row + seat + ".txt ";// Creat filename based on row and seat

        try(FileWriter writer = new FileWriter(fileName)){
            writer.write("Ticket information:\n " );
            writer.write("Row: " + row + " \n ");
            writer.write("Seat: " + seat + " \n ");
            writer.write("Price: £" + price + " \n ");
            writer.write("Person Information: \n " );
            writer.write("Name: " + person.getName() + " \n ");
            writer.write("Surname: " + person.getSurname() + " \n ");
            writer.write("Email: " + person.getEmail() + " \n ");
            System.out.println("Ticket information saved to file:" +fileName);

        }catch (IOException e){
            // Handle invalid input format
            System.out.println("Error writing ticket information to file:" + e.getMessage());
        }

    }
}
