package it.polito.tdp.lab04;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnReset;

	@FXML
	private ChoiceBox<Corso> chBoxCorso;

	@FXML
	private Button btnCercaIscritti;

	@FXML
	private TextField lblMatricola;

	@FXML
	private Button btnAuto;

	@FXML
	private TextField lblNome;

	@FXML
	private TextField lblCognome;

	@FXML
	private Button btnCercaCorsi;

	@FXML
	private Button btnIscrivi;

	@FXML
	private TextArea txtResult;

	private Model model;

	@FXML
	void doCercaCorsi(ActionEvent event) {
		if (!doCompletamento())
			return;
		
		List <Corso> ls= model.getCorsiACuiEIscrittoLoStudente(new Studente(Integer.parseInt(lblMatricola.getText()), null, null, null));
		
		if (ls.size()==0)
		{
			txtResult.setText("Studente non iscritto a nessun corso");
			return;
		}
		
		String s="";
		for (Corso c: ls)
		{
			s+=String.format("%-10s%-5d%-50s%d\n",c.getCodins(), c.getCrediti(), c.getNome(), c.getPd());
		}
		txtResult.setText(s);
	}

	@FXML
	void doCercaIscritti(ActionEvent event) {
		
		lblNome.clear();
		lblCognome.clear();
		lblMatricola.clear();
		Corso c= chBoxCorso.getValue();
		
		if (c.getCodins()==null)
		{
			txtResult.setText("Corso non selezionato");
			return;
		}
		
		else {
			
			List<Studente> ls= model.getStudentiIscrittiAlCorso(c);
			
			if (ls.size()==0)
			{
				txtResult.setText("Nessuno studente iscritto al corso");
				return;
			}
			
			String output="";
			
			for (Studente s:ls)
			{
				output+=String.format("%-10d%-25s%-30s%s\n", s.getMatricola(), s.getNome(), s.getCognome(), s.getCds());	
			}
		
			
			txtResult.setText(output);
			
		}

	}

	@FXML
	void doCompletamento(ActionEvent event) {
		
		txtResult.clear();
		lblNome.clear();
		lblCognome.clear();
		int matricola=0;
		
		
		//Trasformo la stringa in intero
		try {
			matricola= Integer.parseInt(lblMatricola.getText());
		}
		
		catch(NumberFormatException e) {
			txtResult.setText("Matricola non numerica");
			return;
		}
		
		
		//Verifico che la matricola sia accettabile
		if (matricola>99999&&matricola<1000000)
		{
			Studente sTemp= model.getStudente(matricola);
			if (sTemp==null)
			{
				txtResult.setText("Studente non trovato");
				return;
			}
			lblCognome.setText(sTemp.getCognome());
			lblNome.setText(sTemp.getNome());
		}
		else txtResult.setText("Input insensato, la matricola deve essere positiva su 6 cifre");

	}

boolean doCompletamento() {
		
	
		lblNome.clear();
		lblCognome.clear();
		txtResult.clear();
		int matricola=0;
		
		
		//Trasformo la stringa in intero
		try {
			matricola= Integer.parseInt(lblMatricola.getText());
		}
		
		catch(NumberFormatException e) {
			txtResult.setText("Matricola non numerica");
			return false;
		}
		
		
		//Verifico che la matricola sia accettabile
		if (matricola>99999&&matricola<1000000)
		{
			Studente sTemp= model.getStudente(matricola);
			if (sTemp==null)
			{
				txtResult.setText("Studente non trovato");
				return false;
			}
			lblCognome.setText(sTemp.getCognome());
			lblNome.setText(sTemp.getNome());
			return true;
		}
		else {
			txtResult.setText("Input insensato, la matricola deve essere positiva su 6 cifre");
			return false;
		}
			

	}
	
	@FXML
	void doIscrivi(ActionEvent event) {
		if (!doCompletamento())
			{
			return;
			}
		Studente sTemp= new Studente(Integer.parseInt(lblMatricola.getText()), null, null, null);
		Corso cTemp= chBoxCorso.getValue();
		if (model.verificaSeStudenteIscritto(sTemp, cTemp))
		{
			txtResult.setText("Studente già iscritto al corso");
			return;
		}
		if (cTemp.getCodins()==null)
		{
			txtResult.setText("Selezionare un corso per effettuare l'iscrizione");
			return;
		}
		
		if (model.inscriviStudenteACorso(sTemp,cTemp))
		{
			txtResult.setText("Studente "+sTemp.getMatricola()+ " iscritto al corso " +cTemp.getNome());
			return;
		}
		
		else txtResult.setText("Errore nella chiamata al DB");

	}

	@FXML
	void doReset(ActionEvent event) {

		lblMatricola.clear();
		lblCognome.clear();
		lblNome.clear();
		txtResult.clear();

	}

	@FXML
	void initialize() {
		assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";
		assert chBoxCorso != null : "fx:id=\"chBoxCorso\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnCercaIscritti != null : "fx:id=\"btnCercaIscritti\" was not injected: check your FXML file 'Scene.fxml'.";
		assert lblMatricola != null : "fx:id=\"lblMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnAuto != null : "fx:id=\"btnAuto\" was not injected: check your FXML file 'Scene.fxml'.";
		assert lblNome != null : "fx:id=\"lblNome\" was not injected: check your FXML file 'Scene.fxml'.";
		assert lblCognome != null : "fx:id=\"lblCognome\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
		// List <String> ls= model.getNomiCorsi();
		// ls.add(" ");

		List<Corso> ls = model.getTuttiICorsi();
		Corso tuttiICorsi = new Corso(null, 0, "-", 0);
		ls.add(tuttiICorsi);

		chBoxCorso.getItems().addAll(ls);
		chBoxCorso.setValue(tuttiICorsi);
		txtResult.setStyle("-fx-font-family: monospace");
	}
}
