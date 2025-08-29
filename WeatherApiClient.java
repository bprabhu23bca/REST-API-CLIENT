import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;  // You need org.json library (download jar or use Maven/Gradle)

public class WeatherApiClient {
    public static void main(String[] args) {
        try {
            // Step 1: Define the API URL
            String apiUrl = "https://api.open-meteo.com/v1/forecast?latitude=28.61&longitude=77.23&current_weather=true";
            
            // Step 2: Open connection
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            // Step 3: Check response code
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                // Step 4: Read data from API
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Step 5: Parse JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONObject currentWeather = jsonResponse.getJSONObject("current_weather");

                // Step 6: Display structured data
                System.out.println("===== Current Weather Data =====");
                System.out.println("Temperature: " + currentWeather.getDouble("temperature") + "°C");
                System.out.println("Windspeed : " + currentWeather.getDouble("windspeed") + " km/h");
                System.out.println("Wind Direction: " + currentWeather.getDouble("winddirection") + "°");
                System.out.println("Weather Code : " + currentWeather.getInt("weathercode"));
                System.out.println("Time: " + currentWeather.getString("time"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}