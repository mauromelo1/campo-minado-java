package com.meujogo.cm.modelo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.meujogo.cm.excecao.ExplosaoException;

public class CampoTeste {

	private Campo campo;

	@BeforeEach
	void iniciarCampo() {
		campo = new Campo(3, 3);
	}

	@Test
	void testeVizinhoDistancia1Esq() {
		Campo vizinho = new Campo(3, 2);
		boolean resultado = campo.adicionarVizinhoCampo(vizinho);
		assertTrue(resultado);
	}

	@Test
	void testeVizinhoDistancia1Dir() {
		Campo vizinho = new Campo(3, 4);
		boolean resultado = campo.adicionarVizinhoCampo(vizinho);
		assertTrue(resultado);
	}

	@Test
	void testeVizinhoDistancia1Cima() {
		Campo vizinho = new Campo(2, 3);
		boolean resultado = campo.adicionarVizinhoCampo(vizinho);
		assertTrue(resultado);
	}

	@Test
	void testeVizinhoDistancia1Baixo() {
		Campo vizinho = new Campo(4, 3);
		boolean resultado = campo.adicionarVizinhoCampo(vizinho);
		assertTrue(resultado);
	}

	@Test
	void testeVizinhoDistancia2() {
		Campo vizinho = new Campo(2, 2);
		boolean resultado = campo.adicionarVizinhoCampo(vizinho);
		assertTrue(resultado);
	}

	@Test
	void testeNaoVizinho() {
		Campo vizinho = new Campo(1, 1);
		boolean resultado = campo.adicionarVizinhoCampo(vizinho);
		assertFalse(resultado);
	}

	@Test
	void testeValorPadraoAtributoMarcado() {
		assertFalse(campo.isMarcadoCampo());
	}

	@Test
	void testeAlternarMarcacao() {
		campo.alternarMarcacaoCampo();
		assertTrue(campo.isMarcadoCampo());
	}

	@Test
	void testeAlternarMarcacaoDuasChamadas() {
		campo.alternarMarcacaoCampo();
		campo.alternarMarcacaoCampo();
		assertFalse(campo.isMarcadoCampo());
	}

	@Test
	void testeAbrirNaoMinadoNaoMarcado() {
		assertTrue(campo.abrirCampo());
	}

	@Test
	void testeAbrirNaoMinadoMarcado() {
		campo.alternarMarcacaoCampo();
		assertFalse(campo.abrirCampo());
	}

	@Test
	void testeAbrirMinadoMarcado() {
		campo.alternarMarcacaoCampo();
		campo.minarCampo();
		assertFalse(campo.abrirCampo());
	}

	@Test
	void testeAbrirMinadoNaoMarcado() {
		campo.minarCampo();
		assertThrows(ExplosaoException.class, () -> {
			campo.abrirCampo();
		});
	}

	@Test
	void testeAbrirComVizinhos1() {
		Campo campo11 = new Campo(1, 1);
		Campo campo22 = new Campo(2, 2);

		campo22.adicionarVizinhoCampo(campo11);
		
		campo.adicionarVizinhoCampo(campo22);
		campo.abrirCampo();
		
		assertTrue(campo22.isAbertoCampo() && campo11.isAbertoCampo());
	}
	
	@Test
	void testeAbrirComVizinhos2() {
		Campo campo11 = new Campo(1, 1);
		Campo campo12 = new Campo(1, 2);
		campo12.minarCampo();
		
		Campo campo22 = new Campo(2, 2);

		campo22.adicionarVizinhoCampo(campo11);
		campo22.adicionarVizinhoCampo(campo12);
		
		campo.adicionarVizinhoCampo(campo22);
		campo.abrirCampo();
		
		assertTrue(campo22.isAbertoCampo() && campo11.isFechadoCampo());
	}

}
