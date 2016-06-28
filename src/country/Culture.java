/*
 * Copyright 2016 Joseph Zehe
 */
package country;

/**
 *
 * @author Joseph Zehe
 */
public enum Culture {
  WESTERN_EUROPEAN,
  EASTERN_EUROPEAN,
  ASIAN,
  SOUTHAMERICAN,
  MIDDLE_EASTERN,
  AFRICAN;
  
  public static Culture fromString(String str) {
    switch (str) {
      case "western_european":
        return WESTERN_EUROPEAN;
      case "eastern_european":
        return EASTERN_EUROPEAN;
      case "asian":
        return ASIAN;
      case "southamerican":
        return SOUTHAMERICAN;
      case "middle_eastern":
        return MIDDLE_EASTERN;
      case "african":
        return AFRICAN;
      default:
        return WESTERN_EUROPEAN;
    }
  }

  @Override
  public String toString() {
    switch (this) {
      case WESTERN_EUROPEAN:
        return "western_european";
      case EASTERN_EUROPEAN:
        return "eastern_european";
      case ASIAN:
        return "asian";
      case SOUTHAMERICAN:
        return "southamerican";
      case MIDDLE_EASTERN:
        return "middle_eastern";
      case AFRICAN:
        return "african";
      default:
        return "default";
    }
  }

  private String getPortrait() {
    switch (this) {
      case WESTERN_EUROPEAN:
        return "Europe/Portrait_Europe_Generic";
      case EASTERN_EUROPEAN:
        return "Europe/Portrait_Europe_Generic";
      case ASIAN:
        return "Asia/Portrait_Asia_Generic";
      case SOUTHAMERICAN:
        return "South America/Portrait_South_America_Generic";
      case MIDDLE_EASTERN:
        return "SAU/Portrait_Arabia_Generic";
      case AFRICAN:
        return "Africa/Portrait_Africa_Generic";
      default:
        return "Europe/Portrait_Europe_Generic";
    }
  }
  
  public String getLeaderPortrait(Ideology ideology) {
    switch(ideology) {
      case DEMOCRATIC:
        return getPortrait() + "_2.dds";
      case COMMUNISM:
        return getPortrait() + "_1.dds";
      case FASCISM:
        return getPortrait() + "_3.dds";
      case NEUTRALITY:
        return getPortrait() + "_2.dds";
      default:
        return getPortrait() + "_2.dds";
    }
  }
}