/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Food;
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

    @FXML // fx:id="txtPorzioni"
    private TextField txtPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalorie"
    private Button btnCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="boxFood"
    private ComboBox<Food> boxFood; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	try {
    		int porzioni = Integer.parseInt(txtPorzioni.getText());
    		if(porzioni < 1 || porzioni > 3) {
    			txtResult.appendText("Inserire un numero intero di porzioni compreso fra 1 e 3.\n");
    			return;
    		}
    		this.model.creaGrafo(porzioni);
    		boxFood.getItems().clear();
    		boxFood.getItems().addAll(this.model.getListaCibi());
    		txtResult.setText("Grafo creato!\nNumero vertici: " + model.getNumeroVertici());
    		txtResult.appendText("\nNumero archi: " + model.getNumeroArchi() + "\n");
    	} catch (NumberFormatException e) {
    		txtResult.appendText("Inserire un numero intero di porzioni fra 1 e 3 (compresi).\n");
    		return;
    	} catch (NullPointerException e) {
    		txtResult.appendText("Inserire un numero intero di porzioni fra 1 e 3 (compresi).\n");
    		return;
      	}
    }
    
    @FXML
    void doCalorie(ActionEvent event) {
    	
    	
    	try {
    		Food food = boxFood.getValue();
    		List<Adiacenza> adiacenze = new ArrayList<>(this.model.getCalorie(food));
    		txtResult.appendText("Lista cibi con calorie congiunte massime:\n");
    		for(int i=0; i<5 && i<adiacenze.size(); i++) {
    			txtResult.appendText(adiacenze.get(i).toString() + "\n");
    		}
    			
    		
    	}catch(NullPointerException e) {
    		txtResult.appendText("Creare il grafo e scegliere un cibo prima di proseguire.\n");
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	
    	try {
    		Food primo = boxFood.getValue();
    		int K = Integer.parseInt(txtK.getText());
    		this.model.simula(primo, K);
    		txtResult.appendText("Simulazione avvenuta con successo, risultati per " + K + " cucine:\n");
    		txtResult.appendText(this.model.getTotPreparazioni() + " preparazioni in " + this.model.getTotTime().toMinutes() + " minuti.\n");
    	}catch (NumberFormatException e) {
    		txtResult.appendText("Inserire un numero di cucine compreso fra 1 e 10.\n");
    		return;
    	} catch (NullPointerException e) {
    		txtResult.appendText("Creare il grafo, selezionae un cibo e inserire numero di cucine prima di procedere.\n");
    		return;
      	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCalorie != null : "fx:id=\"btnCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
