package com.ludowica.waterqualityanalysisapi.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ludowica.waterqualityanalysisapi.models.Location;
import com.ludowica.waterqualityanalysisapi.repository.LocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class LocationService {

    String baseURL = "https://api.opencagedata.com/geocode/v1/json?q=";
    String apiKey = "&key=c6cba34aa4e34d51bd425f2f1fcbe184";
    String optParams = "&countrycode=LK";


    @Autowired
    LocationRepo locationRepo;

    @Autowired
    ArcGISAuthService arcGISAuthService;

    @Value("${esri.arcgis.org-id}")
    private String orgId;

    @Value("${esri.arcgis.feature-map}")
    private String mapName;

    private String accessToken = null;

    public Location addOrUpdate(Location location) {
        Location savedLocation =  locationRepo.save(location);
        prepareForArcGIS(savedLocation);
        return savedLocation;
    }

    public Location getLatLong(Location location) {

        String url = baseURL + location.getName() + apiKey + optParams;
        RestTemplate restTemplate = new RestTemplate();

        String result = restTemplate.getForObject(url, String.class);

        JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
        JsonArray jsonArray = jsonObject.get("results").getAsJsonArray();

        if (jsonArray.size() != 0) {

            JsonObject latlong = jsonArray.get(0)
                    .getAsJsonObject().get("geometry")
                    .getAsJsonObject();

            location.setLatitude(latlong.get("lat").getAsDouble());
            location.setLongitude(latlong.get("lng").getAsDouble());
        }


        return location;
    }

    private void prepareForArcGIS(Location location){
        accessToken = arcGISAuthService.getAccessToken();
        JsonObject json = prepareLocation(location);
        addToArcGIS(json);
    }

    private void addToArcGIS(JsonObject locationJson) {

        String url = "https://services6.arcgis.com/" + orgId + "/ArcGIS/rest/services/" + mapName + "/FeatureServer/0/applyEdits";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("f", "json");
        params.add("token", accessToken);
        params.add("adds", locationJson.toString());

        HttpEntity requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {

            JsonObject jsonObject = new JsonParser().parse(response.getBody()).getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("addResults").getAsJsonArray();
        }

    }

    private JsonObject prepareLocation(Location location) {

        JsonObject json = new JsonObject();
        JsonObject geometry = new JsonObject();
        JsonObject spatialReference = new JsonObject();
        JsonObject attributes = new JsonObject();

        geometry.addProperty("x", location.getLatitude());
        geometry.addProperty("y", location.getLongitude());

        spatialReference.addProperty("wkid", 4326);

        attributes.addProperty("name", location.getName());
        attributes.addProperty("id", location.getId());
        attributes.addProperty("city", location.getCity());
        attributes.addProperty("longitude", location.getLongitude());
        attributes.addProperty("latitude", location.getLatitude());

        geometry.add("spatialReference", spatialReference);
        json.add("geometry", geometry);
        json.add("attributes", attributes);

        return json;
    }

    public void getWaterQualityByLocation(List<Location> locationList){

    }


}
