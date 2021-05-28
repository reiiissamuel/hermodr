package br.bot.hermodr.Config;

public class Messages {

    public static final String NO_COMMAND_TITLE = "❓";
    public static final String NO_COMMAND = "Formule teu pedido de forma a ser entendido por mim, Mortal!" +
            "se tem dúvidas de como proceder, digite *!hermodr help* e vejas o que podes pedir e como deves faze-lô.";

    public static final String COMMANDS_HELP = "**Comandos para membros**\n\n" +
            "Cadastrar-se em um evento:\n*!hermodr apply <nome-do-evento-separado-por-traço-em-caso-de-duas-palavras> <nome-clã>*\n" +
            "sem fizer parte de um clã escreva *sem-vila* no comando.\n\n" +
            "Listar eventos com inscrições abertas:\n*!hermodr list_events*\n\n" +
            "Detalhes de todos os eventos podem ser encontrados no canal" + AppProperties.eventsChannelName;

    public static final String ADM_COMMANDS_HELP = "**Comandos para ADMs**\n\n" +
            "Cadastrar um novo evento:\n*!hermodr add <nome-do-evento> <limite-participantes> <status-do-event(open ou closed)>*\n\n" +
            "Excluír um evento:\n*!hermodr delete <nome-do-evento>*\n\n" +
            "Limpar cadastros de um evento:\n*!hermodr clean <nome-do-evento>*\n\n" +
            "Abrir ou fechar inscrições para um evento:\n*!hermodr* set_<open/closed> <nome-do-evento>\n\n" +
            "Listar players cadastrados em um evento:\n*!hermodr list_event_players <nome-do-evento>*";

    public static final String MISSING_ARGUMENT = "A operação não pode ser conclupida pois está faltando argumento(s) no comando." +
            "\n digite **help** para ver como aplicar o comando que desejas.";
    public static final String WRONG_SINTAXE = "A sintaxe do comando não parece estar correta.\nDigite **!hermodr help** para verificar os comandos.";

    public static String ADD_EVENT_NOT_POSSIBLE = "Não foi possível cadastrar este evento!" +
            "Verifique se você informou o limite de participante e setou o status do evento como open ou closed." +
            "\nDigite **!hermor help** para verificar os comandos.";

    public static String OPERATION_SUCCESS = "A operação foi concluída com sucesso!";

    public static String NO_EVENT_APPLIES = "Ainda não inscrições para este evento!";
    public static String NO_OPENNED_EVENT = "Ainda não há eventos abertos!";

    //exception
    public static final String ROLE_NOT_FOUND = "Tentativa de opeação com uma Role não existente.\nCertifique-se de ter criado a role." +
            "\nnome da role: ";

    public static final String EVENT_NOT_FOUND = "Não existe nenhum evento com este nome.\n" +
            "Certifique-se de escrever o nome correto e sobstituir os espaços por -\n" +
            "Exemplo: last viking -> last-viking.";

    public static String WRONG_EVENT_COMMAND = "Não consegui entender o que queres!\n" +
            "digite \"**help**\" para ver o que podes me pedir por aqui.";

    public static String APP_ERROR = "**A operação não pode ser concluída por conta de um erro interno**.\n\nException:\n";

    //emojis
    public static final String DOUBT_EMOJI = "❓";
    public static final String WARNNING_EMOJI = "⚠";

}
