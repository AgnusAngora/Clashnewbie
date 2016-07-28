package cl.curso.java.clashnewbie.core;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class Usuario {

    @Id
    private int id;
    private String correo;
    private String password;
    
    
    public Usuario(){
        
    }
    
    public Usuario(String correo){
        this.correo = correo;
    }
    
    
    public Usuario(int id, String correo, String password){
    	this.id = id;
        this.correo = correo;
        this.password = password;
    }
    
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }
    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if( obj instanceof Usuario ){
            return ((Usuario)obj).getCorreo().equals(this.getCorreo());
        }
        return super.equals(obj);
    }
}

