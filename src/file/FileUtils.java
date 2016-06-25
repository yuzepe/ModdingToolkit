/*
 * Copyright 2016 Joseph Zehe
 */
package file;

import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joseph Zehe
 */
public class FileUtils {

  /**
   * Erstellt falls nicht vorhanden den Dateibaum zu einer Datei.
   *
   * @param filename Pfad
   */
  public static void createPath(String filename) {
    File file = new File(filename);
    file = file.getParentFile();
    file.mkdirs();
  }

  /**
   * Erstellt den Dateibaum und die Datei.
   *
   * @param filename Pfad
   */
  private static void createFile(File file) {
    try {
      File parent = file.getParentFile();
      parent.mkdirs();
      file.createNewFile();
    } catch (IOException e) {
      System.out.println("Fehler beim Erstellen der Datei " + file.getName());
    }
  }

  public static String readFromFile(String filename) {
    StringBuilder content = new StringBuilder();
    BufferedReader input = null;
    try {
      input = new BufferedReader(new FileReader(filename));
      String line = null;
      while ((line = input.readLine()) != null) {
          content.append(line).append("\r\n");
      }
    } catch (IOException e) {
      System.out.println("Fehler beim Lesen aus Datei " + filename);
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (IOException e) {
          System.out.println("Fehler beim Schlißen der Datei " + filename);
        }
      }
    }
    return content.toString();
  }

  private static void writeToFileMode(String filename, String content, boolean mode) {
    File file = new File(filename);
    if(!file.exists()) {
      createFile(file);
      mode = false; // Existiert die Datei noch nicht kann an den Anfang geschrieben werden
    }
    FileWriter output = null;
    try {
      output = new FileWriter(file, mode);
      output.write(content);
    } catch (IOException e) {
      System.out.println("Fehler beim Schreiben in Datei " + filename);
    } finally {
      if (output != null) {
        try {
          output.close();
        } catch (IOException e) {
          System.out.println("Fehler beim Schlißen der Datei " + filename);
        }
      }
    }
  }
  
  public static void writeToFile(String filename, String content) {
    writeToFileMode(filename, content, false);
  }

  public static void appendToFile(String filename, String content) {
    writeToFileMode(filename, content, true);
  }

  /**
   * Kopiert eine Datei von A nach B. Ist der Pfad zu B nicht vorhanden wird er
   * erstellt.
   *
   * @param source
   * @param destination
   */
  public static void copyFile(String source, String destination) {
    createPath(destination);
    File srcFile = new File(source);
    File destFile = new File(destination);
    try {
      Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      System.out.println("Fehler beim Kopieren der Datei " + destination);
    }
  }
  
  public static void createPlainTGA(String filename, Color color, int width, int height) {
    File file = new File(filename);
    createFile(file);
    try (BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file))) {
      byte[] head = { 0b0, 0b0, 0b10, 0b0, 0b0, 0b0, 0b0, 0b0, 0b0, 0b0, 0b0, 0b0, (byte)width, 0b0, (byte)height, 0b0, 0b100000, 0b1000 };
      output.write(head);
      int size = width * height;
      int c = color.getRGB();
      byte[] body = { 
        (byte) c,
        (byte)(c >> 8),
        (byte)(c >> 16),
        (byte)(c >> 24) };
      for (int i = 0; i < size; i++) {
          output.write(body);
      }
    } catch (IOException e) {
      System.out.println("Fehler beim Schreiben in Datei " + filename);
    }
  }
}
