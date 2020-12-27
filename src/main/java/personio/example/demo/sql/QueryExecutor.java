package personio.example.demo.sql;

import java.sql.*;
public class QueryExecutor{
    public static void main(String args[]){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/org_db?characterEncoding=utf8","root","password");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from users");
            while(rs.next())
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

    private static Statement createStatement() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/org_db?characterEncoding=utf8","root","password");
        Statement stmt=con.createStatement();
        return stmt;
    }

    public static ResultSet execReads(String sqlQuery) throws SQLException, ClassNotFoundException {
        Statement stmt = createStatement();
        ResultSet rs=stmt.executeQuery(sqlQuery);
        stmt.getConnection().close();
        return rs;
    }

    public static int execWrites(String sqlQuery) throws SQLException, ClassNotFoundException {
        Statement stmt = createStatement();
        int count =  stmt.executeUpdate(sqlQuery);
        stmt.getConnection().close();
        return count;
    }
}
