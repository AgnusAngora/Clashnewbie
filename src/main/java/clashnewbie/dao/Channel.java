package cl.curso.java.clashnewbie.dao;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "channel")
public class Channel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String description;
    private Set<IrcUser> ircUsers = new HashSet<>();
    private Set<IrcUser> operators = new HashSet<>();

    public Channel() {

    }

    public Channel(String id, String description) {
        this.id = id;
        this.description = description;
    }

    @Id
    @Column(name = "channel_id")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JoinTable(name = "user_by_channel", joinColumns = @JoinColumn(name = "nick_id"), inverseJoinColumns = @JoinColumn(name = "channel_id"))
    @OneToMany(fetch = FetchType.EAGER)
    public Set<IrcUser> getIrcUsers() {
        return ircUsers;
    }

    public void setIrcUsers(Set<IrcUser> ircUsers) {
        this.ircUsers = ircUsers;
    }

    @JoinTable(name = "operator_by_channel", joinColumns = @JoinColumn(name = "nick_id"), inverseJoinColumns = @JoinColumn(name = "channel_id"))
    @OneToMany(fetch = FetchType.EAGER)
    public Set<IrcUser> getOperators() {
        return operators;
    }

    public void setOperators(Set<IrcUser> operators) {
        this.operators = operators;
    }

    public void addIrcUser(IrcUser ircUser) {
        if (!this.ircUsers.contains(ircUser)) {
            this.ircUsers.add(ircUser);
        }
    }

    public void addOperator(IrcUser ircUser) {
        if (!this.operators.contains(ircUser)) {
            this.operators.add(ircUser);
        }
    }
}
