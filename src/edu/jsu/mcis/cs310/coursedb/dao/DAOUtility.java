package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import com.github.cliftonlabs.json_simple.*;
import java.util.ArrayList;

public class DAOUtility {
    
    public static final int TERMID_FA24 = 1;
    
    
    public static String getResultSetAsJson(ResultSet rs) {
        
        
        JsonArray records = new JsonArray();
        
        
        try {
        
            if (rs != null) {
                
            //Retrieve metadata to access details about table columns
            ResultSetMetaData metadata = rs.getMetaData();
            int columnCount = metadata.getColumnCount();
            
            //Process each row from the ResultSet
            while (rs.next()) {
                JsonObject row = new JsonObject();
                
                //Loop through columns and extract values
                for (int i = 1; i <= columnCount; ++i) {
                    String columnname = metadata.getColumnName(i);// Get the column name
                    row.put(columnname, rs.getObject(i).toString());// Get the column value
                }
                
                // Add the row to the JsonArray
                records.add(row);
            }


            }
            
        }
        catch (Exception e) {
            
            //Output error details for debugging
            e.printStackTrace();
        }
        
        return Jsoner.serialize(records);
        
    }
    
}
