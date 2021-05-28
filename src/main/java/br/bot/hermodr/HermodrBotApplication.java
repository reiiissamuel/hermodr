package br.bot.hermodr;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;


public class HermodrBotApplication {

	public static JDA jda;

	public static void main(String[] args) throws LoginException {
		jda = JDABuilder.create(EnumSet.allOf(GatewayIntent.class))
				.setToken("ODQ2OTI0MzU3NDcyMjIzMjUz.YK2lxQ.b1-nyT_TAj52q-UcTAIIh06stqM")
				.build();
		jda.getPresence().setStatus(OnlineStatus.ONLINE);
		jda.getPresence().setActivity(Activity.listening("My boss BRAGI, Vulgo Samu-K"));
		jda.addEventListener(new ChatsListener());
	}

}
