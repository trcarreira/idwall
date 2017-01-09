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

	// lê o texto de input e agrupa as linhas em uma única String
	public static List<String> leTextoEntrada() {
		sc = new Scanner(System.in);
		String[] input = sc.nextLine().split(";");
		List<String> subreddits = new ArrayList<String>();
		for (int i = 0; i <= input.length - 1; i++) {
			subreddits.add(input[i]);
		}
		return subreddits;
	}

	public static List<Submission> buscaInfoSubreddit(Submissions subms, String subreddit) {
		List<Submission> threads = subms.ofSubreddit(subreddit, SubmissionSort.TOP, -1, 100, null, null, true);
		List<Submission> threadsSelecionadas = new ArrayList<Submission>();
		for (Submission s : threads) {
			if (s.getScore() >= 500) {
				threadsSelecionadas.add(s);
			}
		}
		return threadsSelecionadas;
	}

	public static void main(String[] args) {
		List<String> subreddits = leTextoEntrada();
		// Initialize REST Client

		RestClient restClient = new HttpRestClient();
		restClient.setUserAgent("bot/1.0 by name");

		// Handle to Submissions, which offers the basic API submission
		// functionality
		Submissions subms = new Submissions(restClient);
		List<Submission> threadsEscolhidas = new ArrayList<Submission>();

		for (String subreddit : subreddits) {
			threadsEscolhidas = buscaInfoSubreddit(subms, subreddit);
			for (Submission thread : threadsEscolhidas) {
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