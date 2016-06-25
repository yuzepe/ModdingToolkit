/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package country;

import java.awt.Color;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Joseph Zehe
 */
public class FXMLController implements Initializable {

  @FXML
  private TextField tfProjectName;
  @FXML
  private TextField tfTemplateName;
  @FXML
  private TextField tfCountryTag;
  @FXML
  private TextField tfCountryName;
  @FXML
  private TextField tfCountryCapital;
  @FXML
  private TextField tfCountryCapitalProvince;
  @FXML
  private TextField tfCountryLeader;
  @FXML
  private ColorPicker cpCountryColor;
  @FXML
  private ChoiceBox cbCountryCulture;
  @FXML
  private Button btnExecute;
  @FXML
  private Label lbMessage;
  
  private boolean tryParseInt(String str) {
    try {  
      Integer.parseInt(str);  
      return true;  
     } catch (NumberFormatException e) {  
      return false;  
     }  
  }

  @FXML
  private void btnExecuteClicked(ActionEvent event) {
    if(tfProjectName.getText().equals("")
    || tfTemplateName.getText().equals("")
    || tfCountryTag.getText().equals("")
    || tfCountryName.getText().equals("")
    || tfCountryCapital.getText().equals("")
    || tfCountryCapitalProvince.getText().equals("")
    || tfCountryLeader.getText().equals("")) {
      lbMessage.setText("All fields have to be filled!");
    } else if(!tryParseInt(tfCountryCapital.getText()) 
           || !tryParseInt(tfCountryCapitalProvince.getText())) {
      lbMessage.setText("There must be a capital state and province number!");
    } else {
      String project = tfProjectName.getText();
      String template = tfTemplateName.getText();
      String tag = tfCountryTag.getText();
      String name = tfCountryName.getText();
      int capital = Integer.parseInt(tfCountryCapital.getText());
      int capitalProvince = Integer.parseInt(tfCountryCapitalProvince.getText());
      String leader = tfCountryLeader.getText();
      javafx.scene.paint.Color fxColor = cpCountryColor.getValue();
      Color color = new Color((float)fxColor.getRed(), (float)fxColor.getGreen(), (float)fxColor.getBlue());
      Culture culture = Culture.fromString(cbCountryCulture.getValue().toString());

      Country country = new Country(project, template, tag, name, capital, capitalProvince, leader, color, culture);
      country.writeToFiles();
      
      lbMessage.setText("Country " + name + " has been generated.");
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    
  }

}
