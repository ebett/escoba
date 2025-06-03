package org.javagames.escoba.view;

import javax.swing.ImageIcon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainFrameTest {

  @Test
  void testInitialization() {
    MainFrame frame = new MainFrame();
    assertNotNull(frame);
    assertNotNull(frame.getContentPane());
  }

  @Test
  void testAddBoardCard() {
    MainFrame frame = new MainFrame();
    CardButton card = new CardButton(new ImageIcon());
    frame.addBoardCard(card);

    assertTrue(frame.getBoardCards().contains(card));
  }

  @Test
  void testClearBoard() {
    MainFrame frame = new MainFrame();
    frame.clearBoard();

    assertTrue(frame.getBoardCards().isEmpty());
  }
}