package com.meujogo.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.meujogo.cm.excecao.ExplosaoException;

public class Tabuleiro {

	private int linhasTabuleiro;
	private int colunasTabuleiro;
	private int minasTabuleiro;

	private final List<Campo> campos = new ArrayList<>();

	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhasTabuleiro = linhas;
		this.colunasTabuleiro = colunas;
		this.minasTabuleiro = minas;

		gerarCamposTabuleiro();
		associarVizinhosTabuleiro();
		sortearMinasTabuleiro();

	} // fim construtor

	public void abrir(int linha, int coluna) {
		try {
			campos.parallelStream().filter(c -> c.getLinhaCampo() == linha && c.getColunaCampo() == coluna).findFirst()
					.ifPresent(c -> c.abrirCampo());
		} catch (ExplosaoException e) {
			campos.forEach(c -> c.setAbertoCampo(true));
			throw e;
		}
	} // fim abrir

	public void alternarMarcacao(int linha, int coluna) {
		campos.parallelStream().filter(c -> c.getLinhaCampo() == linha && c.getColunaCampo() == coluna).findFirst()
				.ifPresent(c -> c.alternarMarcacaoCampo());
	} // fim AlternarMarcacao

	private void gerarCamposTabuleiro() {

		for (int l = 0; l < linhasTabuleiro; l++) {
			for (int c = 0; c < colunasTabuleiro; c++) {
				campos.add(new Campo(l, c));
			} // for c
		} // for l
	} // fim gerarCampos

	private void associarVizinhosTabuleiro() {
		for (Campo c1 : campos) {
			for (Campo c2 : campos) {
				c1.adicionarVizinhoCampo(c2);
			} // for c2
		} // for c1
	} // fim associarOsVizinhos

	private void sortearMinasTabuleiro() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinadoCampo();

		do {
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minarCampo();
			minasArmadas = campos.stream().filter(minado).count();
		} while (minasArmadas < minasTabuleiro);
	} // sortearMinas

	public boolean objetivoAlcancadoTabuleiro() {
		return campos.stream().allMatch(c -> c.objetivoAlcancadoCampo());
	} // fim objetivoAlcancado

	public void reiniciarTabuleiro() {
		campos.stream().forEach(c -> c.reiniciarCampo());
		sortearMinasTabuleiro();
	} // fim reiniciar

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("  ");
		for (int c = 0; c < colunasTabuleiro; c++) {
			sb.append(" " + c + " ");
		}
		sb.append("\n");
		int i = 0;
		for (int l = 0; l < linhasTabuleiro; l++) {
			sb.append(l + " ");
			for (int c = 0; c < colunasTabuleiro; c++) {
				sb.append(" ");
				sb.append(campos.get(i));
				sb.append(" ");
				i++;
			} // for c
			sb.append("\n");
		} // for l

		return sb.toString();
	} // fim toString
}
