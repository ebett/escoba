package org.javagames.escoba.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;

public class ImageLoader {

  private static final String[] CARDS = {"A", "2", "3", "4", "5", "6", "7", /*"8", "9",*/ "S", "C", "R"};

  private static final String[] PALOS = {"B", "C", "E", "O"};

  private static final String FILE_EXT = ".jpeg";

  private Map<String, ImageIcon> cache = new HashMap<>();

  ImageIcon loadIcon(String filename) {
    return cache.computeIfAbsent(filename,
        (fname) -> {
          ImageIcon img = new ImageIcon(ImageLoader.class.getResource(fname));
          img.setDescription(filename.substring(8,10));
          return img;
    });
  }

  public List<ImageIcon> loadIcons() {
    List<ImageIcon> icons = new ArrayList<>(CARDS.length * PALOS.length);
    for (String card: CARDS) {
      for (String p : PALOS) {
        final String filename = "/images/" + card + p + FILE_EXT;
        icons.add(loadIcon(filename));
      }
    }
    return icons;
  }
}
