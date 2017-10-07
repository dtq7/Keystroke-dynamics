
package keystrokeDatabase;

import java.sql.DriverManager;
import java.sql.*;


public final class databaseHandler {
    
    private static databaseHandler handler = null;
    
    private static final String URL="jdbc:mysql://localhost:3306/keystrokesystem";
    private static java.sql.Connection myConn= null;
    private static java.sql.Statement myStmt= null;

    private databaseHandler() {
        createConnection();
       
    }
    
    public static databaseHandler getInstance(){
        if(handler == null){
            handler = new databaseHandler();
        
        }
        return handler;
    }
    
    
    
    void createConnection(){
        try {
            myConn = DriverManager.getConnection(URL, "root", "mysql");
          
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public static ResultSet execQuery(String query){
        ResultSet myRs;
        try {
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Execution at execQuery:dataHandler" + e.getLocalizedMessage());
            return null;
        }finally{
        }
        return myRs;
    }
    
     public static boolean execAction(String qu){
        
        try {
            myStmt = myConn.createStatement();
            myStmt.execute(qu);
            return true;
        } catch (SQLException e) {
            return false;
        }finally{
        }
        
    }
}
