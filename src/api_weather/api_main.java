package api_weather;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class api_main {
    public static void main(String[] args) {
        String apiUrl = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";
        JSONObject weatherData = getWeatherData(apiUrl);
        //if (weatherData == null) {
          //  System.out.println("Failed to fetch data");
           // return;
        //}
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Get Temperature");
            System.out.println("2. Get Wind Speed");
            System.out.println("3. Get Pressure");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter the date&time (YYYY-MM-DD HH:MM:SS): ");
                    String dateTime1 = scanner.nextLine();
                    Double temperature = getTemperature(weatherData, dateTime1);
                    if (temperature != null) {
                        System.out.println("Temperature at " + dateTime1 + ": " + temperature + "°C");
                    } else {
                        System.out.println("Data not found");
                    }
                    break;

                case "2":
                    System.out.print("Enter the date&time (YYYY-MM-DD HH:MM:SS): ");
                    String dateTime2 = scanner.nextLine();
                    Double windSpeed = getWindSpeed(weatherData, dateTime2);
                    if (windSpeed != null) {
                        System.out.println("Wind Speed at " + dateTime2 + ": " + windSpeed + " m/s");
                    } else {
                        System.out.println("Data not found");
                    }
                    break;

                case "3":
                    System.out.print("Enter the date&time (YYYY-MM-DD HH:MM:SS): ");
                    String dateTime3 = scanner.nextLine();
                    Double pressure = getPressure(weatherData, dateTime3);
                    if (pressure != null) {
                        System.out.println("Pressure at " + dateTime3 + ": " + pressure + " hPa");
                    } else {
                        System.out.println("Data not found");
                    }
                    break;

                case "0":
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

    private static JSONObject getWeatherData(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream inputStream = connection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            StringBuilder response = new StringBuilder();

            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }

            scanner.close();
            connection.disconnect();

            return new JSONObject(response.toString());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Double getTemperature(JSONObject data, String dateTime) {
        JSONArray list = data.getJSONArray("list");

        for (int i = 0; i < list.length(); i++) {
            JSONObject item = list.getJSONObject(i);
            if (item.getString("dt_txt").equals(dateTime)) {
                JSONObject main = item.getJSONObject("main");
                return main.getDouble("temp");
            }
        }
        return null;
    }

    private static Double getWindSpeed(JSONObject data, String dateTime) {
        JSONArray list = data.getJSONArray("list");

        for (int i = 0; i < list.length(); i++) {
            JSONObject item = list.getJSONObject(i);
            if (item.getString("dt_txt").equals(dateTime)) {
                JSONObject wind = item.getJSONObject("wind");
                return wind.getDouble("speed");
            }
        }
        return null;
    }

    private static Double getPressure(JSONObject data, String dateTime) {
        JSONArray list = data.getJSONArray("list");

        for (int i = 0; i < list.length(); i++) {
            JSONObject item = list.getJSONObject(i);
            if (item.getString("dt_txt").equals(dateTime)) {
                JSONObject main = item.getJSONObject("main");
                return main.getDouble("pressure");
            }
        }
        return null;
    }
}