package respostas;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import telegrambot.TulrcBot;

public class Desafio2Teste2 {
	
	public static void main(String[] args) {
		
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			//registra o bot e inicializa a sessão
			telegramBotsApi.registerBot(new TulrcBot());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}