package cl.curso.java.clashnewbie.dao;


import cl.curso.java.clashnewbie.core.Usuario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;


public class UsuarioDAO implements ICrud{

    public UsuarioDAO() {
    }
        
    //<editor-fold defaultstate="collapsed" desc="Crud">
    public boolean addElemento(Object objetoInsert) {
        Usuario objUsuario=(Usuario)objetoInsert;
        try{
            Connection con= Conexion.getConexion();
            String query="INSERT INTO usuario VALUES(?,?,?)";
            PreparedStatement ps=con.prepareStatement(query);
            ps.setInt(1,ultimoID());
            ps.setString(2, objUsuario.getCorreo());
            ps.setString(3, objUsuario.getPassword());
            try{
                return ps.executeUpdate()==1;
            }catch(Exception e){
                System.out.println("error al insertar");
                return false;
            }
        }catch(Exception e){
            System.out.println("Error al Insertar en la BD"+e.getMessage());
        }
        return false;
    }
    
    public List readElementos() {
        List listadoUsuarios= new LinkedList();
        try {
            Connection con= Conexion.getConexion();
            String query="select * from usuario";
            PreparedStatement ps=con.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Usuario objUsuario= new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3));
                listadoUsuarios.add(objUsuario);
            }
        } catch (Exception e) {
            System.out.println("error al conectar la BD"+e.getMessage());
        }
        return listadoUsuarios;
    }
    

    public boolean updateElemento(Object objetoUpdate) {
        Usuario objUsuario=(Usuario)objetoUpdate;
        try {
            Connection con = Conexion.getConexion();
            String query="update usuario set correo_usuario=?,pass_usuario=?, where id_usuario=?";
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1, objUsuario.getCorreo());
            ps.setString(2, objUsuario.getPassword());
            ps.setInt(3, objUsuario.getId());
            try{
                return ps.executeUpdate()==1;
            }catch(Exception e){
                System.out.println("Problemas al updatear");
            }
        } catch (Exception e) {
            System.out.println("No se pudo updatear la base de datos");
        }
        return false;
    }
    
    public boolean deleteElemento(int codigo) {
        try {
            Connection con =Conexion.getConexion();
            String query="DELETE FROM usuario WHERE id_usuario=?";
            PreparedStatement ps=con.prepareStatement(query);
            ps.setInt(1, codigo);
            try{
                return ps.executeUpdate()==1;
            }catch(Exception e){
                System.out.println("Error al Eliminar el registros"+e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error al borrar los registros"+e.getMessage());
        }
        return false;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public int ultimoID() {
        int ultim=0;
        try {
            Connection con= Conexion.getConexion();
            String query="SELECT MAX(id_usuario) FROM usuario";
            PreparedStatement ps=con.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                ultim=rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("No se pudo conectar a la BD"+e.getMessage());
        }
        return ultim+1;
    }
    
    
    public Usuario compruebaUsuario(String correo,String pass){
        Usuario ObjUsuario= null;
        try{
            Connection con = Conexion.getConexion();
            String query="SELECT * FROM usuario WHERE correo_usuario= ? AND pass_usuario= ?";
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1, correo);
            ps.setString(2, pass);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                ObjUsuario= new Usuario(rs.getInt(1),rs.getString(2),rs.getString(3));
            }
        }catch(Exception e){
            System.out.println("Error al conectar con la BD"+e.getMessage());
        }
        
        return ObjUsuario;
    }
    
    //</editor-fold>
    
    
}



