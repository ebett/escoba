package org.javagames.escoba.core;

import org.javagames.escoba.core.Score;
import org.javagames.escoba.view.CardButton;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

  @Test
  void testReset() {
    Score score = new Score();
    score.update(1, List.of(new CardButton(new ImageIcon())));
    score.reset();

    assertEquals(0, score.getCards());
    assertEquals(0, score.getEscobas());
    assertEquals(0, score.getGolden());
    assertEquals(0, score.getSevens());
  }

  @Test
  void testUpdate() {
    Score score = new Score();
    CardButton card = new CardButton(new ImageIcon());
    card.setCardImage(new ImageIcon("7O.jpeg"));

    score.update(1, List.of(card));

    assertEquals(1, score.getEscobas());
    assertEquals(1, score.getCards());
    assertEquals(1, score.getSevens());
    assertEquals(1, score.getGolden());
  }

  @Test
  void testGetTotal() {
    Score score1 = new Score();
    Score score2 = new Score();
    score1.update(1, List.of(new CardButton(new ImageIcon())));

    assertTrue(score1.getTotal(score2) > 0);
  }
}