package idwall.tuliocarreira.desafios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Desafio1 {

	private static Scanner sc;

	// lê o texto de input e agrupa as linhas em uma única String
	public static String leTextoEntrada() {
		sc = new Scanner(System.in);
		String input = "";
		Boolean fim = false;

		while (sc.hasNextLine()) {
			String linha = sc.nextLine();
			if (linha.equals("") | linha.isEmpty()) {
				// Somente sai do loop quando são detectadas duas linhas vazias
				if (fim) {
					break;
				}
				// marca a boolean como verdadeira quando é encontrada a
				// primeira linha vazia
				fim = true;
			} else {
				input += linha;
			}
		}
		return input;
	}

	// retorna a quantidade de linhas do texto resultante
	public static int encontraQtdeLinhas(String input, int limite) {
		int totalLinhas = input.length() / limite;
		// ao dividir o tamanho do texto pelo limite de caracteres por linha,
		// obtem-se o número de linhas. caso haja resto na divisão, a última
		// linha conterá os caracteres excedentes
		if (input.length() % limite > 0) {
			totalLinhas++;
		}
		return totalLinhas;
	}

	// encontra o índice que representa o fim de uma linha do texto de entrada
	public static int encontraFimDaLinha(String input, int limiteLinha) {
		while (input.charAt(limiteLinha) != ' ') {
			// ao encontrar um espaço, sabe-se que uma palavra acaba de ser
			// terminada
			limiteLinha--;
		}
		return limiteLinha;
	}

	// retorna o texto conforme limite de caracteres e tipo de formatação
	// especificados
	public static List<String> geraTexto(String input, int limite, Boolean justificado) {
		List<String> texto = new ArrayList<String>();

		// validação dos parâmetros de entrada
		if (limite <= 0 || input.isEmpty()) {
			texto.add("Texto vazio ou limite de caracteres inválido");
			return texto;
		}

		int totalLinhas = encontraQtdeLinhas(input, limite);
		int fimLinha;

		// loop que varre a string de entrada linha por linha
		for (int numLinha = 1, index = 0; numLinha <= totalLinhas; numLinha++) {
			// evita que a linha seja iniciada com um caracter de espaço
			if (input.charAt(index) == ' ') {
				index++;
			}
			String linha;
			if (numLinha == totalLinhas) {
				// caso o loop se enconte na última linha do texto, não há
				// limite de caracteres a ser especificado na chamada da
				// função de substring
				linha = input.substring(index);

			} else {
				// função que encontra o caractere de fim da linha atual
				fimLinha = encontraFimDaLinha(input, index + limite);
				linha = input.substring(index, fimLinha);
				// o índice de início da próxima linha é o fim da linha
				// atual somado a 1
				index = fimLinha + 1;
			}
			if (justificado) {
				texto.add(justificaTextoLinha(linha, limite));
			} else {
				texto.add(linha);
			}
		}
		return texto;
	}

	// formata o texto do parâmetro no modo justificado
	public static String justificaTextoLinha(String linha, int limite) {
		String linhaResultante = "";
		// verifica quantos espaços devem ser distribuídos pela linha
		int espacosFaltando = limite - linha.length();

		// não é necessário justificar se falta somente um (ou nenhum) espaço
		if (espacosFaltando <= 1) {
			return linha;
		}

		// quebra a string de entrada entre palavras
		String[] palavras = linha.split(" ");

		int index = 0;
		// enquanto houver necessidade de preencher a linha com espaços...
		while (espacosFaltando > 0) {
			palavras[index] = palavras[index].concat(" ");
			index++;
			// ao chegar ao final da linha, o índice deve voltar para o início
			// da mesma
			if (index >= palavras.length) {
				index = 0;
			}
			espacosFaltando--;
		}

		for (int i = 0; i <= palavras.length - 1; i++) {
			// devolve os espaços que foram previamente removidos pelo split
			linhaResultante = linhaResultante.concat(palavras[i] + " ");
		}
		return linhaResultante;
	}

	public static void main(String args[]) throws IOException {
		// chama uma função para o ler o texto de entrada
		System.out.println("Insira o texto: ");
		String input = leTextoEntrada();

		// retorna um texto não justificado com 40 caracteres por linha
		List<String> textoNaoJustificado = geraTexto(input, 40, false);

		// retorna um texto justificado com 40 caracteres por linha
		List<String> textoJustificado = geraTexto(input, 40, true);

		// imprime a lista obtida quebrando uma linha para cada string
		System.out.println("...");
		System.out.println("Texto não justificado: ");
		for (String linha : textoNaoJustificado) {
			System.out.println(linha);
		}
		System.out.println("...");
		System.out.println("Texto justificado: ");
		for (String linha : textoJustificado) {
			System.out.println(linha);
		}
	}
}
