package com.example.mcp_server_basic.services;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.geojson.Feature;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.Map;

@Service // NOTE: auto register this bean in the application context
public class WeatherService {

  private final RestClient restClient;

  public WeatherService() {
    this.restClient =
        RestClient.builder()
            .baseUrl("https://api.weather.gov")
            .defaultHeader(
                "Accept",
                "application/geo+json") // GeoJson: JSON based format specific for geographical data
            .defaultHeader("User-Agent", "WeatherAPIClient-MCPServerBasics (payassingh18@gmail.com)")
            .build();
    // NOTE: following line is just for debugging
    getWeatherForecastByLocation(39, -104);
  }

  // NOTE: tools should be automatically registered with the MCP Server
  // NOTE: descriptions help the model understand if and when to call this tool
  @Tool(description = "Get the weather forecast for a location using longitude and latitude")
  public String getWeatherForecastByLocation(double latitude, double longitude) {
     // Call the api
     Map<String, Object> latLongApiResponse = restClient.get()
            .uri("/points/{latitude},{longitude}", latitude, longitude)
            .retrieve()
            .body(Map.class);

     // Convert to GeoJSON type object and extract url for forecast
    Feature feature = new ObjectMapper().convertValue(latLongApiResponse, Feature.class);
    String forecastUrl = feature.getProperties().get("forecast").toString();

    // Call the forecast url
    Map<String, Object> forecastResponse =  restClient.get()
            .uri(forecastUrl)
            .retrieve()
            .body(Map.class);

    forecastResponse.get("properties");

    return "forecast";
  }


  @Tool(description = "Get weather alerts for a US State")
  public String getWeatherForecastByProvince(
      @ToolParam(description = "Two-letter US state code") String state) {
    return "US State forecast";
  }
}
