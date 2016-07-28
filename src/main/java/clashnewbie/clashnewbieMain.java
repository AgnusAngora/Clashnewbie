package cl.curso.java.proyecto.clashnewbie.clash_newbie;

import java.io.IOException;

import org.pircbotx.*;

import com.btilm305.clashapi.BTClashWrapper;
import com.btilm305.clashapi.ClashAPI;
import com.btilm305.clashapi.exception.ClashException;


public class clashnewbieMain {

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws Exception {

        Configuration<PircBotX> config = new Configuration.Builder()
                .setName("ClashNewbie") //Nick of the bot. CHANGE IN YOUR CODE
                .setLogin("Tiksi") //Login part of hostmask, eg name:login@host
                .setNickservPassword("2252639")
                
                .setAutoNickChange(true) //Automatically change nick when the current one is in use
                .setServer("irc.rizon.net", 6667) //The server were connecting to
                .addAutoJoinChannel("#SoporteCoC") //Join #pircbotx channel on connect
                .addListener(new Tiksi())
                .buildConfiguration(); //Create an immutable configuration from this builder
                

        //Create our bot with the configuration
        PircBotX bot = new PircBotX(config);
        //Connect to the server
        bot.startBot();
        
        // DEMONZ INFORMATION:
		/*
		String clanTAG = "#LPUC290V";
		String userTAG = "#LY8GRCVV";
		int intern = 32000006;
		try {
			System.out.println("Probando: " + clashAPI.requestTopClans(intern).size());
			
		} catch (ClashException | IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
        */

        try {
            bot.startBot();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}