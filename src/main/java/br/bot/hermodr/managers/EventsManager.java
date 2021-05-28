package br.bot.hermodr.managers;

import br.bot.hermodr.Config.AppProperties;
import br.bot.hermodr.Config.Messages;
import br.bot.hermodr.Dtos.Evento;
import br.bot.hermodr.Dtos.EventoCadastro;
import br.bot.hermodr.enums.EventCommand;
import br.bot.hermodr.Repository.EventoCadastroSheetRepository;
import br.bot.hermodr.Repository.EventoSheetRepository;
import br.bot.hermodr.utils.EmbedUtils;
import br.bot.hermodr.utils.ManagementUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.List;

public class EventsManager {

    private EventoCadastroSheetRepository eventoCadastroSheetRepository;
    private EventoSheetRepository eventoSheetRepository;
    private boolean succesfullOperation;

    public EventsManager() {
        eventoCadastroSheetRepository = new EventoCadastroSheetRepository();
        eventoSheetRepository = new EventoSheetRepository();
    }

    public void verifyWhichCommand(GuildMessageReceivedEvent event){
        String words[] = event.getMessage().getContentRaw().split(" ");
        User user = event.getMessage().getAuthor();
        Member member = event.getGuild().getMemberById(user.getId());
        EmbedBuilder meb;

        try{
            EventCommand eventCommand = EventCommand.valueOf(words[1].toUpperCase());

            switch (eventCommand){
                case HELP://tested
                    if(ManagementUtils.hasRoleWithName(member.getRoles(), AppProperties.ADM_ROLE_NAME))
                        meb = EmbedUtils.getEmptyEmbedBuilder(EventCommand.HELP.resultTitle, Messages.ADM_COMMANDS_HELP);
                    else
                        meb = EmbedUtils.getEmptyEmbedBuilder(EventCommand.HELP.resultTitle, Messages.COMMANDS_HELP);
                    break;
                case ADD://tested
                    meb = EmbedUtils.getEmptyEmbedBuilder(EventCommand.ADD.resultTitle , addEvento(words));
                    break;
                case CLEAN:
                    meb = EmbedUtils.getEmptyEmbedBuilder(EventCommand.CLEAN.resultTitle, cleanEvento(words));
                    break;
                case DELETE:
                    //todo implementar remoção de evento
                    meb = EmbedUtils.getEmptyEmbedBuilder("", "Está feature será implementada em breve");
                    break;
                case SET_OPEN://tested
                    meb = EmbedUtils.getEmptyEmbedBuilder(EventCommand.SET_OPEN.resultTitle, setEventoStatus(words, EventCommand.SET_OPEN));
                    break;
                case SET_CLOSED://tested
                    meb = EmbedUtils.getEmptyEmbedBuilder(EventCommand.SET_CLOSED.resultTitle, setEventoStatus(words, EventCommand.SET_CLOSED));
                    break;
                case LIST_EVENT_PLAYERS://tested
                    meb = EmbedUtils.getEmptyEmbedBuilder(EventCommand.LIST_EVENT_PLAYERS.resultTitle, listEventoPlayers(words));
                    break;
                case APPLY: //tested
                    meb = EmbedUtils.getEmptyEmbedBuilder(EventCommand.APPLY.resultTitle, applyToEvent(words, user));
                    break;
                case LIST_EVENTS://tested
                    meb = EmbedUtils.getEmptyEmbedBuilder(EventCommand.LIST_EVENTS.resultTitle, listOpenEvents());
                    break;
                default:
                    meb = EmbedUtils.getEmptyEmbedBuilder(Messages.DOUBT_EMOJI, Messages.WRONG_EVENT_COMMAND);
            }
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            meb = EmbedUtils.getEmptyEmbedBuilder("**Falha**", Messages.APP_ERROR + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            meb = EmbedUtils.getEmptyEmbedBuilder("**Falha**", Messages.APP_ERROR + e.getMessage());
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            meb = EmbedUtils.getEmptyEmbedBuilder("**Falha**", Messages.APP_ERROR + Messages.WRONG_SINTAXE);
        }

        event.getMessage().reply(meb.build()).queue();

        //retrive operation flag to default value
        succesfullOperation = false;
    }

    //add event
    //chat command = !hermodr add-event <nome-do-evento> <limite-participantes> <status-do-event(open ou closed)>
    private String addEvento(String[] commandFields) throws GeneralSecurityException, IOException {
        Evento evento = new Evento();
        if(commandFields.length != EventCommand.ADD.qtdArguments)
            return Messages.MISSING_ARGUMENT;

        String playersLimit = commandFields[3];
        String eventName = commandFields[2];
        String status = commandFields[4];

        if(ManagementUtils.isNotNumber(playersLimit) ||
                (!status.equalsIgnoreCase("open") && !status.equalsIgnoreCase("close")))
            return Messages.ADD_EVENT_NOT_POSSIBLE;

        evento = getEvento(eventName);
        if(evento.getNome() == null){
            evento.setId(String.valueOf(eventoSheetRepository.QTD_EVENTS + 1));
            evento.setNome(eventName);
            evento.setPlayerLimit(Integer.valueOf(playersLimit));
            evento.setStatus(status);

            //create new sheet to register playes in event
            //eventoSheetRepository.createNewEventSheet(eventName);
        }

        updateEvento(evento);
        return Messages.OPERATION_SUCCESS;
    }

    //claen events applyes
    private String cleanEvento(String[] commandFields) throws GeneralSecurityException, IOException {
        String eventName = commandFields.length >= 2  ? commandFields[2] : "-";
        Evento evento = getEvento(eventName);
        if(evento.getNome() == null)
            return Messages.EVENT_NOT_FOUND;

        eventoCadastroSheetRepository.cleanRaw(eventName);
        return Messages.OPERATION_SUCCESS;
    }

    //set event status
    private String setEventoStatus(String[] commandFields, EventCommand eventCommand) throws GeneralSecurityException, IOException {
        String eventName = commandFields.length >= 2  ? commandFields[2] : "-";

        Evento evento = getEvento(eventName);
        if(evento.getNome() == null)
            return Messages.EVENT_NOT_FOUND;

        if(eventCommand == EventCommand.SET_OPEN)
            evento.setStatus("open");
        else if(eventCommand == EventCommand.SET_CLOSED){
            evento.setStatus("close");
        }

        updateEvento(evento);
        return Messages.OPERATION_SUCCESS;
    }

    //list evento players
    private String listEventoPlayers(String[] commandFields) throws GeneralSecurityException, IOException {
        StringBuilder sb = new StringBuilder();
        String eventName = commandFields.length >= 2  ? commandFields[2] : "-";

        Evento evento = getEvento(eventName);
        if(evento.getNome() == null)
            return Messages.EVENT_NOT_FOUND;

        List<EventoCadastro> cadastros = eventoCadastroSheetRepository.getAllByEventName(eventName);

        if(cadastros.isEmpty())
            return Messages.NO_EVENT_APPLIES;

        cadastros.forEach(cadastro -> {
            sb.append("**Jogador:** ").append(cadastro.getDiscord())
                    .append("\n**Clã:** ").append(cadastro.getCla())
                    .append("\n**Data de Inscrição:** ").append(cadastro.getDtInscricao())
                    .append("\n\n");
        });

        return sb.toString();
    }

    //apply to event
    private String applyToEvent(String[] commandFields, User user) throws GeneralSecurityException, IOException {
        String eventName = commandFields.length >= 2  ? commandFields[2] : "-";
        String nickname = user.getName();
        String playerDiscord = user.getAsMention();
        String claName = commandFields.length >= 4 ? commandFields[3] : "-";
        EventoCadastro eventoCadastro = new EventoCadastro();

        Evento evento = getEvento(eventName);
        if(evento.getNome() == null)
            return Messages.EVENT_NOT_FOUND;

        eventoCadastro = eventoCadastroSheetRepository.getEventoCadastro(playerDiscord, eventName);
        if(eventoCadastro.getDiscord() == null){
            eventoCadastro.setDiscord(playerDiscord);
            eventoCadastro.setNickname(nickname);
            eventoCadastro.setCla(claName);
            eventoCadastro.setDtInscricao(LocalDate.now());
            eventoCadastroSheetRepository.updateRow(eventoCadastro, evento.getNome());
        }

        succesfullOperation = true;
        return Messages.OPERATION_SUCCESS;
    }

    //list opem events
    private String listOpenEvents() throws GeneralSecurityException, IOException {
        StringBuilder sb = new StringBuilder();
        List<Evento> eventos = eventoSheetRepository.getAll();

        if(eventos.isEmpty())
            return Messages.NO_OPENNED_EVENT;

        eventos.forEach(evento -> {
            if(evento.getStatus().equalsIgnoreCase("open"))
                sb.append("**Evento:** ").append(evento.getNome())
                        .append("\n**Limite de participantes:** ").append(evento.getPlayerLimit())
                        .append("\n**Data de criação:** ").append(evento.getDtAtualz())
                        .append("\n\n");
        });

        return sb.toString();
    }

    private Evento updateEvento(Evento evento) throws GeneralSecurityException, IOException {
        evento.setDtAtualz(LocalDate.now());

        return eventoSheetRepository.updateRow(evento);
    }

    private Evento getEvento(String eventName) throws GeneralSecurityException, IOException {
        Evento evento = new Evento();
        return evento = eventoSheetRepository.getByName(eventName);
    }

}
