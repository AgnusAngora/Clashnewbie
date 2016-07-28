package cl.curso.java.clashnewbie.dao;

public class TestChannel {

    public static void main(String[] args) {
        try {

            // Creating Contact entity that will be save to the sqlite database
            IrcUser myContact = new IrcUser("3", "My Name");
            IrcUser yourContact = new IrcUser("24", "Your Name");
            IrcUsersDAO.saveIrcUser(myContact);
            IrcUsersDAO.saveIrcUser(yourContact);

            Channel channelGato = new Channel("#chile", "Canal Chileno");
            ChannelDAO.saveChannel(channelGato);
            Channel channelG = ChannelDAO.getChannel("#chile");
            System.out.println("=======================");
            System.out.println("ANDO el CHANNEL: "+channelG.getId());
            System.out.println("=======================");

            Channel channel = new Channel("chile", "Canal Chileno");
            channel.addIrcUser(myContact);
            channel.addIrcUser(yourContact);

            ChannelDAO.saveChannel(channel);

            Channel channelDos = ChannelDAO.getChannel("chile");

            IrcUser newIrcUSer = new IrcUser("333", "csozac");
            IrcUsersDAO.saveIrcUser(newIrcUSer);

            channelDos.addIrcUser(newIrcUSer);

            ChannelDAO.updateChannel(channelDos);

            IrcUser ircUserOperator = IrcUsersDAO.getIrcUser("333");
            channelDos.addOperator(ircUserOperator);
            ChannelDAO.updateChannel(channelDos);

            System.out.println("USERS:");
            for (IrcUser ircUser : channelDos.getIrcUsers()) {
                System.out.println("Id: " + ircUser.getId() + " | Nick:" + ircUser.getNick());
            }

            for (IrcUser ircUser : channelDos.getOperators()) {
                System.out.println("Id: " + ircUser.getId() + " | Nick:" + ircUser.getNick());
            }

            System.out.println("OPERATORS:");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
