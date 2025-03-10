package demo;

import java.sql.ResultSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
       
       // Menu
        int option = 0;
        ResultSet resultSet = null;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("1. Insert data");
            System.out.println("2. List data");
            System.out.println("3. Update data");
            System.out.println("4. Delete data");
            System.out.println("5. Find By Id");
            System.out.println("6. Exit");
            System.out.println("Select an option: ");
            option = scanner.nextInt();
            switch (option) {
                case 1 -> Connect.insertData();
                case 2 -> resultSet = Connect.listData();
                case 3 -> Connect.updateData();
                case 4 -> Connect.deleteData();
                case 5 -> Connect.findById(1);
                case 6 -> System.exit(0);
                default -> System.out.println("Invalid option.");
            }
        } while (option != 6);
        
        scanner.close();


        listData(resultSet);
            }
        
            private static void listData(ResultSet resultSet) {
                try {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String firstName = resultSet.getString("first_name");
                        String lastName = resultSet.getString("last_name");
                        String email = resultSet.getString("email");
                        String password = resultSet.getString("password");
                        System.out.println("ID: " + id + ", First Name: " + firstName + ", Last Name: " + lastName + ", Email: " + email + ", Password: " + password);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }
}