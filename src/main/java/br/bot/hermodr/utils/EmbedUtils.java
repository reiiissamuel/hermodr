package br.bot.hermodr.utils;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class EmbedUtils {

    private static final String BOT_CREDIT = "Bot made by Samu-K vulgo BRAGI";

    private static final String BOT_URL_ICON = "https://cdn.discordapp.com/avatars/461564543672254466/f78b0f0dc2a28c91ae63a54c25344ca3.png?size=128";

    public static EmbedBuilder getEmptyEmbedBuilder(String title, String content) {
        EmbedBuilder meb = new EmbedBuilder();
        meb.setTitle(title);
        meb.setDescription(content);
        meb.setColor(Color.YELLOW);
        meb.setFooter(BOT_CREDIT, BOT_URL_ICON);
        return meb;
    }
}
