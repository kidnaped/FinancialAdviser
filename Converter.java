import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Converter {
    private final HttpClient client;

    Converter() {
        client = HttpClient.newHttpClient();
    }

    public void convert(double rubles, String currencySymbol) {
        double rate = getRate(currencySymbol);
        if (rate != 0) {
            System.out.println("Ваши сбережения: " + rubles * rate + " " + currencySymbol);
        }
    }

    private double getRate(String currencySymbol) {
        double rate = 0;
        URI url = URI.create("https://api.exchangerate.host/latest?base=RUB&symbols=" + currencySymbol);
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        try {
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JsonElement jsonElement = JsonParser.parseString(response.body());
                if(!jsonElement.isJsonObject()) {
                    System.out.println("Ответ от сервера не соответствует ожидаемому.");
                    return rate;
                }
                rate = jsonElement.getAsJsonObject()
                        .getAsJsonObject("rates")
                        .get(currencySymbol)
                        .getAsDouble();
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        return rate;
    }
}
