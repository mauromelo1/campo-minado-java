package com.meujogo.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import com.meujogo.cm.excecao.ExplosaoException;

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
	} // fim adicionarVizinho

	void alternarMarcacao() {
		if (!aberto) {
			marcado = !marcado;
		}
	} // fim alternarMarcacao

	boolean abrir() {
		if (!aberto && !marcado) {
			aberto = true;

			if (minado) {
				throw new ExplosaoException();
			} // fim if do minado

			if (vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			} // fim if vizinhancaSegura

			return true;

		} // fim if !aberto && !marcado
		else {
			return false;
		}
	} // fim abrir

	boolean vizinhancaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	} // fim vizinhancaSegura

	void minar() {
		minado = true;
	} // fim minar

	public boolean isMarcado() {
		return marcado;
	} // fim isMarcado

	public boolean isAberto() {
		return aberto;
	} // fim isAberto

	public boolean isFechado() {
		return !isAberto();
	} // fim fechado

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}

	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;

		return desvendado || protegido;
	} // fim objetivoAlcancado

	long minasNaVizinhanca() {
		return vizinhos.stream().filter(v -> v.minado).count();
	} // fim minasNavizinhanca

	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
	} // fim reiniciar

	public String toString() {
		if (marcado) {
			return "x";
		} else if (aberto && minado) {
			return "*";
		} else if (aberto && minasNaVizinhanca() > 0) {
			return Long.toString(minasNaVizinhanca());
		} else if (aberto) {
			return " ";
		} else {
			return "?";
		}
	} // fim toString

}
