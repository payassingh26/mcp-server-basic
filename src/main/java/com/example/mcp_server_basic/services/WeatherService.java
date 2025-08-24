package com.example.mcp_server_basic.services;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

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
            .defaultHeader("User-Agent", "WeatherApiClient/1.0 (your@email.com)")
            .build();
  }

  // NOTE: tools should be automatically registered with the MCP Server
  // NOTE: descriptions help the model understand if and when to call this tool
  @Tool(description = "Get the weather forecast for a location using longitude and latitude")
  public String getWeatherForecastByLocation(double latitude, double longitude) {
    return "forecast";
  }
  ;

  @Tool(description = "Get weather alerts for a US State")
  public String getWeatherForecastByProvince(
      @ToolParam(description = "Two-letter US state code") String state) {
    return "US State forecast";
  }
}
