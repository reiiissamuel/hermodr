package br.bot.hermodr;

import br.bot.hermodr.Config.AppProperties;
import br.bot.hermodr.managers.AnnoucementsManager;
import br.bot.hermodr.managers.EventsManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ChatsListener extends ListenerAdapter {

    private AnnoucementsManager annoucementsManager = new AnnoucementsManager();
    private EventsManager eventsManager = new EventsManager();

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String command = event.getMessage().getContentRaw();

        if(!command.startsWith(AppProperties.MAIN_COMMAND) && !command.startsWith(AppProperties.ANNOUNCE_COMMAND))
            return;

        if(command.startsWith(AppProperties.ANNOUNCE_COMMAND))
            annoucementsManager.verifyWhichCommand(event);
        else
            eventsManager.verifyWhichCommand(event);

    }

}
