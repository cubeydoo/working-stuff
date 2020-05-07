package games;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RussianRoulette extends AbstractGame {
    public static Random random = new Random();

    public int[] clip = new int[6];
    private int currPlayerIndex = 0;
    private int clipIndex = -1;
    private boolean[] forced;
    public boolean gameOver = false;
    private ArrayList<User> _players;

    public RussianRoulette(ArrayList<User> players, GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("Use !shoot followed by self, sky, or @someone.").queue();
        _players = players;
        newRound(event);
    }

    private void newRound(GuildMessageReceivedEvent event) {
        currPlayerIndex = 0;
        clip[random.nextInt(6)] = 1;
        forced = new boolean[_players.size()];
        Collections.shuffle(_players);
        event.getChannel().sendMessage(_players.size() + " players remain.").queue();
        event.getChannel().sendMessage(_players.get(currPlayerIndex).getName() + " it is your turn!").queue();
    }


    @Override
    public void execute(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        User user = null;
        try {
            Member member = event.getMessage().getMentionedMembers().get(0);
            user = member.getUser();
        } catch (NullPointerException | IndexOutOfBoundsException e)  {
        }
        if (args[0].equals("!shoot") && event.getAuthor().equals(_players.get(currPlayerIndex))) {
            if (user != null && !forced[currPlayerIndex]) {
                if (nextShotVal() == 1) {
                    kill(event, user);
                } else {
                    event.getChannel().sendMessage("Shot was a whiff. You must shoot yourself next.").queue();
                    forced[currPlayerIndex] = true;
                    advance(event.getChannel(), event.getAuthor());
                }
            }
            switch (args[1]) {
                case "self":
                    forced[currPlayerIndex] = false;
                    if (nextShotVal() == 1) {
                        kill(event, event.getAuthor());
                    } else {
                        advance(event.getChannel(), event.getAuthor());
                    }
                    break;
                case "air":
                    if (!forced[currPlayerIndex] && nextShotVal() == 1) {
                        declareWinner(event.getAuthor(), event);
                    } else {
                        kill(event, event.getAuthor());
                    }
                    break;
            }
        }
    }

    private void declareWinner(User winner, GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage(winner.getName() + " has won the game!").queue();
        gameOver = true;
    }

    private void kill (GuildMessageReceivedEvent event, User user) {
        event.getChannel().sendMessage(":skull: " + user.getName()
                + " has died. ").queue();
        _players.remove(user);
        if (_players.size() == 1) {
            declareWinner(_players.get(0), event);
        } else {
            newRound(event);
        }
    }

    private void advance(TextChannel channel, User user) {
        channel.sendMessage(user.getName() + " is safe! "
        + "now it is " + _players.get(nextPlayerIndex()).getName() + "'s turn!").queue();
    }

    private int nextPlayerIndex() {
        currPlayerIndex += 1;
        if (currPlayerIndex == _players.size()) {
            currPlayerIndex = 0;
        }
        return currPlayerIndex;
    }

    private int nextShotVal() {
        clipIndex += 1;
        if (clipIndex == 6) {
            clipIndex = 0;
        }
        return clip[clipIndex];
    }
}
