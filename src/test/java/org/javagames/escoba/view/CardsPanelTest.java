package org.javagames.escoba.view;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardsPanelTest {

  @Test
  void testAddCard() {
    CardsPanel panel = new CardsPanel();
    CardButton card = new CardButton(new ImageIcon());
    panel.addCard(card);

    assertEquals(1, panel.getCards().size());
    assertTrue(panel.getCards().contains(card));
  }

  @Test
  void testAddCards() {
    CardsPanel panel = new CardsPanel();
    CardButton card1 = new CardButton(new ImageIcon());
    CardButton card2 = new CardButton(new ImageIcon());
    panel.addCards(List.of(card1, card2));

    assertEquals(2, panel.getCards().size());
    assertTrue(panel.getCards().containsAll(List.of(card1, card2)));
  }

  @Test
  void testIsEmpty() {
    CardsPanel panel = new CardsPanel();
    assertTrue(panel.isEmpty());

    panel.addCard(new CardButton(new ImageIcon()));
    assertFalse(panel.isEmpty());
  }
}