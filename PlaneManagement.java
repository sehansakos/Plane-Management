import java.util.NoSuchElementException;
import java.util.Scanner;

public class PlaneManagement {
    // Constant variable total number of rows
    private static final int NumRows = 4;
    // Constant array the number of seats
    private static final int[] SeatsPerRow = {14, 12, 12, 14};
    // 2D array to store the seating plan
    private static int[][] seats;
    // Array of ticket objects to store information about purchased tickets
    private static final Ticket[] ticketsSold = new Ticket[NumRows * 14];
    // Array of person objects to store passenger information
    private static final Person[] person = new Person[NumRows * 14];


    public static void main(String[] args) {
        System.out.println("Welcome to the Plane Management application ");
        chooseSeats();

        Scanner input = new Scanner(System.in);

        int options = -1;
        while (options != 0) {
            // Display menu options
            System.out.println("\n******************************************");
            System.out.println("*               MENU OPTIONS             *");
            System.out.println("******************************************");
            System.out.println("1) Buy a seat");
            System.out.println("2) Cancel a seat");
            System.out.println("3) Find first available seat");
            System.out.println("4) Show seating plan");
            System.out.println("5) Print tickets information");
            System.out.println("6) Search ticket");
            System.out.println("0) Quit");
            System.out.println("******************************************");

            System.out.println("\nPlease select an option: ");
            try {
                options = input.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine();
                continue;
            }

            // Execute selected menu option
            switch (options) {
                case 1:
                    buySeat();
                    break;
                case 2:
                    cancelSeat();
                    break;
                case 3:
                    findFirstAvailable();
                    break;
                case 4:
                    showSeatingPlan();
                    break;
                case 5:
                    printTicketsInfo();
                    break;
                case 6:
                    searchTicket();
                    break;
                case 0:
                    System.out.println("Thank you for using our service. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please select a number from the given menu options.");
            }
        }
    }

    // Initialize seats on the plane
    private static void chooseSeats() {
        seats = new int[NumRows][];
        for (int i = 0; i < NumRows; i++) {
            seats[i] = new int[SeatsPerRow[i]];

        }
    }


    //Seat purchase process
    private static void buySeat() {
        try {
            Scanner input = new Scanner(System.in);

            // Get user input
            System.out.print("Enter row (A, B, C, D): ");
            char row = input.next().toUpperCase().charAt(0);
            System.out.print("Enter seat number: ");
            int seatNumber = input.nextInt();

            // Check if the row index is valid
            int rowIndex = row - 'A';
            if (rowIndex < 0 || rowIndex >= NumRows) {
                System.out.println("Invalid row. Please try again.");
                return;
            }
            // Check if the seat number is valid
            if (seatNumber < 1 || seatNumber > SeatsPerRow[rowIndex]) {
                System.out.println("Invalid seat number.Please try again.");
                return;
            }
            // Check if the seat is already reserved
            if (seats[rowIndex][seatNumber - 1] == 1) {
                System.out.println("Seat already reserved.Please choose another seat.");
                return;
            }

            // Person Information
            System.out.print("Enter name: ");
            String name = input.next();
            System.out.print("Enter surname: ");
            String surname = input.next();
            System.out.print("Enter email: ");
            String email = input.next();


            // Create Person object
            person[seatNumber * (rowIndex + 1)] = new Person(name, surname, email);

            // Calculate price based on seat location
            double price = calculatePrice(seatNumber);

            // Create Ticket object
            ticketsSold[seatNumber * (rowIndex + 1)] = new Ticket(String.valueOf(row), seatNumber, price, person[seatNumber * (rowIndex + 1)]);
            ticketsSold[seatNumber * (rowIndex + 1)].setRow(String.valueOf(row));
            ticketsSold[seatNumber * (rowIndex + 1)].setSeat(seatNumber);
            ticketsSold[seatNumber * (rowIndex + 1)].setPrice(price);
            ticketsSold[seatNumber * (rowIndex + 1)].setPerson(person[seatNumber * (rowIndex + 1)]);

            // Print ticket info method of the ticket object to display ticket details
            ticketsSold[seatNumber * (rowIndex + 1)].printTicketInfo();

            // Mark seat as sold, updates the seats array to indicate that the seat is no longer available
            seats[rowIndex][seatNumber - 1] = 1;

            // Confirmation message
            System.out.println("Seat " + row + seatNumber + " sold successfully.");

            // Save ticket calls a method to save the ticket information to a file
            ticketsSold[seatNumber * (rowIndex + 1)].save();

        } catch (NoSuchElementException e) {
            // Handle invalid input format
            System.out.println("Invalid input. Please enter in correct form.");
        }
    }

    // Tickets prices
    private static double calculatePrice(int seatNumber) {

        if (seatNumber >= 0 && seatNumber <= 5) {
            return 200.0;
        } else if (seatNumber >= 6 && seatNumber <= 9) {
            return 150.0;
        } else {
            return 180.0;
        }
    }

    // Seats cancellation
    private static void cancelSeat() {
        try {
            Scanner input = new Scanner(System.in);

            // Get user input for
            System.out.print("Enter row (A, B, C, D): ");
            char row = input.next().toUpperCase().charAt(0);
            System.out.print("Enter seat number: ");
            int seatNumber = input.nextInt();

            // Check if the row index is valid
            int rowIndex = row - 'A';
            if (rowIndex < 0 || rowIndex >= NumRows) {
                System.out.println("Invalid row. Please try again.");
                return;
            }

            // Check if the seat number is valid
            if (seatNumber < 1 || seatNumber > SeatsPerRow[rowIndex]) {
                System.out.println("Invalid seat number.Please try again.");
                return;
            }

            // Check if the seat is already reserved
            if (seats[rowIndex][seatNumber - 1] == 0) {
                System.out.println("Seat is already available.");
                return;
            }

            // Cancel the seat
            seats[rowIndex][seatNumber - 1] = 0; // Mark seat as available in the seats array
            ticketsSold[seatNumber * (rowIndex + 1)] = null; // Remove corresponding ticket

            // Confirmation message
            System.out.println("Seat " + row + seatNumber + " cancelled successfully.");

        } catch (NoSuchElementException e) {
            // Handle invalid input format
            System.out.println("Invalid input. Please enter the correct form.");
        }
    }


    // Find the first available seat
    private static void findFirstAvailable() {
        // Loop through all rows and seats
        for (int i = 0; i < NumRows; i++) {
            for (int j = 0; j < SeatsPerRow[i]; j++) {
                // Check available seats
                if (seats[i][j] == 0) {
                    // Convert row index to character
                    char row = (char) ('A' + i);
                    // Print the first available seat details
                    System.out.println("First available seat found at row " + row + ", seat " + (j + 1));
                    return;
                }
            }
        }
        // Not find to available seat
        System.out.println("Sorry, no available seats.");
    }

    // Seating plan
    private static void showSeatingPlan() {
        System.out.println("Seating Plan:");

        // Loop through all rows and seats
        for (int i = 0; i < NumRows; i++) {
            // Convert row index to character
            char row = (char) ('A' + i);
            System.out.print(row + " ");

            // Loop through seats in the current row
            for (int j = 0; j < SeatsPerRow[i]; j++) {
                // Available seats
                if (seats[i][j] == 0) {
                    System.out.print("O ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println(); // Move to the next line after each row
        }

    }

    // Print tickets information

    private static void printTicketsInfo() {
        double totalSales = 0.0; // Total sales amount
        System.out.println("Tickets Information:");

        // Counter for ticketsSold array
        int i = 0;
        for (Ticket ticket : ticketsSold) {
            // Check if the tickets is not null
            if (ticket != null) {
                System.out.println("\nTicket " + i); // Print ticket number
                ticketsSold[i].printTicketInfo(); // Call the tickets printTicketInfo method
                totalSales += ticketsSold[i].getPrice(); // Add ticket price to total sales

            }
            i++; // Increment counter for next iteration
        }
        // Print total sales amount
        System.out.println("\nTotal Amount: £" + totalSales);
    }

    // Search for a ticket
    private static void searchTicket() {
        try {
            Scanner input = new Scanner(System.in);

            // Ask for row letter and seat number
            System.out.print("Enter row letter (A-D): ");
            char row = input.next().toUpperCase().charAt(0);
            System.out.print("Enter seat number: ");
            int seatNumber = input.nextInt();

            // Calculate row index from the entered row letter
            int rowIndex = row - 'A';

            // Validate user input for row and seat number
            if (rowIndex < 0 || rowIndex >= NumRows || seatNumber < 1 || seatNumber > SeatsPerRow[rowIndex]) {
                System.out.println("Invalid row or seat number.");
                return;
            }

            // Check if a ticket exists for the given seat
            if (ticketsSold[seatNumber * (rowIndex + 1)] != null && ticketsSold[seatNumber * (rowIndex + 1)].getSeat() == seatNumber) {
                ticketsSold[seatNumber * (rowIndex + 1)].printTicketInfo(); // Inform user if seat is not sold
            } else {
                System.out.println("This seat is available."); // Inform user if seat in not sold
            }
        } catch (NoSuchElementException e) {
            // Handle invalid input format
            System.out.println("Invalid input. Please enter the correct form.");

        }
    }
}
