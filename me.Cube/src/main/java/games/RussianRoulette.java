package games;

import events.testEvent;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RussianRoulette extends AbstractGame {
    public static Random random = new Random();

    public int[] clip = new int[6];
    private int currPlayerIndex = 0;
    private int clipIndex = -1;
    private ArrayList<Boolean> forced = new ArrayList<>();
    public boolean gameOver = false;
    private ArrayList<User> _players;
    private ArrayList<User> _playersAtStart = new ArrayList<>();

    public RussianRoulette(ArrayList<User> players, GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("Use !shoot followed by self, sky, or @someone.").queue();
        _players = players;
        Collections.shuffle(_players);
        _playersAtStart.addAll(_players);
        newRound(event);
        for (int i = 0; i < _players.size(); i++) {
            forced.add(false);
        }
    }

    private void newRound(GuildMessageReceivedEvent event) {
        clip = new int[6];
        clip[random.nextInt(6)] = 1;
        event.getChannel().sendMessage(_players.size() + " players remain.\n"
        + _players.get(currPlayerIndex).getName() + " it is your turn!").queue();
    }


    @Override
    public void execute(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        User user = null;
        if (args[0].equals("!reset")) {
            reset(event);
        } else if (args[0].equals("!players")) {
            String playerList = "";
            for (User username : _players) {
                playerList += username.getName() + ", ";
            }
            playerList = playerList.substring(0, playerList.length()-2);
            event.getChannel().sendMessage(playerList).queue();
        } else if (args[0].equals("!quit")) {
            _players = _playersAtStart;
            testEvent.gameInProgress = false;
            testEvent.game = "";
            testEvent.gameMap.remove(this);
            event.getChannel().sendMessage("Quitting Russian Roulette.").queue();
        }
        try {
            Member member = event.getMessage().getMentionedMembers().get(0);
            user = member.getUser();
        } catch (NullPointerException | IndexOutOfBoundsException e)  {
        }
        if (args[0].equals("!shoot") && event.getAuthor().equals(_players.get(currPlayerIndex))) {
            if (user != null && !forced.get(currPlayerIndex)) {
                if (nextShotVal() == 1) {
                    event.getChannel().sendMessage(":fire: BANG! :fire:").queue();
                    kill(event, user);
                } else {
                    event.getChannel().sendMessage("Shot was a whiff. You must shoot yourself next.").queue();
                    forced.set(currPlayerIndex, false);
                    advance(event.getChannel(), event.getAuthor());
                }
            }
            switch (args[1]) {
                case "self":
                    forced.set(currPlayerIndex, false);
                    if (nextShotVal() == 1) {
                        event.getChannel().sendMessage(":fire: BANG! :fire:").queue();
                        kill(event, event.getAuthor());
                    } else {
                        advance(event.getChannel(), event.getAuthor());
                    }
                    break;
                case "sky":
                    if (!forced.get(currPlayerIndex) && nextShotVal() == 1) {
                        event.getChannel().sendMessage(":fire: BANG! :fire:").queue();
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

    private void kill(GuildMessageReceivedEvent event, User user) {
        event.getChannel().sendMessage(":skull: " + user.getName()
                + " has died. ").queue();
        int dindex = _players.indexOf(user);
        int oindex = _players.indexOf(event.getAuthor());
        if (oindex >= dindex) {
            currPlayerIndex -= 1;
        }
        _players.remove(user);
        forced.remove(dindex);
        if (_players.size() == 1) {
            declareWinner(_players.get(0), event);
        } else {
            newRound(event);
        }
    }

    private void advance(TextChannel channel, User user) {
        channel.sendMessage(user.getName() + " is safe! "
        + "Now it is " + _players.get(nextPlayerIndex()).getName() + "'s turn!").queue();
    }

    private int nextPlayerIndex() {
        currPlayerIndex += 1;
        if (currPlayerIndex == _players.size()) {
            currPlayerIndex = 0;
        }
        return currPlayerIndex;
    }

    private void reset(GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("Use !shoot followed by self, sky, or @someone.").queue();
        _players.clear();
        _players.addAll(_playersAtStart);
        Collections.shuffle(_players);
        forced.clear();
        currPlayerIndex = 0;
        for (int i = 0; i < _players.size(); i++) {
            forced.add(false);
        }
        newRound(event);
    }

    private int nextShotVal() {
        clipIndex += 1;
        if (clipIndex == 6) {
            clipIndex = 0;
        }
        return clip[clipIndex];
    }
}
