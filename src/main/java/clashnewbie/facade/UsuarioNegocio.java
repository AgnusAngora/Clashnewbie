package cl.curso.java.clashnewbie.facade;

import cl.curso.java.clashnewbie.core.Usuario;
import cl.curso.java.clashnewbie.dao.UsuarioDAO;

public class UsuarioNegocio {
    private UsuarioDAO objUsuarioDAO;

    public UsuarioNegocio() {
        this.objUsuarioDAO= new UsuarioDAO();
    }
    
    public Usuario compruebaUsuario(String login,String pass){
        return this.objUsuarioDAO.compruebaUsuario(login, pass);
    }
    
}




