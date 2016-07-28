package cl.curso.java.clashnewbie.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import cl.curso.java.clashnewbie.clash_newbie.exceptions.ConexionException;

public class Conexion {
    
     public static Connection getConexion() throws ConexionException{
        Connection con=null;
        try{
            String driverClassName="com.mysql.jdbc.Driver";
            String driverUrl="jdbc:mysql://localhost:81/clashnewbie";
            Class.forName(driverClassName);
            con= DriverManager.getConnection(driverUrl,"root","");
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new ConexionException("error al conectar la BD"+e.getMessage());
        }
        return con;
    }
}




