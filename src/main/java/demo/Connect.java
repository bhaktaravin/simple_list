package demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Connect {

    public static Connection connectToDB() {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/users";
        String username= "postgres";
        String dbPassword= "test1234";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcUrl, username, dbPassword);
            System.out.println("Connection to database successful!");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    //List all data
    public static ResultSet listData() {
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM Users");
            resultSet = preparedStatement.executeQuery();
            System.out.println("List of users:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                System.out.println("ID: " + id + ", First Name: " + firstName + ", Last Name: " + lastName + ", Email: " + email + ", Password: " + password);
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultSet;
    }


    //Insert data
    public static void insertData() {  // removed User user parameter
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        
        User user = new User(firstName, lastName, email, password);  // no conflict here
        
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO users (first_name, last_name, email, password) VALUES (?, ?, ?, ?)");
            
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            
            preparedStatement.executeUpdate();
            
            System.out.println("User " + user.getFirstName() + " " + user.getLastName() + " has been added to the database.");
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    
    //Delete data
    public static void deleteData() {
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM users WHERE first_name = ?");
            preparedStatement.setString(1, "John");
            preparedStatement.executeUpdate();
            System.out.println("User John Doe has been deleted from the database.");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Update Data
    public static void updateData() {
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE users SET last_name = ? WHERE first_name = ?");
            preparedStatement.setString(1, "Smith");
            preparedStatement.setString(2, "John");
            preparedStatement.executeUpdate();
            System.out.println("User John Doe has been updated to Smith.");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Find By Id
    public static void findById(int id) {
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id =" + id + "");
            preparedStatement.setInt(1, 1);
            preparedStatement.executeQuery();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


   
}
