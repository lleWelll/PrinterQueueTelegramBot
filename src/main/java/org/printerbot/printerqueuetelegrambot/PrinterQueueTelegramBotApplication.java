package org.printerbot.printerqueuetelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@EnableConfigurationProperties
@SpringBootApplication
public class PrinterQueueTelegramBotApplication {
	public static void main(String[] args)  {
		ConfigurableApplicationContext context = SpringApplication.run(PrinterQueueTelegramBotApplication.class, args);
	}

}
