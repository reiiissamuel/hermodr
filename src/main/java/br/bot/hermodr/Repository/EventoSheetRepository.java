package br.bot.hermodr.Repository;

import br.bot.hermodr.Config.SheetServiceConnection;
import br.bot.hermodr.Dtos.Evento;
import br.bot.hermodr.Dtos.EventoCadastro;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventoSheetRepository extends SheetServiceConnection {

    private static String TABLE_RANGE = "events!A2:E";

    private static String ROWS_START_INDEX = "events!A";

    public static int QTD_EVENTS = 1;

    public Evento getByName(String eventName) throws IOException, GeneralSecurityException {
        final Sheets sheetsService = getSheetsService();
        Evento evento = new Evento();

        ValueRange response = sheetsService.spreadsheets().values()
                .get(getSpreedsheetId(), TABLE_RANGE)
                .execute();

        List<List<Object>> rows = response.getValues() != null ? response.getValues() : new ArrayList<>();
        QTD_EVENTS = rows.size();

        if (rows.isEmpty()){
            evento.setRowNumber(String.valueOf(2));
            return evento;
        } else {
            for (int i = 0; i < rows.size(); i++){

                if(rows.get(i).get(1).toString().equals(eventName)){
                    evento.setId(rows.get(i).get(0).toString());
                    evento.setNome(rows.get(i).get(1).toString());
                    evento.setStatus(rows.get(i).get(2).toString());
                    evento.setPlayerLimit(Integer.valueOf(rows.get(i).get(3).toString()));
                    evento.setDtAtualzFromString(rows.get(i).get(4).toString());
                    evento.setRowNumber(String.valueOf(i + 2));

                    return evento;

                }else if(i == rows.size() - 1){
                    evento.setRowNumber(String.valueOf(rows.size() + 2));
                }
            }
        }

        return evento;

    }

    public Evento updateRow(Evento evento) throws IOException, GeneralSecurityException {

        final Sheets sheetsService = getSheetsService();

        ValueRange body = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList(
                                evento.getId(),
                                evento.getNome(),
                                evento.getStatus(),
                                evento.getPlayerLimit().toString(),
                                evento.getDtAtualz().toString()
                        )
                ));

        String playerRegisterRowRange = ROWS_START_INDEX + evento.getRowNumber();
        UpdateValuesResponse response = sheetsService.spreadsheets().values()
                .update(getSpreedsheetId(),playerRegisterRowRange ,body)
                .setValueInputOption("RAW")
                .execute();

        return evento;
    }

    public List<Evento> getAll() throws GeneralSecurityException, IOException {
        List<Evento> eventos = new ArrayList<>();
        final Sheets sheetsService = getSheetsService();

        ValueRange response = sheetsService.spreadsheets().values()
                .get(getSpreedsheetId(), TABLE_RANGE)
                .execute();

        List<List<Object>> rows = response.getValues() != null ? response.getValues() : new ArrayList<>();

        if (rows.isEmpty()){
            return eventos;
        } else {
            for (int i = 0; i < rows.size(); i++){
                Evento evento = new Evento();
                evento.setId(rows.get(i).get(0).toString());
                evento.setNome(rows.get(i).get(1).toString());
                evento.setStatus(rows.get(i).get(2).toString());
                evento.setPlayerLimit(Integer.valueOf(rows.get(i).get(3).toString()));
                evento.setDtAtualzFromString(rows.get(i).get(4).toString());
                evento.setRowNumber(String.valueOf(i + 2));
                eventos.add(evento);
            }
        }

        return eventos;

    }

    /*public void createNewEventSheet(String eventName) throws GeneralSecurityException, IOException {
        final Sheets sheetsService = getSheetsService();

        Spreadsheet spreadsheet = new Spreadsheet()
                .setProperties(new SpreadsheetProperties()
                        .setTitle(eventName));

        sheetsService.spreadsheets().create(spreadsheet)
                .setFields(getSpreedsheetId())
                .execute();

    }*/

}
