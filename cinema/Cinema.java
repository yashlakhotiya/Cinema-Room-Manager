package cinema;
import java.util.*;

public class Cinema {

    public static void initializeSeatingArrangement(char[][] seating_arrangement){
        int rows = seating_arrangement.length;
        int cols = seating_arrangement[0].length;

        for(int i=0; i<rows; i++) {
            Arrays.fill(seating_arrangement[i], 0, cols, 'S');
        }
    }

    public static void showTheSeats(char[][] seating_arrangement){
        int rows = seating_arrangement.length;
        int cols = seating_arrangement[0].length;
        System.out.println("Cinema:");
        System.out.print("  ");
        for (int i=1; i<=cols; i++) {
            System.out.print(i + " ");
        }
        System.out.println("");
        for (int i=1; i<=rows; i++) {
            for (int j=0; j<=cols; j++){
                if (j==0) {
                    System.out.print(i + " ");
                }
                else{
                    System.out.print(seating_arrangement[i-1][j-1] + " ");
                }
            }
            System.out.println("");
        }
    }

    public static boolean isInputProper(int row_no, int col_no, int rows, int cols){
        if(row_no >= 1 && row_no <= rows && col_no >= 1 && col_no <= cols){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean isBooked(char[][] seating_arrangement, int row_no, int col_no){
        if(seating_arrangement[row_no-1][col_no-1] == 'B'){
            return true;
        }
        else{
            return false;
        }
    }

    public static void buyTheSeat(char[][] seating_arrangement, int row_no, int col_no){
        seating_arrangement[row_no-1][col_no-1] = 'B';
    }

    public static int calculatePrice(int row_no, int rows, int price_front, int price_back){
        boolean is_front = false;

        if (row_no >= 1 && row_no <= rows / 2) {
            is_front = true;
        }

        int ticketPrice = 0;

        if (is_front) {
            ticketPrice = price_front;
        } else {
            ticketPrice = price_back;
        }
        return ticketPrice;
    }

    public static int calculateTotalIncome(int rows, int cols, int price_front, int price_back){
        int total_income = 0;
        int front = rows/2;
        int back = rows - front;
        total_income = front*cols*price_front + back*cols*price_back;
        return total_income;
    }

    public static int calculateCurrentIncome(/*int[][] income,*/ char[][] seating_arrangement, int price_front, int price_back){
        int current_income = 0;
        for(int i=0; i<seating_arrangement.length; i++){
            for(int j=0; j<seating_arrangement[0].length; j++){
                if(seating_arrangement[i][j] == 'B'){
                    if(i <= seating_arrangement.length/2-1){
                        current_income += price_front;
                        //income[i][j] = price_front;
                        //System.out.print(price_front + " ");
                    }
                    else{
                        current_income += price_back;
                        //income[i][j] = price_back;
                        //System.out.print(price_back + " ");
                    }
                }
            }
        }
        return current_income;
    }

    public static int calculateBookedSeats(char[][] seating_arrangement, int price_front, int price_back){
        int booked_seats = 0;
        for(int i=0; i<seating_arrangement.length; i++){
            for(int j=0; j<seating_arrangement[0].length; j++){
                if(seating_arrangement[i][j] == 'B'){
                    booked_seats++;
                }
            }
        }
        return booked_seats;
    }

    public static void showStatistics(char[][] seating_arrangement, int rows, int cols, int price_front, int price_back){
        int booked_seats = calculateBookedSeats(seating_arrangement,price_front,price_back);
        float percent_booked_seats = (float)(booked_seats*100)/(rows*cols);
        //int[][] income = new int[seating_arrangement.length][seating_arrangement[0].length];
        int current_income = calculateCurrentIncome(/*income,*/seating_arrangement, price_front, price_back);
        int total_income = calculateTotalIncome(rows, cols, price_front, price_back);
        System.out.printf("Number of purchased tickets: %d\n",booked_seats);
        System.out.printf("Percentage: %.2f%%\n",percent_booked_seats);
        System.out.println("Current income: $" + current_income);
        System.out.println("Total income: $" + total_income);
        /*for(int[] row: income){
            for(int i: row){
                System.out.print(i + " ");
            }
            System.out.println("");
        }*/
    }


    public static void main(String[] args) {
        // Write your code here
        Scanner sc = new Scanner(System.in);
        int rows = 0, cols = 0;
        System.out.println("Enter the number of rows:");
        rows = sc.nextInt();
        System.out.println("Enter the number of seats in each row:");
        cols = sc.nextInt();

        char[][] seating_arrangement = new char[rows][cols];

        Cinema.initializeSeatingArrangement(seating_arrangement);

        int price_front = 0, price_back = 0;
        if(rows*cols <= 60){
            price_front = price_back = 10;
        }
        else {
            price_front = 10;
            price_back = 8;
        }

        int s = 0;
        int row_no = 0, col_no = 0, ticketPrice = 0;
        loop: while(true) {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");

            s = sc.nextInt();


            switch (s) {
                case 1:
                    Cinema.showTheSeats(seating_arrangement);
                    break;

                case 2:
                    System.out.println("Enter a row number:");
                    row_no = sc.nextInt();
                    System.out.println("Enter a seat number in that row:");
                    col_no = sc.nextInt();
                    while(!Cinema.isInputProper(row_no,col_no,rows,cols)){
                        System.out.println("Wrong input!");
                        System.out.println("Enter a row number:");
                        row_no = sc.nextInt();
                        System.out.println("Enter a seat number in that row:");
                        col_no = sc.nextInt();
                    }
                    while(Cinema.isBooked(seating_arrangement,row_no,col_no)){
                        if(Cinema.isBooked(seating_arrangement,row_no,col_no)) {
                            System.out.println("That ticket has already been purchased!");
                        }
                        System.out.println("Enter a row number:");
                        row_no = sc.nextInt();
                        System.out.println("Enter a seat number in that row:");
                        col_no = sc.nextInt();
                    }
                    Cinema.buyTheSeat(seating_arrangement,row_no,col_no);
                    ticketPrice = Cinema.calculatePrice(row_no, rows, price_front, price_back);
                    System.out.println("Ticket price: $" + ticketPrice);
                    Cinema.showTheSeats(seating_arrangement);
                    break;

                case 3:
                    Cinema.showStatistics(seating_arrangement, rows, cols, price_front, price_back);
                    break;

                case 0:
                    break loop;
            }
        }
    }
}