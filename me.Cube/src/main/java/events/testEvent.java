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
            if (gameMap.get(game) != null && gameMap.get(game).gameOver) {
                gameInProgress = false;
            } else if (gameInProgress) {
                gameMap.get(game).execute(event);
            } else if (messageSent[0].equals("!start")) {
                if (gameInProgress) {
                    event.getChannel().sendMessage("Game in progress.").queue();
                } else if (playerList.size() == 0) {
                    event.getChannel().sendMessage("Nobody is here to play!").queue();
                } else {
                    gameInProgress = true;
                    // Switch case for starting a certain game
                    switch (messageSent[1]) {
                        case "rr":
                            game = "rr";
                            RussianRoulette rr = new RussianRoulette(playerList, event);
                            gameMap.put("rr", rr);
                            break;
                        default:
                            gameInProgress = false;
                            event.getChannel().sendMessage("I do not understand.").queue();
                    }
                }
            } else {
                // handles the rest of commands that aren't tied to a game start
                switch (messageSent[0]) {
                    case "!join":
                        playerList.add(event.getAuthor());
                        event.getChannel().sendMessage(event.getAuthor().getName() + " has joined!").queue();;
                }
            }
        }
    }

}
