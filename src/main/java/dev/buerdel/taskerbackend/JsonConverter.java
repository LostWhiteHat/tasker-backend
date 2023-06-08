package dev.buerdel.taskerbackend;

import org.apache.logging.log4j.util.Strings;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JsonConverter {

    public JsonConverter(){

    }

    public ArrayList get_json_result_array(ResultSet resultSet) throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columns = resultSetMetaData.getColumnCount();
        ArrayList results = new ArrayList<>();
        while (resultSet.next()){
            HashMap<String, Object> hashMap = new HashMap<String, Object>(columns);
            for (int i = 1; i <= columns; ++i){
                hashMap.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
            }
            results.add(hashMap);

        }
        return results;
    }

}
