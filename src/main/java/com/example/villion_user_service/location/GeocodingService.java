package com.example.villion_user_service.location;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeocodingService {

    @Autowired
    private Environment env;

    public String getAddressFromCoordinates(double latitude, double longitude) {
        String googleApiKey = env.getProperty("google.api.key"); // 프로퍼티 값 읽기

        System.out.println(googleApiKey);

        String url = String.format(
                "https://maps.googleapis.com/maps/api/geocode/json?latlng=%s,%s&key=%s",
                latitude, longitude, googleApiKey
        );

        RestTemplate restTemplate = new RestTemplate();
        String status = "UNKNOWN";

        try {
            String response = restTemplate.getForObject(url, String.class);

            if (response != null) {
                JSONObject jsonObject = new JSONObject(response);
                status = jsonObject.optString("status", "ZERO_RESULTS");

                if ("OK".equals(status)) {
                    JSONArray resultsArray = jsonObject.optJSONArray("results");
                    if (resultsArray != null && resultsArray.length() > 0) {
                        JSONObject firstResult = resultsArray.optJSONObject(0);
                        return firstResult.optString("formatted_address", "Address not found");
                    }
                } else {
                    // API 상태 코드가 OK가 아닐 때
                    System.err.println("Geocoding API returned status: " + status);
                }
            }
        } catch (Exception e) {
            // 예외 발생 시
            System.err.println("Error occurred while calling Geocoding API: " + e.getMessage());
            e.printStackTrace();
        }

        return "Unable to determine address"; // 위치 정보를 찾을 수 없는 경우
    }
}
