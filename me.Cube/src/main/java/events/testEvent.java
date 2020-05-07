package events;

import games.AbstractGame;
import games.RussianRoulette;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class testEvent extends ListenerAdapter {

    private boolean gameInProgress = false;
    private static HashMap<String, AbstractGame> gameMap = new HashMap<>();
    private static String game = "";
    private static ArrayList<User> playerList = new ArrayList<>();

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] messageSent = event.getMessage().getContentRaw().split(" ");
        if (!event.getAuthor().isBot()) {
            if (gameInProgress) {
                gameMap.get(game).execute(event);
            }
            if (messageSent[0].equals("!start")) {
                if (gameInProgress) {
                    event.getChannel().sendMessage("Game in progress.").queue();
                } else {
                    gameInProgress = true;
                    switch (messageSent[1]) {
                        case "rr":
                            RussianRoulette rr = new RussianRoulette(playerList, event);
                            game = "rr";
                            gameMap.put("rr", rr);
                            break;
                        default:
                            gameInProgress = false;
                            event.getChannel().sendMessage("I do not understand.").queue();
                    }
                }
            } else {
                switch (messageSent[0]) {
                    case "!join":
                        playerList.add(event.getAuthor());
                        event.getChannel().sendMessage(event.getAuthor().getName() + " has joined!").queue();;
                }
            }
        }
    }

}
