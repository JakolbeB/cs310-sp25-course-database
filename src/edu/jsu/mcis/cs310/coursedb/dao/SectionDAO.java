package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class SectionDAO {
    
    private static final String QUERY_FIND = "SELECT * FROM section WHERE termid = ? AND subjectid = ? AND num = ? ORDER BY crn";
    
    private final DAOFactory daoFactory;
    
    SectionDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public String find(int termid, String subjectid, String num) {
        
        String result = "[]";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                // Prepare the SQL statement
                 ps = conn.prepareStatement(QUERY_FIND); 
                // Set the values for the parameters in the SQL statement
                ps.setInt(1, termid);
                ps.setString(2, subjectid);
                ps.setString(3, num);
                // Execute the SQL statement and check if it has results
                boolean hasResults = ps.execute(); 
                if (hasResults) {
                    // Get the ResultSet from the executed SQL statement
                    rs = ps.getResultSet(); 
                    // Convert the ResultSet to a JSON string using utility method
                    result = DAOUtility.getResultSetAsJson(rs); // Convert the ResultSet to a JSON string using utility method
                }
              
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
}