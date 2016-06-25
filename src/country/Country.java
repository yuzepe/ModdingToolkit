/*
 * Copyright 2016 Joseph Zehe
 */
package country;

import file.FileUtils;
import java.awt.Color;

/**
 *
 * @author Joseph Zehe
 */
public class Country {

  String project;
  String template;
  String tag;
  String name;
  int capital;
  int capitalProvince;
  String leader;
  Color color;
  Culture culture;

  public Country(String project, String template, String tag, String name, int capital, int capitalProvince, String leader, Color color, Culture culture) {
    this.project = project;
    this.template = template;
    this.tag = tag;
    this.name = name;
    this.capital = capital; //state id
    this.capitalProvince = capitalProvince; //province id
    this.leader = leader;
    this.color = color;
    this.culture = culture;
  }
  
  private String buildLeaderString(Ideology ideology) {
    StringBuilder strBuilder = new StringBuilder();
    strBuilder.append("\r\ncreate_country_leader = {");
    strBuilder.append("\r\n\tname = \"").append(leader).append("\"");
    strBuilder.append("\r\n\tdesc = \"\"");
    strBuilder.append("\r\n\tpicture = \"gfx/leaders/").append(culture.getLeaderPortrait(ideology)).append("\"");
    strBuilder.append("\r\n\tideology = ").append(ideology.getDefaultName());
    strBuilder.append("\r\n\ttraits = {");
    strBuilder.append("\r\n\t\t#");
    strBuilder.append("\r\n\t}");
    strBuilder.append("\r\n}");
    return strBuilder.toString();
  }

  /**
   * Schreibt die dem Land zugehörigen Daten in die notwendigen Dateien.
   * Manche Einträge sind nur Vorlagen, oder werden ganz weggelassen,
   * da sie immer Individuell bearbeitet werden müssen.
   */
  public void writeToFiles() {
    String path;
    String source;
    String destination;
    String fileInput;
    StringBuilder fileOutput;
    
    //Farbe und Standartportraits festlegen
    destination = project + "/common/countries/" + name + ".txt";
    fileOutput = new StringBuilder("");
    fileOutput.append("graphical_culture = ").append(culture.toString()).append("_gfx");
    fileOutput.append("\r\ngraphical_culture_2d = ").append(culture.toString()).append("_2d");
    fileOutput.append("\r\n\r\ncolor = { ").append(color.getRed()).append(" ").append(color.getGreen()).append(" ").append(color.getBlue()).append(" }");
    FileUtils.writeToFile(destination, fileOutput.toString());
    

    //Farbe des Landes speichern
    destination = project + "/common/countries/" + project + "_colors.txt";
    fileOutput = new StringBuilder("");
    fileOutput.append("\r\n").append(tag).append(" = {");
    fileOutput.append("\r\n\tcolor = rgb { ").append(color.getRed()).append(" ").append(color.getGreen()).append(" ").append(color.getBlue()).append(" }");
    fileOutput.append("\r\n\tcolor_ui = rgb { ").append(color.getRed()).append(" ").append(color.getGreen()).append(" ").append(color.getBlue()).append(" }");
    fileOutput.append("\r\n}");
    FileUtils.appendToFile(destination, fileOutput.toString());

    //Referenz für neues Land erstellen
    destination = project + "/common/country_tags/" + project + "_countries.txt";
    fileOutput = new StringBuilder("");
    fileOutput.append("\r\n").append(tag).append(" = \"countries/").append(name).append(".txt\"");
    FileUtils.appendToFile(destination, fileOutput.toString());
    
    //Personennamensdatei - Kulturabhängig
    source = template + "/common/names/" + culture.toString() + "_names.txt";
    destination = project + "/common/names/" + project + "_names.txt";
    fileInput = FileUtils.readFromFile(source);
    fileOutput = new StringBuilder("");
    fileOutput.append("\r\n").append(tag).append(fileInput);
//    fileOutput.append("\r\n").append(tag).append(" = {");
//    fileOutput.append("\r\n\tmale = {");
//    fileOutput.append("\r\n\t\tnames = { \"John\" \"Bob\" \"Lucas\" \"Marcus\" \"Alexander\" \"Alex\" \"Peter\" \"Jonas\" \"Jim\" \"Johannes\"}");
//    fileOutput.append("\r\n\t}");
//    fileOutput.append("\r\n\tfemale = {");
//    fileOutput.append("\r\n\t\tnames = { }");
//    fileOutput.append("\r\n\t}");
//    fileOutput.append("\r\n\tsurnames = { \"Smith\" \"Jones\" \"Taylor\" \"Brown\" \"Williams\" \"Wilson\" \"Evans\" \"Walker\" \"White\" \"Roberts\" }");
//    fileOutput.append("\r\n\tcallsigns = { }");
//    fileOutput.append("\r\n}");
    FileUtils.appendToFile(destination, fileOutput.toString());
    
    //Standartportraits für Generäle und Admirale
    //TODO: Aus Datei lesen und je nach Culture abhängig Nutzen
//    destination = project + "/common/portraits/" + project + "_portraits.txt";
//    fileOutput = new StringBuilder("");
//    fileOutput.append("\r\n").append(tag).append(" = {");
//    RAJ = {
//            army = {
//                    male = {
//                            "gfx/leaders/Asia/Portrait_Asia_Generic_land_1.dds"
//                            "gfx/leaders/Asia/Portrait_Asia_Generic_land_2.dds"
//                            "gfx/leaders/Asia/Portrait_Asia_Generic_land_3.dds"
//                            "gfx/leaders/Asia/Portrait_Asia_Generic_land_4.dds"
//                            "gfx/leaders/Asia/Portrait_Asia_Generic_land_5.dds"
//                    }
//            }
//
//            navy = {
//                    male = {
//                            "gfx/leaders/Asia/Portrait_Asia_Generic_navy_1.dds"
//                            "gfx/leaders/Asia/Portrait_Asia_Generic_navy_2.dds"
//                            "gfx/leaders/Asia/Portrait_Asia_Generic_navy_3.dds"
//                    }
//            }
//    }
//    fileOutput.append("\r\n}");
//    FileUtils.appendToFile(destination, fileOutput.toString());
    
    //Flaggen erstellen
    FileUtils.createPlainTGA(project + "/gfx/flags/" + tag + ".tga", color, 82, 52);
    FileUtils.createPlainTGA(project + "/gfx/flags/medium/" + tag + ".tga", color, 41, 26);
    FileUtils.createPlainTGA(project + "/gfx/flags/small/" + tag + ".tga", color, 10, 7);
    
    //Hauptdatei erstellen
    path = "/history/countries/";
    source = template + path + "TAG - Name.txt";
    destination = project + path + tag + " - " + name + ".txt";
    fileInput = FileUtils.readFromFile(source);
    fileOutput = new StringBuilder("");
    fileOutput.append("capital = ").append(capital);
    fileOutput.append("\r\n\r\noob = \"").append(tag).append("_1936\"");
    fileOutput.append("\r\n\r\n").append(fileInput);
    fileOutput.append(buildLeaderString(Ideology.DEMOCRATIC));
    fileOutput.append(buildLeaderString(Ideology.COMMUNISM));
    fileOutput.append(buildLeaderString(Ideology.FASCISM));
    fileOutput.append(buildLeaderString(Ideology.NEUTRALITY));
    FileUtils.writeToFile(destination, fileOutput.toString());

    //Einheitentemplates + Waffenproduktion
    path = "/history/units/";
    source = template + path + "TAG_1936.txt";
    destination = project + path + tag + "_1936.txt";
    fileInput = FileUtils.readFromFile(source);
    fileOutput = new StringBuilder("");
    fileOutput.append(fileInput);
    fileOutput.append("\r\nunits = {");
    fileOutput.append("\r\n\tdivision= {");
    fileOutput.append("\r\n\t\tname = \"Infantry Division\"");
    fileOutput.append("\r\n\t\tlocation = ").append(capitalProvince);
    fileOutput.append("\r\n\t\tdivision_template = \"Infantry Division\"");
    fileOutput.append("\r\n\t\tstart_experience_factor = 0.1");
    fileOutput.append("\r\n\t\tstart_equipment_factor = 0.5");
    fileOutput.append("\r\n\t}");
    fileOutput.append("\r\n}");
    fileOutput.append("\r\n\r\ninstant_effect = {");
    fileOutput.append("\r\n\tadd_equipment_production = {");
    fileOutput.append("\r\n\t\tequipment = {");
    fileOutput.append("\r\n\t\t\ttype = infantry_equipment_0");
    fileOutput.append("\r\n\t\t\tcreator = \"").append(tag).append("\"");
    fileOutput.append("\r\n\t\t}");
    fileOutput.append("\r\n\t\trequested_factories = 1");
    fileOutput.append("\r\n\t\tprogress = 0.22");
    fileOutput.append("\r\n\t\tefficiency = 100");
    fileOutput.append("\r\n\t}");
    fileOutput.append("\r\n}");
    FileUtils.writeToFile(destination, fileOutput.toString());
  }
}
