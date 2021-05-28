package br.bot.hermodr.enums;

public enum EventCommand {

    //adm commands
    ADD(5, "**Criação de Evento**"),
    DELETE(3, "**Exclusão de Evento**"),
    SET_OPEN(3, "**Inscrições Abertas**"),
    SET_CLOSED(3, "**Inscrições Fechadas**"),
    LIST_EVENT_PLAYERS(3, "**Jogadores Inscritos no Evento**"),
    CLEAN(3, "**Limpar Inscrições**"),

    //players commands
    APPLY(4, "**Inscrição para Evento**"),
    LIST_EVENTS( 2, "**Lista de eventos com inscrições abertas**"),

    HELP(2, "**Ajuda**");

    public  int qtdArguments;
    public  String resultTitle;

    EventCommand(int qtdArguments, String resultTitle) {
        this.qtdArguments = qtdArguments;
        this.resultTitle = resultTitle;
    }
}
