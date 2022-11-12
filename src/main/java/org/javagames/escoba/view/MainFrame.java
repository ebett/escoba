package org.javagames.escoba.view;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.javagames.escoba.core.Game;
import org.javagames.escoba.core.Score;

public class MainFrame extends JFrame {

  public static final Color BOARD_BG_COLOR = Color.GREEN.darker().darker().darker();

  private CardsPanel cpuCardsPanel = new CardsPanel();

  private CardsPanel playerCardsPanel = new CardsPanel();

  private CardsPanel boardPanel;

  private JButton dealButton;

  private ScorePanel scorePanel;

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      MainFrame frame = new MainFrame();
      //frame.pack();
      frame.setVisible(true);
    });
  }

  public MainFrame() {
    super("Escoba");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation(100, 50);
    setSize((int) screenSize.getWidth() - 250, (int) screenSize.getHeight() -100);
    setLayout(new BorderLayout(0, 0));
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    getContentPane().add(cpuCardsPanel, BorderLayout.NORTH);

    getContentPane().add(playerCardsPanel, BorderLayout.SOUTH);

    getContentPane().add(createBoardPanel(), BorderLayout.CENTER);

    getContentPane().add(createScorePanel(), BorderLayout.EAST);

    new Game(this).start();
  }

  private CardsPanel createBoardPanel() {
    boardPanel = new CardsPanel();
    TitledBorder border = new TitledBorder(new LineBorder(Color.black),
            "Board",
            TitledBorder.CENTER,
            TitledBorder.BELOW_TOP);
    boardPanel.setBorder(border);
    boardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));
    return boardPanel;
  }

  public void setDealButton(final JButton dealButton) {
    Dimension size = new Dimension(150, 70);
    dealButton.setMinimumSize(size);
    dealButton.setPreferredSize(size);
    dealButton.setMaximumSize(size);
    scorePanel.add(dealButton);
    this.dealButton = dealButton;
  }

  private JPanel createScorePanel() {
    scorePanel = new ScorePanel();
    return scorePanel;
  }

  public void setCpuCards(final List<CardButton> cards) {
    cards.forEach(c -> {
      c.setHidden(true);
      c.setEnabled(false);
    });
    cpuCardsPanel.addCards(cards);
  }

  public void setPlayerCards(final List<CardButton> cards) {
    playerCardsPanel.addCards(cards);
  }

  public void addBoardCard(final CardButton card) {
    addBoardCard(card, true);
  }

  public void addBoardCard(final CardButton card, final boolean repaint) {
    boardPanel.addCard(card);
    if (repaint) {
      refresh();
    }
  }

  public List<CardButton> getBoardCards() {
    return boardPanel.getCards();
  }

  public void removePlayerCard(final CardButton cardButton) {
    playerCardsPanel.remove(cardButton);
  }

  public void removeBoardCards(final List<CardButton> cards) {
    cards.forEach(boardPanel::remove);
  }

  public void refresh() {
    Container contentPane = getContentPane();
    contentPane.revalidate();
    contentPane.repaint();
  }

  public boolean hasEmptyPlayerCards() {
    return playerCardsPanel.isEmpty();
  }

  public List<CardButton> getCpuCards() {
    return cpuCardsPanel.getCards();
  }

  public void removeCpuCard(final CardButton card) {
    cpuCardsPanel.remove(card);
  }

  public void updateScorePlayer(final Score score) {
    scorePanel.setScorePlayer(score);
  }

  public void updateScoreCpu(final Score score) {
    scorePanel.setScoreCpu(score);
  }

  public void clearBoard() {
    boardPanel.removeAll();
  }
}
