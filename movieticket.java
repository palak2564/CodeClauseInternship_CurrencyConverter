import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        String[] movies = {"Avengers", "Titanic", "Jurassic Park"};
        boolean[][] seats = new boolean[3][40]; 

        while (true) {
            System.out.println("            ***             ");
            System.out.println("Welcome to Movie Booking!");
            System.out.println("1. View Movies");
            System.out.println("2. Book Ticket");
            System.out.println("3. Exit");
            System.out.println("            ***             ");
            System.out.print("Enter your choice: ");
            int choice = input.nextInt();
            input.nextLine();
            
            if (choice == 1) {
                viewMovies(movies, seats);
            } else if (choice == 2) {
                bookTicket(input, movies, seats);
            } else if (choice == 3) {
                System.out.println("Thank you for using Movie Booking!");
                System.out.println("            ***             ");
                break;
            } else {
                System.out.println("Invalid choice");
                System.out.println("            ***             ");
            }
            
            System.out.println();
        }
        input.close();
    }

    static void viewMovies(String[] movies, boolean[][] seats) {
        for (int i = 0; i < movies.length; i++) {
            int availableSeats = 40 - countBookedSeats(seats[i]);
            System.out.println((i+1) + ". " + movies[i] + " - Seats available: " + availableSeats);
        }
    }

    static void bookTicket(Scanner input, String[] movies, boolean[][] seats) {
        viewMovies(movies, seats);
        System.out.print("Enter movie number: ");
        int movieChoice = input.nextInt() - 1;
        input.nextLine();

        if (movieChoice < 0 || movieChoice >= movies.length) {
            System.out.println("Invalid movie choice.");
            return;
        }

        displaySeats(seats[movieChoice]);

        System.out.print("How many tickets do you want to book? ");
        int numTickets = input.nextInt();
        input.nextLine();

        if (numTickets <= 0) {
            System.out.println("Invalid number of tickets.");
            return;
        }

        System.out.print("Enter seat number(s) to book (separated by commas if more than one): ");
        String seatInput = input.nextLine();
        String[] seatChoices = seatInput.split(",");

        if (seatChoices.length != numTickets) {
            System.out.println("Number of seats doesn't match the number of tickets.");
            return;
        }

        boolean allSeatsAvailable = true;
        for (String seatChoice : seatChoices) {
            int seat = Integer.parseInt(seatChoice.trim()) - 1;
            if (seat < 0 || seat >= 40 || seats[movieChoice][seat]) {
                allSeatsAvailable = false;
                break;
            }
        }

        if (allSeatsAvailable) {
            for (String seatChoice : seatChoices) {
                int seat = Integer.parseInt(seatChoice.trim()) - 1;
                seats[movieChoice][seat] = true;
            }
            System.out.println("Tickets booked for " + movies[movieChoice] + ", Seats: " + seatInput);
        } else {
            System.out.println("One or more selected seats are invalid or already booked.");
        }
    }

    static void displaySeats(boolean[] movieSeats) {
        System.out.println("Seat layout (X means booked):");
        for (int i = 0; i < 40; i++) {
            if (movieSeats[i]) {
                System.out.print("X  ");
            } else {
                System.out.printf("%-3d", i + 1);
            }
            if ((i + 1) % 10 == 0) System.out.println();
        }
        System.out.println();
    }

    static int countBookedSeats(boolean[] movieSeats) {
        int count = 0;
        for (boolean seat : movieSeats) {
            if (seat) count++;
        }
        return count;
    }
}
