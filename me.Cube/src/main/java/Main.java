import events.testEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Main {
    public static void main(String[] args) throws Exception {

        JDA jda = new JDABuilder("NzA3NzM5MDExOTAwNTA2MTg1.XrNL7w.yGg8OjMaE3dh8FSdOvpGmBqy5G0").build();
        jda.addEventListener(new testEvent());

    }
}
