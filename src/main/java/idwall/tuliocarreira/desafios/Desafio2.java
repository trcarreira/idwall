package idwall.tuliocarreira.desafios;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.github.jreddit.entity.Submission;
import com.github.jreddit.retrieval.Submissions;
import com.github.jreddit.retrieval.params.SubmissionSort;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

public class Desafio2 {
	private static Scanner sc;

	// lê o texto de input e agrupa os subreddits delimitados por
	// ponto-e-vírgula em uma lista de strings
	public static List<String> leTextoEntrada() {
		sc = new Scanner(System.in);
		String[] input = sc.nextLine().split(";");
		List<String> subreddits = new ArrayList<String>();
		for (int i = 0; i <= input.length - 1; i++) {
			subreddits.add(input[i]);
		}
		return subreddits;
	}

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

	public static void main(String[] args) {
		List<String> subreddits = leTextoEntrada();

		// Inicializa o REST Client
		RestClient restClient = new HttpRestClient();
		restClient.setUserAgent("bot/1.0 by name");

		// Manipula a API de Submissions
		Submissions subms = new Submissions(restClient);
		List<Submission> threadsEscolhidas = new ArrayList<Submission>();

		for (String subreddit : subreddits) {
			threadsEscolhidas = buscaInfoSubreddit(subms, subreddit);
			for (Submission thread : threadsEscolhidas) {
				// Exibe algumas informações para cada thread selecionada
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("Subreddit: " + thread.getSubreddit());
				System.out.println("Título: " + thread.getTitle());
				System.out.println("Upvotes: " + thread.getUpVotes());
				System.out.println("Link: " + thread.getURL());
				System.out.println("Qtde de comentários: " + thread.getCommentCount());
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			}

			System.out.println("...");
			System.out.println("...");
			System.out.println("...");

		}
	}
}