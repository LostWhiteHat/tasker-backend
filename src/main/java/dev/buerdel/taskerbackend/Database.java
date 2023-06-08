package dev.buerdel.taskerbackend;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/tasker", "root", "asdf1234");
    }

    public boolean insert_user_task(String user, String task){
        try (Connection conn = connect();
             PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO tasks(user,task) VALUES(?,?)",
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

    public ArrayList<HashMap<String, Object>> select_all_tasks() {
        try (Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tasks"))
        {
            JsonConverter jsonConverter = new JsonConverter();
            ArrayList<HashMap<String, Object>> results = jsonConverter.get_json_result_array(resultSet);
            conn.close();
            return results;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return new ArrayList<>();
        }
    }

    public ArrayList<HashMap<String, Object>> select_user_tasks(String user){
        try (Connection conn = connect();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM tasks WHERE user=?", Statement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setString(1, user);
            ResultSet resultSet = preparedStatement.executeQuery();
            JsonConverter jsonConverter = new JsonConverter();
            return jsonConverter.get_json_result_array(resultSet);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean delete_task(int id){
        try (Connection conn = connect();
        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM tasks WHERE id=?", Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
}