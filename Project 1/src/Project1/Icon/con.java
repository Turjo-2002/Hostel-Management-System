package Project1.Icon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class con {
    Connection connection;

    java.sql.Statement statement;
    public con() {
        try {
            connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/hostelms","root","");
            statement = (Statement) connection.createStatement();
        }catch(Exception e){

            e.printStackTrace();

        }
    }
}