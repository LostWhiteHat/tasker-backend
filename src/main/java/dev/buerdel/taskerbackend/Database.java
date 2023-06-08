package dev.buerdel.taskerbackend;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    private final String dbUrl = "jdbc:mysql://localhost:3306/tasker";
    private final String dbUser = "root";
    private final String dbPasswd = "asdf1234";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPasswd);
    }

    public boolean insert_user_task(String user, String task){
        String query = "INSERT INTO tasks(user,task) VALUES(?,?)";

        long id = 0;

        try (Connection conn = connect();
             PreparedStatement preparedStatement = conn.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS))
        {
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

    public ArrayList select_all_tasks() {
        String query = "SELECT * FROM tasks";

        try (Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);)
        {
            JsonConverter jsonConverter = new JsonConverter();
            ArrayList results = jsonConverter.get_json_result_array(resultSet);
            conn.close();
            return results;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return new ArrayList();
        }
    }

    public ArrayList select_user_tasks(String user){
        String query = "SELECT * FROM tasks WHERE user=?";

        try (Connection conn = connect();
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);)
        {
            preparedStatement.setString(1, user);
            ResultSet resultSet = preparedStatement.executeQuery();
            JsonConverter jsonConverter = new JsonConverter();
            ArrayList results = jsonConverter.get_json_result_array(resultSet);
            return results;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return new ArrayList<>();
        }
    }
}