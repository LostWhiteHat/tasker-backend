package dev.buerdel.taskerbackend;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class JsonConverter {

    public JsonConverter(){

    }

    public ArrayList get_json_result_array(ResultSet resultSet) throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columns = resultSetMetaData.getColumnCount();
        ArrayList results = new ArrayList<>();
        while (resultSet.next()){
            HashMap hashMap = new HashMap(columns);
            for (int i = 1; i <= columns; ++i){
                hashMap.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
            }
            results.add(hashMap);

        }
        return results;
    }

}
