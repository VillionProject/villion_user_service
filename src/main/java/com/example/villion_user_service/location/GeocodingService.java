package com.example.villion_user_service.location;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
//@Autowired
//private Environment env;


@Service
public class GeocodingService {

    @Autowired
    private Environment env;

    public String getAddressFromCoordinates(double latitude, double longitude) {
        String googleApiKey = env.getProperty("google.api.key");
        String url = String.format(
                "https://maps.googleapis.com/maps/api/geocode/json?latlng=%s,%s&key=%s&language=ko",
                latitude, longitude, googleApiKey
        );

        RestTemplate restTemplate = new RestTemplate();

        try {
            String response = restTemplate.getForObject(url, String.class);

            if (response != null) {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.optString("status", "ZERO_RESULTS");

                if ("OK".equals(status)) {
                    JSONArray resultsArray = jsonObject.optJSONArray("results");
                    if (resultsArray != null && resultsArray.length() > 0) {
                        JSONObject firstResult = resultsArray.getJSONObject(0);
                        JSONArray addressComponents = firstResult.getJSONArray("address_components");

                        String dong = null;
                        String gu = null;
                        String city = null;

                        // 각 address_component의 types 분석
                        for (int i = 0; i < addressComponents.length(); i++) {
                            JSONObject component = addressComponents.getJSONObject(i);
                            JSONArray types = component.getJSONArray("types");

                            // 구(administrative_area_level_2) 정보 추출
                            if (types.toString().contains("administrative_area_level_2")) {
                                gu = component.getString("long_name");
                            }

                            // 동(sublocality_level_2 또는 sublocality_level_1) 정보 추출
                            if (types.toString().contains("sublocality_level_2")) {
                                dong = component.getString("long_name");
                            } else if (types.toString().contains("sublocality_level_1")) {
                                // sublocality_level_1이 동 정보인 경우
                                if (dong == null) { // 동 정보가 없는 경우에만 저장
                                    dong = component.getString("long_name");
                                }
                            }

                            // 시(locality) 정보 추출
                            if (types.toString().contains("locality")) {
                                city = component.getString("long_name");
                            }
                        }

                        // 시, 구, 동 정보를 출력
                        if (city != null) {
                            System.out.println("시 정보: " + city); // 예: 수원시
                        }
                        if (gu != null) {
                            System.out.println("구 정보: " + gu); // 예: 팔달구
                        }
                        if (dong != null) {
                            System.out.println("동 정보: " + dong); // 예: 인계동
                            return dong; // 동 정보가 있으면 반환
                        } else {
                            return "동 정보를 찾을 수 없습니다"; // 동 정보를 찾지 못한 경우
                        }
                    }
                } else {
                    System.out.println("Geocoding API returned status: " + status);
                }
            }
        } catch (Exception e) {
            System.out.println("Error occurred while calling Geocoding API: " + e.getMessage());
            e.printStackTrace();
        }

        return "동 정보를 찾을 수 없습니다"; // 예외 상황에서 동 정보를 찾을 수 없는 경우
    }
}
