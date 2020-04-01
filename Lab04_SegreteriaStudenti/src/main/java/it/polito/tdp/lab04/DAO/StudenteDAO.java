package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	
	public static Studente getStudente (int m) {
		final String sql = "SELECT * FROM studente WHERE matricola=?";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, m);

			ResultSet rs = st.executeQuery();
			
			Studente sTemp= null;

			while (rs.next()) {
				int matricola = rs.getInt("matricola");
				String cognome = rs.getString("cognome");
				String nome = rs.getString("nome");
				String cds = rs.getString("CDS");

				// Crea un nuovo JAVA Bean Studente
				sTemp= new Studente(matricola, cognome, nome, cds);
			}
				
			conn.close();
			
			return sTemp;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	public static List<Corso> getCorsiACuiEIscrittoLoStudente(Studente studente){
		
		final String sql = "SELECT codins FROM iscrizione WHERE matricola=?";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");

				// Crea un nuovo JAVA Bean Corso
				Corso cTemp= CorsoDAO.getCorso(new Corso(codins, 0, null, 0));
				
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				corsi.add(cTemp);
			}

			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		
	};

}
