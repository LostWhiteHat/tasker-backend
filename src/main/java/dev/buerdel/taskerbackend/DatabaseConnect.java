package dev.buerdel.taskerbackend;

import java.sql.*;

public class DatabaseConnect {

    private final String dbUrl = "jdbc:mysql://localhost:3306/tasker";
    private final String dbUser = "root";
    private final String dbPasswd = "asdf1234";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPasswd);
    }

    public boolean insert_user_task(String user, String task) {
        String statement = "INSERT INTO tasks(user,task) VALUES(?,?)";

        long id = 0;

        try (Connection conn = connect();
             PreparedStatement preparedStatement = conn.prepareStatement(statement,
                     Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, task);
            preparedStatement.executeUpdate();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean select_all_tasks(){
        String statement = "SELECT * FROM tasks";

        try (Connection conn = connect();
            PreparedStatement preparedStatement = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)){
            ;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

}
