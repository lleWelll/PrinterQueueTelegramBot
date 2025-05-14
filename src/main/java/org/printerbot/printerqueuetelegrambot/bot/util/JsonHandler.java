package org.printerbot.printerqueuetelegrambot.bot.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonHandler {

	private static final ObjectMapper mapper = new ObjectMapper();

	public static List<String> jsonToList(String json) {
		try{
			return mapper.readValue(json, new TypeReference<>() {});
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String listToJson(List<String> list) {
		try {
			return mapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
