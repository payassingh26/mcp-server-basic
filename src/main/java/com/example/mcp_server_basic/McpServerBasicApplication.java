package com.example.mcp_server_basic;

import com.example.mcp_server_basic.services.WeatherService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class McpServerBasicApplication {

  public static void main(String[] args) {
    SpringApplication.run(McpServerBasicApplication.class, args);
  }

  @Bean
  public ToolCallbackProvider weatherTools(WeatherService weatherService) {
    return MethodToolCallbackProvider.builder().toolObjects(weatherService).build();
    // NOTE: MethodToolCallbackProvider allows us to turn methods annotated with @Tool to get
    // exposed as MCP Tools on application startup
  }
}
