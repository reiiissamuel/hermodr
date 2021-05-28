package br.bot.hermodr.Dtos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EventoCadastro {

    private String discord;
    private String nickname;
    private String cla;
    private LocalDate dtInscricao;
    //line position on googleSheets
    private String rowNumber;


    public String getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(String rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getDiscord() {
        return discord;
    }

    public void setDiscord(String discord) {
        this.discord = discord;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCla() {
        return cla;
    }

    public void setCla(String cla) {
        this.cla = cla;
    }

    public LocalDate getDtInscricao() {
        return dtInscricao;
    }

    public void setDtInscricao(LocalDate dtInscricao) {
        this.dtInscricao = dtInscricao;
    }

    public void setDtInscricaoFromString(String dtInscricao) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dtInscricao, formatter);

        this.dtInscricao = date;
    }
}
