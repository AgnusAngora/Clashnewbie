package cl.curso.java.clashnewbie.dao;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "ircuser")
public class IrcUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String nick;

    public IrcUser() {

    }

    public IrcUser(String id, String nick) {
        this.id = id;
        this.nick = nick;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    @Column(name = "nick_id")
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

}
