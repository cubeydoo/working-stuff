package games;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.Random;

public class RussianRoulette extends AbstractGame {
    public static Random random = new Random();

    public int[] clip = new int[6];

    public RussianRoulette(ArrayList<User> players, GuildMessageReceivedEvent event) {
        clip[random.nextInt(6)] = 1;
        event.getChannel().sendMessage("Starting").queue();
    }


    @Override
    public void execute(GuildMessageReceivedEvent event) {
        System.out.println("BITCH");
    }
}
