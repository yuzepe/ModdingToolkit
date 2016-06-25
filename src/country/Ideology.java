/*
 * Copyright 2016 Joseph Zehe
 */
package country;

//democratic
//  conservatism
//  liberalism
//  socialism
//
//communism
//  marxism
//  leninism
//  stalinism
//  anti_revisionism
//  anarchist_communism
//
//fascism
//  nazism
//  fascism_ideology
//  falangism
//  rexism
//
//neutrality
//  despotism
//  oligarchism
//  moderatism
//  centrism

/**
 *
 * @author Joseph Zehe
 */
public enum Ideology {
  DEMOCRATIC,
  COMMUNISM,
  FASCISM,
  NEUTRALITY;
  
  public String getDefaultName() {
    switch(this) {
      case DEMOCRATIC:
        return "liberalism";
      case COMMUNISM:
        return "leninism";
      case FASCISM:
        return "fascism_ideology";
      case NEUTRALITY:
        return "despotism";
      default:
        return "despotism";
    }
  }
}

