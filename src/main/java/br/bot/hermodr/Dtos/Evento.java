package br.bot.hermodr.Dtos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Evento {

    private String id;
    private String nome;
    private String status;
    private Integer playerLimit;
    private LocalDate dtAtualz;

    //line position on googleSheets
    private String rowNumber;

    public String getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(String rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPlayerLimit() {
        return playerLimit;
    }

    public void setPlayerLimit(Integer playerLimit) {
        this.playerLimit = playerLimit;
    }

    public LocalDate getDtAtualz() {
        return dtAtualz;
    }

    public void setDtAtualz(LocalDate dtCriacao) {
        this.dtAtualz = dtCriacao;
    }

    public void setDtAtualzFromString(String dtCriacao) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dtCriacao, formatter);

        this.dtAtualz = date;

    }

}
