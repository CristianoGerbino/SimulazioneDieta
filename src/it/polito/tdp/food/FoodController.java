/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.ResourceBundle;

import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.food.db.Condiment;
import it.polito.tdp.food.model.Ingrediente;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="boxIngrediente"
    private ComboBox<Condiment> boxIngrediente; // Value injected by FXMLLoader

    @FXML // fx:id="btnDietaEquilibrata"
    private Button btnDietaEquilibrata; // Value injected by FXMLLoader
    
    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcolaDieta(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	
    	
    	
    	Condiment c = this.boxIngrediente.getValue();
    	
    	if (c == null) {
    		this.txtResult.appendText("Errore: seleziona un ingrediente!\n");
    		return;
    	}
    	
    	this.txtResult.appendText("Lista archi:\n");
    	for (DefaultWeightedEdge e: model.getGrafo().edgeSet()) {
    		this.txtResult.appendText(e+"\n");
    	}
    	
    	this.txtResult.appendText("Lista ingredienti contenente "+c+":\n");
    	for (Condiment ing : model.trovaDieta(c)) {
    		this.txtResult.appendText("-"+ing+"\n");
    	}
    	}

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	String s = this.txtCalorie.getText();
    	if (s.equals("")) {
    		this.txtResult.appendText("Errore! Inserisci un numero reale\n");
    		return;
    	}
    	
    	Double calories;
    	try {
    		calories = Double.parseDouble(s);
    	} catch (NumberFormatException nfe) {
    		this.txtResult.appendText("Errore! Inserisci un numero reale\n");
    		throw nfe;
    	}
    	this.txtResult.appendText("CALORIE = "+calories+"\n");
    	model.creaGrafo(calories);
    	this.txtResult.appendText(String.format("Creato grafo con %d vertici e %d archi\n", model.getNumVertici(), model.getNumArchi()));
    	this.boxIngrediente.getItems().addAll(model.getCondimentiGrafo());
    	
    	this.txtResult.appendText("LISTA INGREDIENTI:\n");
    	for (Ingrediente i : model.trovalistaIngredienti()) {
    		this.txtResult.appendText("-"+i+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxIngrediente != null : "fx:id=\"boxIngrediente\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnDietaEquilibrata != null : "fx:id=\"btnDietaEquilibrata\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
