package cz.muni.fi.pv168.project.export;

import cz.muni.fi.pv168.project.business.model.Currency;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author Sabrina Orálková, 525089
 */
public class BatchExporterCurrencyJSON {
    public void exportData(List<Currency> currencies, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            JSONArray currencyArray = new JSONArray();

            for (Currency currency : currencies) {
                JSONObject currencyObject = new JSONObject();

                // Populate currencyObject
                currencyObject.put("name", currency.getName());
                currencyObject.put("symbol", currency.getSymbol());
                currencyObject.put("rate_to_dollar", currency.getNewestRateToDollar());

                currencyArray.put(currencyObject);
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("carrides", currencyArray);

            fileWriter.write(jsonObject.toString(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}