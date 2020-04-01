package it.polito.tdp.lab04.model;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {

//	public List<String> getNomiCorsi() {
//		List <String> ls= new LinkedList<String>();
//		
//		for (Corso c: CorsoDAO.getTuttiICorsi())
//			ls.add(c.getNome());
//		
//		return ls;
//	}

	public List<Corso> getTuttiICorsi() {
		return CorsoDAO.getTuttiICorsi();
	}

	public Studente getStudente(int m) {
		return StudenteDAO.getStudente(m);
	}
	
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso){
		return CorsoDAO.getStudentiIscrittiAlCorso(corso);
	}
	
	public List<Corso> getCorsiACuiEIscrittoLoStudente(Studente studente){
		return StudenteDAO.getCorsiACuiEIscrittoLoStudente(studente);
	}

	public boolean verificaSeStudenteIscritto(Studente studente,Corso corso) {
		return CorsoDAO.verificaSeStudenteIscritto(studente, corso);
	}

	public boolean inscriviStudenteACorso(Studente sTemp, Corso cTemp) {
		return CorsoDAO.inscriviStudenteACorso(sTemp, cTemp);
	}
	
}
