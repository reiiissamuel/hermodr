package br.bot.hermodr.Repository;

import br.bot.hermodr.Config.SheetServiceConnection;
import br.bot.hermodr.Dtos.EventoCadastro;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ClearValuesResponse;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventoCadastroSheetRepository extends SheetServiceConnection {

    private static String TABLE_RANGE = "!A2:E";

    private static String ROWS_START_INDEX = "!A";

    public EventoCadastro getEventoCadastro(String playerDiscord, String eventName) throws IOException, GeneralSecurityException {
        final Sheets sheetsService = getSheetsService();
        EventoCadastro eventoCadastro = new EventoCadastro();

        ValueRange response = sheetsService.spreadsheets().values()
                .get(getSpreedsheetId(), eventName + TABLE_RANGE)
                .execute();

        List<List<Object>> rows = response.getValues() != null ? response.getValues() : new ArrayList<>();

        if (rows.isEmpty()){
            eventoCadastro.setRowNumber(String.valueOf(2));
            return eventoCadastro;
        } else {
            for (int i = 0; i < rows.size(); i++){

                if(rows.get(i).get(0).toString().equals(playerDiscord)){
                    eventoCadastro.setDiscord(rows.get(i).get(0).toString());
                    eventoCadastro.setNickname(rows.get(i).get(1).toString());
                    eventoCadastro.setCla(rows.get(i).get(2).toString());
                    eventoCadastro.setDtInscricaoFromString(rows.get(i).get(3).toString());
                    eventoCadastro.setRowNumber(String.valueOf(i + 2));

                    return eventoCadastro;

                }else if(i == rows.size() - 1){
                    eventoCadastro.setRowNumber(String.valueOf(rows.size() + 2));
                }
            }
        }

        return eventoCadastro;

    }

    public EventoCadastro updateRow(EventoCadastro eventoCadastro, String eventName) throws IOException, GeneralSecurityException {

        final Sheets sheetsService = getSheetsService();

        ValueRange body = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList(
                                eventoCadastro.getDiscord(),
                                eventoCadastro.getNickname(),
                                eventoCadastro.getCla(),
                                eventoCadastro.getDtInscricao().toString()
                        )
                ));

        String playerRegisterRowRange = eventName + ROWS_START_INDEX + eventoCadastro.getRowNumber();
        UpdateValuesResponse response = sheetsService.spreadsheets().values()
                .update(getSpreedsheetId(),playerRegisterRowRange ,body)
                .setValueInputOption("RAW")
                .execute();

        return eventoCadastro;
    }

    public void cleanRaw(String eventName) throws GeneralSecurityException, IOException {

        final Sheets sheetsService = getSheetsService();

        ClearValuesResponse response = sheetsService.spreadsheets().values()
                .clear(getSpreedsheetId(), eventName + TABLE_RANGE, null)
                .execute();

    }

    public List<EventoCadastro> getAllByEventName(String eventName) throws GeneralSecurityException, IOException {
        List<EventoCadastro> cadastros = new ArrayList<>();
        final Sheets sheetsService = getSheetsService();

        ValueRange response = sheetsService.spreadsheets().values()
                .get(getSpreedsheetId(), eventName + TABLE_RANGE)
                .execute();

        List<List<Object>> rows = response.getValues() != null ? response.getValues() : new ArrayList<>();

        if (rows.isEmpty()){
            return cadastros ;
        } else {
            for (int i = 0; i < rows.size(); i++){
                EventoCadastro eventoCadastro = new EventoCadastro();
                eventoCadastro.setDiscord(rows.get(i).get(0).toString());
                eventoCadastro.setNickname(rows.get(i).get(1).toString());
                eventoCadastro.setCla(rows.get(i).get(2).toString());
                eventoCadastro.setDtInscricaoFromString(rows.get(i).get(3).toString());
                eventoCadastro.setRowNumber(String.valueOf(i + 2));
                cadastros.add(eventoCadastro);
            }
        }

        return cadastros;

    }
}
