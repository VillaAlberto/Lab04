package it.polito.tdp.lab04.DAO;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class TestDB {

	public static void main(String[] args) {

		/*
		 * 	This is a main to check the DB connection
		 */
		
		CorsoDAO cdao = new CorsoDAO();
		cdao.getTuttiICorsi();
		
		System.out.println(cdao.verificaSeStudenteIscritto(new Studente(200482, null, null, null), new Corso("01PDYPG", 0, null, 0)));
		System.out.println(cdao.verificaSeStudenteIscritto(new Studente(123, null, null, null), new Corso("01PDYPG", 0, null, 0)));
		System.out.println(cdao.verificaSeStudenteIscritto(new Studente(200482, null, null, null), new Corso("01PDYP", 0, null, 0)));
	}

}
