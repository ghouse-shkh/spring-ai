package com.learn.springai.tools;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class DateTimeTool {

    private static final Logger logger = LoggerFactory.getLogger(DateTimeTool.class);


    @Tool(description = "Get the current date and time for current or local timezone of the system", name = "get_current_local_datetime")
    public String getCurrentDateTime() {
        logger.info("Fetching current date and time");
        return LocalDateTime.now().toString();
    }

    @Tool(name ="get_timezone", description = "Get the system timezone")
    public String getSystemTimeZone() {
        logger.info("Fetching system timezone");
        return ZoneId.systemDefault().toString();
    }

    @Tool(name = "get_datetime_in_timezone", description = "Get the current date and time in the specified timezone")
    public String getDateTimeInTimeZone(@ToolParam(description = "The timezone to get the current date and time for") String timezone) {
        logger.info("Fetching date and time for timezone: {}", timezone);
        return LocalDateTime.now(ZoneId.of(timezone)).toString();
    }
}