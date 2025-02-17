package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class RegistrationDAO {
    
    public static final String QUERY_CREATE = "INSERT INTO registration (studentid, termid, crn) VALUES (?,?,?)";
    public static final String QUERY_DROP = "DELETE FROM registration WHERE studentid=? AND termid=? AND crn=?";
    public static final String QUERY_WITHDRAW = "DELETE FROM registration WHERE studentid=? AND termid=?";
    public static final String QUERY_LIST = "SELECT * FROM registration WHERE studentid=? AND termid=? ORDER BY crn";
    
    
    private final DAOFactory daoFactory;
    
    
    RegistrationDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    // Method to register a student for a course
    public boolean create(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                ps = conn.prepareStatement(QUERY_CREATE);
                
                //Bind the studentid, termid, and crn to the SQL query
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
                // Execute the SQL statement
                int updateCount = ps.executeUpdate();
                
                // If at least one row is updated in the database, set the result to return true
                if (updateCount > 0) {
                    result = true;
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
    
    // Method to drop a student's course registration
    public boolean delete(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
              
                ps = conn.prepareStatement(QUERY_DROP);
                
                // Bind the studentid, termid, and crn to the SQL query
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
                // Execute the query to delete registration
                int updateCount = ps.executeUpdate(); 
                
                // If deletion was successful, return true
                if (updateCount > 0) {
                    result = true;
                }
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
    //Method to withdraw a student from all courses in a specific term
    public boolean delete(int studentid, int termid) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                
                ps = conn.prepareStatement(QUERY_WITHDRAW);
                
                // Bind the studentid and termid to the SQL query
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                
                // Execute the query to withdraw
                int updateCount = ps.executeUpdate();
                
                // If withdrawal was successful, return true
                if (updateCount > 0) {
                    result = true;
                }
                
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    //Method to list all course registrations for a student in a given term
    public String list(int studentid, int termid) {
        
        String result = null;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                
                ps = conn.prepareStatement(QUERY_LIST);
                
                 //Bind the studentid and termid to the SQL query
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                
                //Execute the query and check if results exist
                boolean hasresults = ps.execute(); 
                if (hasresults) {
                    
                    //Get the result set
                    rs = ps.getResultSet();
                    
                    //Convert result set to JSON
                    result = DAOUtility.getResultSetAsJson(rs);
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
