package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public static List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				// Crea un nuovo JAVA Bean Corso
				Corso cTemp= new Corso(codins, numeroCrediti, nome, periodoDidattico);
				
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				corsi.add(cTemp);
			}

			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	
	
	public static Corso getCorso(Corso corso) {
		
		final String sql = "SELECT * FROM corso WHERE codins=?";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			
			ResultSet rs = st.executeQuery();
			Corso cTemp=null;

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				// Crea un nuovo JAVA Bean Corso
				cTemp= new Corso(codins, numeroCrediti, nome, periodoDidattico);
			}

			conn.close();
			
			return cTemp;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public static List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		final String sql = "SELECT * FROM iscrizione WHERE codins=? ";

		List<Studente> studenti = new LinkedList<Studente>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				int matricola = rs.getInt("matricola");
				Studente sTemp= StudenteDAO.getStudente(matricola);
				studenti.add(sTemp);
			}

			conn.close();
			
			return studenti;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public static boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		final String sql="INSERT INTO iscrizione (matricola, codins) values(?,?)";
		Connection conn= ConnectDB.getConnection();
		try {
		PreparedStatement st= conn.prepareStatement(sql);
		st.setInt(1, studente.getMatricola());
		st.setString(2, corso.getCodins());
		
		int rs= st.executeUpdate();
		if (rs==0)
			return false;
		return true;
		}
		
		catch(SQLException e){
			throw new RuntimeException("Errore Db", e);
		}
		
	}
	
	/*
	 * Data una matricola ed il codice insegnamento ritorna true se lo studente e'iscritto;
	 */
	public static boolean verificaSeStudenteIscritto(Studente studente, Corso corso) {
		final String sql="SELECT * FROM iscrizione WHERE matricola=?&&codins=?";
		try {
			Connection conn= ConnectDB.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodins());
			
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) {
				return true;
			}
			return false;
			
		}
		
		catch(SQLException e){
			throw new RuntimeException("Errore Db", e);
			
		}
	}


}
