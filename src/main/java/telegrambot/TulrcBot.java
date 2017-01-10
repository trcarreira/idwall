package telegrambot;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.github.jreddit.entity.Submission;
import com.github.jreddit.retrieval.Submissions;
import com.github.jreddit.retrieval.params.SubmissionSort;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

public class TulrcBot extends TelegramLongPollingBot {

	// busca informações sobre um subreddit específico
	public static List<Submission> buscaInfoSubreddit(Submissions subms, String subreddit) {
		// busca as 100 threads mais "populares"
		List<Submission> threads = subms.ofSubreddit(subreddit, SubmissionSort.TOP, -1, 100, null, null, true);
		List<Submission> threadsSelecionadas = new ArrayList<Submission>();
		for (Submission s : threads) {
			if (s.getScore() >= 500) {
				// quando o score de uma thread é maior que 500, ela é
				// adicionada à lista de retorno
				threadsSelecionadas.add(s);
			}
		}
		return threadsSelecionadas;
	}

	public void onUpdateReceived(Update update) {

		if (update.hasMessage() && update.getMessage().hasText()) {

			String comando = update.getMessage().getText();
			String[] entrada = null;
			String saida = "";
			if (comando.startsWith("/NadaPraFazer ")) {
				// caso o comando digital seja o /NadaPraFazer, considera-se as
				// categorias delimitadas com ponto-e-vírgula logo depois dele
				entrada = comando.substring(14).split(";");
				List<String> subreddits = new ArrayList<String>();
				for (int i = 0; i <= entrada.length - 1; i++) {
					subreddits.add(entrada[i]);
				}

				// Inicializa o REST Client
				RestClient restClient = new HttpRestClient();
				restClient.setUserAgent("bot/1.0 by name");

				// Manipula a API de Submissions
				Submissions subms = new Submissions(restClient);
				List<Submission> threadsEscolhidas = new ArrayList<Submission>();

				for (String subreddit : subreddits) {
					threadsEscolhidas = buscaInfoSubreddit(subms, subreddit);
					for (Submission thread : threadsEscolhidas) {
						// incrementa, na saída, algumas informações para cada
						// thread selecionada
						saida += "~~~~~~~~~~~~~~~~~~~~~~~\n";
						saida += "Subreddit: " + thread.getSubreddit() + "\n";
						saida += "Título: " + thread.getTitle() + "\n";
						saida += "Upvotes: " + thread.getUpVotes() + "\n";
						saida += "Link: " + thread.getURL() + "\n";
						saida += "Qtde de comentários: " + thread.getCommentCount() + "\n";
						saida += "~~~~~~~~~~~~~~~~~~~~~~~\n";
					}

					saida += "...\n";
					saida += "...\n";
				}
			} else {
				saida = "Comando inválido";
			}

			// monta a mensagem a ser devolvida pelo bot ao usuário
			SendMessage message = new SendMessage().setChatId(update.getMessage().getChatId());
			try {
				sendMessage(message);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
	}

	public String getBotUsername() {
		// username do bot criado com a ajuda do BotFather
		return "trcarreiraBot";
	}

	@Override
	public String getBotToken() {
		// token fornecido pelo BotFather
		return "320617494:AAE4HzaleNCbJdKRo6QIt4EEIBo_AeZMdRk";
	}
}
