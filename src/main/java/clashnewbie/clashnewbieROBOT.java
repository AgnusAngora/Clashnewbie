package cl.curso.java.proyecto.clashnewbie;
package ****;

import com.google.common.collect.ImmutableSortedSet;

import cl.curso.java.clashnewbie.clash_newbie.dao.ChannelDAO;
import cl.curso.java.clashnewbie.clash_newbie.dao.IrcUser;
import cl.curso.java.clashnewbie.clash_newbie.dao.IrcUsersDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.pircbotx.*;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import com.btilm305.clashapi.BTClashWrapper;
import com.btilm305.clashapi.ClashAPI;
import com.btilm305.clashapi.Player;
import com.btilm305.clashapi.exception.ClashException;

@SuppressWarnings("rawtypes")
public class clashnewbieROBOT extends ListenerAdapter {

    private final Map<String, String> nickIRC = Collections.synchronizedMap(new ConcurrentHashMap<String, String>());
    private List<String> admins = new ArrayList<>(Arrays.asList(new String[]{"__Joseph"}));
    private final Map<Channel, List<String>> operators = Collections.synchronizedMap(new ConcurrentHashMap<Channel, List<String>>());
	private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6IjQyOTk0YWFkLWRiODUtNGNkNC1iYTQxLTljNmRkOTg1NmU1MCIsImlhdCI6MTQ2ODU5ODUxMywic3ViIjoiZGV2ZWxvcGVyL2I1YThhZWVkLWZmZmQtOTUxYS0yOWZlLWFmMzU3NmY0ZWJmOSIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjIwMS4yMjMuMTk3LjQ0Il0sInR5cGUiOiJjbGllbnQifV19.UQIAVnn1An_Y5Kqb7AawpGWsIyV50slw3Ox2OP2iwr4YahydIJhsn2ruJwyrenV0tlVBxJ1HDM_MHUi3YFBpGg";
	private ClashAPI clashAPI = BTClashWrapper.getAPIInstance(token);

    @Override
    public void onMessage(MessageEvent event) throws Exception {

        String message = event.getMessage();
        String sender = event.getUser().getNick();
        Channel channel = event.getChannel();
		

        //When someone says ?helloworld respond with "Hello World"
        

        if (message.startsWith(".help") || message.startsWith(".ayuda")) {
            event.respond("Comandos del BoT: https://goo.gl/4gg6zP");
        }

        if (message.equalsIgnoreCase("time")) {
            String time = new java.util.Date().toString();
            event.respond(sender + ": " + time);
        }

        if (message.startsWith(".regad")) {
            if (sender.equalsIgnoreCase("__Joseph")) {
                String[] arrayCommands = message.split(" ");
                //.regAD __Joseph --> [__Joseph]
                if (arrayCommands.length == 2) {
                    regAd(arrayCommands[1]);
                    event.respond("Nick anadido como ADMIN exitosamente");
                } else {
                    event.respond("To add an Admin, write .regAD + NickName(Example: .regAD __Joseph)");
                }
            }
        } else if (message.startsWith(".delad")) {
            if (sender.equalsIgnoreCase("__Joseph")) {
                String[] arrayCommands = message.split(" ");
                //.regAD __Joseph --> [__Joseph]
                if (arrayCommands.length == 2) {
                    delAd(arrayCommands[1]);
                    event.respond("Nick eliminado como ADMIN exitosamente");
                } else {
                    event.respond("To add an Admin, write .regOP + Nickname(Example: .regAD __Joseph)");
                }
            }
        }

        if (message.startsWith(".call")) {
            if (operators.containsKey(channel) && operators.get(channel).contains(sender)) {
                ImmutableSortedSet<User> usuarios = channel.getUsers();
                List<User> users = new ArrayList<>(usuarios);
                StringBuffer usersMessage = new StringBuffer();

                for (int i = 0; i < users.size();) {
                    for (int j = 0; j < 40 && i < users.size(); j++) {
                        usersMessage.append(users.get(i++).getNick()).append(' ');
                    }
                    usersMessage.delete(usersMessage.length() - 1, usersMessage.length());
                    System.out.println(usersMessage.toString());
                    event.respond(usersMessage.toString());
                    usersMessage.delete(0, usersMessage.length());
                }
                int indexOfFirstSpace = message.indexOf(" ");
                String msg = message.substring(indexOfFirstSpace, message.length()), fin;

                fin = " " + Colors.RED + Colors.BOLD + " Says: " + Colors.DARK_GREEN + msg;
                event.respond(fin);
            } else {
                event.respond("Dont have access to the BoT. Contact __Joseph");
            }

        } else if (message.startsWith(".join")) {
            if (admins.contains(sender)) {
                String[] arrayCommands = message.split(" ");
                if (arrayCommands.length == 2) {
                    event.getBot().sendIRC().joinChannel(arrayCommands[1]);
                } else {
                    event.respond("To join use .join CHANNEL_NAME ID(Example: .join #UPA)");
                }
            } else {
                event.respond("Insufficient Access to the BoT");
            }
        } else if (message.startsWith(".part")) {
            if (admins.contains(sender)) {
                String[] arrayCommands = message.split(" ");
                if (arrayCommands.length == 2) {
                    event.getBot().sendRaw().rawLineNow("part " + arrayCommands[1] + " I want out of here");
                } else {
                    event.respond("To part use .part CHANNEL_NAME ID(Example: .part #UPA)");
                }
            } else {
                event.respond("You dont have access to this BoT");
            }
        } else if (message.startsWith(".regid")) {

            String[] arrayCommands = message.split(" ");
            //.regid 1234 --> [sender][1234]
            if (arrayCommands.length == 2) {
                regID(event, sender, arrayCommands[1]);
            } //.regid 1234 --> [sender][Nickname][1234567]
            else if (arrayCommands.length > 2) {
                regID(event, arrayCommands[1], arrayCommands[2]);
            } else {
                event.respond("To register, write .regid + IDClashOfClans(Example: .regid 2740853) or .regid Nickname IDClashOfClans (Example: .regid __Joseph 2740853");
            }
        } else if (message.startsWith(".regop")) {
            if (admins.contains(sender)) {
                String[] arrayCommands = message.split(" ");
                //.regOP __Joseph --> [#channel][__Joseph]
                if (arrayCommands.length == 2) {
                    regOP(channel, arrayCommands[1]);
                    event.respond("Nick anadido como OP exitosamente");
                } else {
                    event.respond("To add an Operator, write .regOP + Nickname (Example: .regOP __Joseph)");
                }
            }
        } else if (message.startsWith(".delop")) {
            if (admins.contains(sender)) {
                String[] arrayCommands = message.split(" ");
                //.regOP __Joseph --> [#channel][__Joseph]
                if (arrayCommands.length == 2) {
                    delOP(channel, arrayCommands[1]);
                    event.respond("Nick eliminado como OP exitosamente");
                } else {
                    event.respond("To add an Operator, write .regOP + Nickname (Example: .regOP __Joseph)");
                }
            }
        } else if (message.startsWith(".infoplayer") || message.startsWith(".infojugador")) {
			String[] arrayCommands = message.split(" ");
            //.infoplayer 34 --> [sender][34]
			
			if (nickIRC.containsKey(sender)) {
                //System.out.println("Nick"+nickIRC.get(sender));
				// Con nickIRC.get(sender) Obtengo el ID del jugador (SIN #)
                //System.out.println("Name: "+userInfo.getName());
                String fin;
                fin = Colors.BOLD + Colors.BLUE + "Id: #" + nickIRC.get(sender) + Colors.BROWN + " Nombre del Jugador: " + clashAPI.getName() + Colors.DARK_BLUE
                        + " Trofeos: " + clashAPI.getTrophies() + Colors.DARK_GRAY + " Nivel de Experiencia: " + clashAPI.getExpLevel();
                event.respond(fin);
                

            } else if (arrayCommands.length >= 2) {
                String nicknme = arrayCommands[1];
                if (nickIRC.get(nicknme) != null) {
                    String fin;
                    fin = Colors.BOLD + Colors.BLUE + "Id: #" + nickIRC.get(arrayCommands[1]) + Colors.BROWN + " Nombre del Jugador: " + clashAPI.getName() + Colors.DARK_BLUE
                        + " Trofeos: " + clashAPI.getTrophies() + Colors.DARK_GRAY + " Nivel de Experiencia: " + clashAPI.getExpLevel();
                    event.respond(fin);
                } else {
                    event.respond("To register, write .regid + IDClashOfClans(Example: .regid 2740853) or .regid Nickname IDClashOfClans (Example: .regid __Joseph 2740853");
                }
            } else {

                event.respond("ERROR 404 NOT FOUND ON THIS SERVER");
            }
		} else if (message.startsWith(".infoclan")) {
			String[] arrayCommands = message.split(" ");
            //.infoclan 49816984 --> [sender][17641698]
			if (arrayCommands.length >= 2) {
                String clan = arrayCommands[1];
                
                    String fin;
					//ARREGLAR ESTA SALIDA
                    fin = Colors.BOLD + Colors.BLUE + "Id: #" + nickIRC.get(sender) + Colors.BROWN + " Nombre del Jugador: " + clashAPI.getName() + Colors.DARK_BLUE
                        + " Trofeos: " + clashAPI.getTrophies() + Colors.DARK_GRAY + " Nivel de Experiencia: " + clashAPI.getExpLevel();
                    event.respond(fin);
                
            } else {

                event.respond("ERROR 404 NOT FOUND ON THIS SERVER");
            }
		} else if (message.startsWith(".guideyt")) {
			event.respond("Ooops! Encontramos un error interno y trabajamos en ello. Trataremos de solucionarlo lo antes posible! (Codigo: 2707E404");
        }

    }

    @Override
    public void onPrivateMessage(PrivateMessageEvent event) throws Exception {
        String message = event.getMessage();
        String sender = event.getUser().getNick();

        if (message.startsWith(".join")) {
            if (admins.contains(sender)) {
                String[] arrayCommands = message.split(" ");
                if (arrayCommands.length == 2) {
                    event.getBot().sendIRC().joinChannel(arrayCommands[1]);
                } else {
                    event.respond("To join use .join CHANNEL_NAME ID(Example: .join #UPA)");
                }
            } else {
                event.respond("Insufficient Access to the BoT");
            }
        } else if (message.startsWith(".part")) {
            if (admins.contains(sender)) {
                String[] arrayCommands = message.split(" ");
                if (arrayCommands.length == 2) {
                    event.getBot().sendRaw().rawLineNow("part " + arrayCommands[1] + " I want out of here");
                } else {
                    event.respond("To part use .part CHANNEL_NAME ID(Example: .part #UPA)");
                }
            } else {
                event.respond("You dont have access to this BoT");
            }
        }
    }

    public void regID(MessageEvent event, String nick, String ide) {
        //NICK VA PRIMERO, ID VA SEGUNDO
        //Si ya existe el nick el id se reemplazara. NO SE PERMITE BORRAR NICKS
        nickIRC.put(nick, ide);
        IrcUser ircUser = IrcUsersDAO.getIrcUserByNickname(nick);
        if (ircUser != null) {
            ircUser.setId(ide);
            IrcUsersDAO.updateIrcUser(ircUser);
            event.respond("ID de usuario actualizado exitosamente");
        } else {
            IrcUsersDAO.saveIrcUser(new IrcUser(ide, nick));
            event.respond("ID registrado exitosamente");
        }

    }

    public void regOP(Channel channel, String nick) {
        IrcUser ircUser = IrcUsersDAO.getIrcUserByNickname(nick);
        if (ircUser != null) {
            cl.curso.java.proyecto.clashnewbie.database.model.Channel channel1 = ChannelDAO.getChannel(channel.getName());
            channel1.addOperator(ircUser);
            ChannelDAO.updateChannel(channel1);
        }

        if (!operators.containsKey(channel)) {
            operators.put(channel, new ArrayList<String>());
        }
        operators.get(channel).add(nick);
    }

    public void delOP(Channel channel, String nick) {
        if (!operators.containsKey(channel)) {
            operators.put(channel, new ArrayList<String>());
        }
        operators.get(channel).remove(nick);
    }

    public void regAd(String nick) {
        /*if (!admins.contains(nick)) {
         admins.add(new ArrayList<String>());
         }*/
        admins.add(nick);
    }

    public void delAd(String nick) {

        admins.remove(nick);
    }
}
