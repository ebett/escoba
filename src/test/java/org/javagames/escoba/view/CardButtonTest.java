package org.javagames.escoba.view;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class CardButtonTest {

  @Test
  void testSetCardImage() {
    CardButton button = new CardButton(new ImageIcon());
    ImageIcon icon = new ImageIcon();
    button.setCardImage(icon);

    assertEquals(icon, button.getCardImage());
  }

  @Test
  void testSetHidden() {
    CardButton button = new CardButton(new ImageIcon());
    button.setHidden(true);

    assertTrue(button.isHidden());
  }

  @Test
  void testClone2() {
    ImageIcon bg = new ImageIcon();
    CardButton button = new CardButton(bg);
    CardButton clone = button.clone2();

    assertNotSame(button, clone);
    assertEquals(button.getCardImage(), clone.getCardImage());
  }
}