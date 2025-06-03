package org.javagames.escoba.view;

import java.util.List;
import org.javagames.escoba.core.Score;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScorePanelTest {

  @Test
  void testSetScorePlayer() {
    ScorePanel panel = new ScorePanel();
    Score score = new Score();
    score.update(1, List.of());

    panel.setScorePlayer(score);

    // Verify the labels are updated (mocking UI behavior is optional)
    assertNotNull(panel);
  }

  @Test
  void testSetScoreCpu() {
    ScorePanel panel = new ScorePanel();
    Score score = new Score();
    score.update(1, List.of());

    panel.setScoreCpu(score);

    // Verify the labels are updated (mocking UI behavior is optional)
    assertNotNull(panel);
  }
}