package com.meujogo.cm;

import com.meujogo.cm.modelo.Tabuleiro;
import com.meujogo.cm.visao.TabuleiroConsole;

public class Aplicacao {

	public static void main(String[] args) {

		Tabuleiro tabuleiro = new Tabuleiro(6, 6, 3);
		new TabuleiroConsole(tabuleiro);

	}

}
