import java.io.File;
import java.util.Scanner;
public class PlaneManagement {
    // initializing global variables
    static Ticket[] tickets = new Ticket [52];
    static int row;
    static int seat;
    static String row_letter;
    static Scanner read = new Scanner(System.in);
    static int[][] seats = new int[4][];
    static int num = 0;

    // main code
    public static void main(String[] args) {
        int selection;

        // Assigning columns for each row in the ragged array 'seats'
        seats[0] = new int[14];
        seats[1] = new int[12];
        seats[2] = new int[12];
        seats[3] = new int[14];

        // The Welcome massage and main menu
        System.out.println("\nWelcome to the Plane Management application\n");
        // main menu loop until user exits
        do {
            System.out.println("\n****************************************\n");
            System.out.println("*             " + "MENU OPTIONS" + "             *\n");
            System.out.println("****************************************\n");



            System.out.println("1) Buy a seat");
            System.out.println("2) Cancel a seat");
            System.out.println("3) Find first available seat");
            System.out.println("4) Show seating plan");
            System.out.println("5) Print tickets information and total sales");
            System.out.println("6) Search ticket");
            System.out.println("0) Quit\n");
            System.out.println("****************************************\n");
            System.out.print("Please select an option: ");


            do {
                try{
                    selection = read.nextInt();
                    if (!(selection<0 || selection>7)){
                        break;
                    }
                    else {
                        System.out.println("Invalid input. Enter a number between 0 and 6 ");
                    }
                }
                catch (Exception e){
                    System.out.println("Invalid input try again!");
                    read.nextLine();
                }
            }while (true);


            // methods declaration on user selection input
            switch (selection) {
                case 1: {
                    buy_seat();
                    break;
                }
                case 2: {
                    cancel_seat();
                    break;
                }
                case 3: {
                    find_first_available();
                    break;
                }
                case 4: {
                    show_seating_plan();
                    break;
                }
                case 5: {
                    print_tickets_info();
                    break;
                }
                case 6: {
                    search_ticket();
                    break;
                }
            }
        } while (selection != 0);
        System.out.println("\nExiting Plane Management application");
        System.exit(0);
    }

    // method for buying a seat
    public static void buy_seat() {
        double price;
        System.out.println("Enter your name: ");
        String name = read.next();
        System.out.println("Enter your surname; ");
        String surname = read.next();
        System.out.println("Enter your email; ");
        String email = read.next();

        // object for passing variables to class Person
        Person obj = new Person(name,surname,email);

        // methods for row and seat inputs
        input_row();
        input_seat();

        if (seats[row][seat] == 1) {
            System.out.println("\nSeat is already booked!\n");
        } else {
            if (seat>0 && seat+1<6){
                price = 200;
            } else if (seat+1>5 && seat+1<10) {
                price = 150;
            }
            else {
                price = 180;
            }

            // object for passing variables to class Ticket
            Ticket obj2 = new Ticket(row_letter,(seat+1),price,obj);

            // storing data to the array list
            tickets[num] = obj2;
            num++;

            // updating the seats array corresponding index
            seats[row][seat] = 1;

            System.out.println("\nSeat purchased successfully!\n");

            // calling the save method from class Ticket to save the ticket file
            obj2.save();
        }
    }

    // method for cancelling seat
    public static void cancel_seat() {

        // taking inputs for row and seat
        input_row();
        input_seat();

        // checking whether the seat is booked and if its booked removing it from array list
        if (seats[row][seat] == 1) {
            for (int i = 0; i < tickets.length; i++) {
                Ticket tick = tickets[i];
                if (tick != null && row_letter.equals(tick.getRow()) && seat + 1 == tick.getSeat()) {
                    // remove the ticket
                    tickets[i] = null;
                    num--;
                }
            }

            // assigning the corresponding index in the seat array to 0
            seats[row][seat] = 0;

            // deleting the created ticket file
            File ticketFile = new File("Tickets", row_letter + (seat +1) + ".txt");
            boolean deleted = ticketFile.delete();

            if (deleted){
                System.out.println("\nSeat cancellation successfully");
            }
        } else {
            System.out.println("The seat is not booked");
        }
    }

    // method for finding the first available seat
    public static void find_first_available() {

        // checking each row for an available seat
        for (int row = 0; row < seats.length; row++) {
            for (int col = 0; col < seats[row].length; col++) {
                if (seats[row][col] == 0) {
                    String r = null;

                    if(row == 0){
                        r = "A";
                    } else if (row == 1) {
                        r = "B";
                    } else if (row == 2) {
                        r = "C";
                    } else if (row == 3) {
                        r = "D";
                    }

                    System.out.println("First available seat: Row " + (r) + ", Seat " + (col + 1));
                    System.out.println();
                    return; // for exiting the method and returning to main method
                }
            }
        }

        System.out.println("No seats available");
    }

    // method for showing the seat plan
    public static void show_seating_plan(){
        System.out.println("********************");
        System.out.println("   Seating Plan");
        System.out.println("********************\n");
        // printing out element by element in the seats array by checking if it is booked or not
        for (int[] row : seats) {
            for (int seat : row) {
                if (seat == 0) {
                    System.out.print("O ");
                }
                else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // method for printing all tickets information that have been sold
    public static void print_tickets_info(){
        double sum = 0;
        int number = 1;

        System.out.println("********************");
        System.out.println("All Tickets Sold");
        System.out.println("********************\n");

        // retrieving data from the array list
        for(Ticket tick : tickets){
            if (tick != null) {
                // displaying each ticket one by one
                System.out.println(number + ") " + tick.getPerson().getName() + " " +
                        tick.getPerson().getSurname() + " " + tick.getPerson().getEmail());
                System.out.println("   Seat " + tick.getRow() + tick.getSeat() + "\n");
                System.out.println("   Price: " + tick.getPrice());
                System.out.println("********************");

                number++;
                // calculating total price of all tickets
                sum += tick.getPrice();
            }
        }
        if (sum == 0){
            System.out.println("No seats booked");
        }else {
            System.out.println("Total price of tickets: £"+sum);
        }
    }

    // method for searching a ticket
    public static void search_ticket(){

        // user input row letter and seat number
        input_row();
        input_seat();

        // checking the stored data in the array list
        for (Ticket tick : tickets){
            if (tick == null){
                break;
            }
            if ((row_letter.equals(tick.getRow()) && (seat+1 == tick.getSeat()))){
                System.out.println("\nSeat is already booked\n");
                System.out.println("********************");
                System.out.println("Name: "+tick.getPerson().getName()+" "+tick.getPerson().getSurname());
                System.out.println("Email: "+tick.getPerson().getEmail());
                System.out.println("********************");
                System.out.println("Seat: "+tick.getRow()+tick.getSeat());
                System.out.println("********************");
                return; // exiting the for loop and returning to the main method
            }

            else {
                break;
            }

        }
        System.out.println("This seat is available");
    }
    public static void input_row(){

        // method for user input row letter
        System.out.println("Enter the row letter (A-D): ");
        row_letter = read.next().toUpperCase();
        while (!(row_letter.equals("A") || row_letter.equals("B") || row_letter.equals("C") || row_letter.equals("D"))){
            System.out.println("Invalid row input. Please enter a letter between A and D. ");
            row_letter = read.next().toUpperCase();
        }
        switch (row_letter){
            case "A":{
                row = 0;
                break;
            }
            case "B":{
                row = 1;
                break;
            }
            case "C":{
                row = 2;
                break;
            }
            case "D":{
                row = 3;
                break;
            }
        }
    }
    public static void input_seat(){

        // method for user input seat number;
        do {
            try {
                if (row == 1 || row == 2){
                System.out.println("Enter the seat number (1-12): ");
                }
                else {
                    System.out.println("Enter the seat number (1-14): ");
                }
                seat = read.nextInt() - 1;

                while (seat < 0 || seat >= 14 || ((row==1 || row==2) && (seat==12||seat==13))) {
                    if ((row==1 || row==2) && (seat==12||seat==13)){
                        System.out.println("for row B and C. Please enter a value between 1 and 12.");
                    }
                    else {
                        System.out.println("Invalid seat number. Please enter a value between 1 and 14.");
                    }
                    seat = read.nextInt() - 1;

                }
                break;
            } catch (Exception e){
                System.out.println("Invalid input!");
                read.nextLine();
            }
        }while (true);

    }
}