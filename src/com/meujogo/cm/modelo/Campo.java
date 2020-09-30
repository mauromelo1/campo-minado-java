package com.meujogo.cm.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {

	private final int linha;
	private final int coluna;

	private boolean aberto = false;
	private boolean minado = false;
	private boolean marcado = false;

	private List<Campo> vizinhos = new ArrayList<>();

	public Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}

	boolean adicionarVizinho(Campo candidatoVizinho) {
		boolean linhaDiferente = (linha != candidatoVizinho.linha);
		boolean colunaDiferente = (coluna != candidatoVizinho.coluna);
		boolean diagonal = linhaDiferente && colunaDiferente;

		int deltaLinha = Math.abs(linha - candidatoVizinho.linha);
		int deltaColuna = Math.abs(coluna - candidatoVizinho.coluna);
		int deltaGeral = deltaLinha + deltaColuna;

		if (deltaGeral == 1 && !diagonal) {
			vizinhos.add(candidatoVizinho);
			return true;
		} else if (deltaGeral == 2 && diagonal) {
			vizinhos.add(candidatoVizinho);
			return true;
		} else {
			return false;
		}

	}

}
