package br.bot.hermodr.managers;

import br.bot.hermodr.Config.Messages;
import br.bot.hermodr.utils.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class AnnoucementsManager {

    public void verifyWhichCommand(GuildMessageReceivedEvent event) {
        EmbedBuilder meb;
        String arguments[] = event.getMessage().getContentRaw().split("\n", 3);

        if (arguments.length != 3)
            meb = EmbedUtils.getEmptyEmbedBuilder(Messages.WRONG_EVENT_COMMAND, Messages.WRONG_SINTAXE);

        String[] targetChannels = arguments[1].split(" ");
        String annoucement = arguments[2];
        List<TextChannel> channels = event.getGuild().getTextChannels();

        for(int i = 0; i < targetChannels.length; i++){
            for (int j = 0; j < channels.size(); j++){
                if(targetChannels[i].equals(channels.get(j).getAsMention())){
                    TextChannel textChannel = channels.get(j);
                    meb = EmbedUtils.getEmptyEmbedBuilder("**ANÚNCIO**", annoucement);
                    textChannel.sendMessage(meb.build()).submit();
                }
            }
        }

        event.getMessage().delete().complete();
    }

}
