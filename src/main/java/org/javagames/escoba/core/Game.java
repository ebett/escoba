package org.javagames.escoba.core;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.*;

import org.javagames.escoba.core.strategy.ChoiceStrategy;
import org.javagames.escoba.core.strategy.DefaultChoiceStrategy;
import org.javagames.escoba.view.CardButton;
import org.javagames.escoba.view.MainFrame;

public class Game {
  private final MainFrame mainFrame;

  private List<ImageIcon> cardIcons;

  private JButton dealButton;

  private List<CardButton> cpuCards;

  private List<CardButton> playerCards;

  private List<CardButton> selectedPlayerCards = new ArrayList<>();

  private LinkedList<ImageIcon> mazeIcons;

  private ImageIcon bg;

  private Score scorePlayer = new Score();

  private Score scoreCpu = new Score();

  private ChoiceStrategy choiceStrategy = new DefaultChoiceStrategy();

  public Game(final MainFrame mainFrame) {
    this.mainFrame = mainFrame;
    init();
  }

  private void init() {
    final ImageLoader imageLoader = new ImageLoader();
    bg = imageLoader.loadIcon("/images/bg.png");
    cardIcons = imageLoader.loadIcons();

    cpuCards = Arrays.asList(new CardButton(bg), new CardButton(bg), new CardButton(bg));
    mainFrame.setCpuCards(cpuCards);

    playerCards = Arrays.asList(createPlayerCard(bg), createPlayerCard(bg), createPlayerCard(bg));
    mainFrame.setPlayerCards(playerCards);

    mainFrame.setDealButton(createDealButton());
  }

  private JButton createDealButton() {
    dealButton = new JButton("Deal");
    dealButton.setEnabled(false);
    dealButton.setFont(new Font("Serif", Font.BOLD, 18));
    dealButton.setForeground(Color.orange.darker());
    dealButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    dealButton.addActionListener(this::mazeClick);
    return dealButton;
  }

  public void start() {
    scorePlayer.reset();
    scoreCpu.reset();
    selectedPlayerCards.clear();
    mainFrame.clearBoard();

    Collections.shuffle(cardIcons);

    mazeIcons = new LinkedList<>(cardIcons);

    cpuCards.forEach(cardButton -> cardButton.setCardImage(mazeIcons.removeFirst()));
    playerCards.forEach(cardButton -> cardButton.setCardImage(mazeIcons.removeFirst()));

    for(int i = 0; i < 4; i++) {
      CardButton card = new CardButton(bg);
      card.setCardImage(mazeIcons.removeFirst());
      card.addActionListener(this::playerClickOnBoardCard);
      mainFrame.addBoardCard(card, false);
    }
    mainFrame.updateScorePlayer(scorePlayer);
    mainFrame.updateScoreCpu(scoreCpu);
    mainFrame.refresh();
  }

  private void deal() {
    cpuCards.forEach(cardButton -> cardButton.setCardImage(mazeIcons.removeFirst()));
    mainFrame.setCpuCards(cpuCards);

    playerCards.forEach(cardButton -> cardButton.setCardImage(mazeIcons.removeFirst()));
    mainFrame.setPlayerCards(playerCards);
    mainFrame.refresh();
    dealButton.setEnabled(false);
  }

  private CardButton createPlayerCard(ImageIcon bg) {
    CardButton card = new CardButton(bg);
    card.addActionListener(this::playerClickOnCard);
    return card;
  }

  private void playerClickOnCard(ActionEvent actionEvent) {
    CardButton card = (CardButton) actionEvent.getSource();
    if (selectedPlayerCards.size() > 0) {
      final int sum = getSumOfSelectedBoardCards();
      if (card.getNumber() + sum == 15) {
        winEscoba(card);
      } else {
        mainFrame.removePlayerCard(card);
        selectedPlayerCards.forEach(CardButton::onCardClick);
        selectedPlayerCards.clear();
      }
    } else {
      CardButton newCard = card.clone2();
      newCard.addActionListener(this::playerClickOnBoardCard);
      mainFrame.addBoardCard(newCard);
      mainFrame.removePlayerCard(card);
    }


    DelayedAction.delay(1000, () -> {
      computerChoice();

      if (mainFrame.hasEmptyPlayerCards()) {
        dealButton.setEnabled(true);
      }
    });
  }

  private void winEscoba(final CardButton card) {
    final boolean isEscoba = selectedPlayerCards.size() == mainFrame.getBoardCards().size();
    List<CardButton> cards = new ArrayList<>(selectedPlayerCards);
    cards.add(card);
    scorePlayer.update(isEscoba ? 1: 0, cards);
    if (isEscoba) {
      JOptionPane.showMessageDialog(mainFrame, "You win a Escoba!", "Escoba", JOptionPane.INFORMATION_MESSAGE);
    }
    mainFrame.removePlayerCard(card);
    mainFrame.removeBoardCards(selectedPlayerCards);
    mainFrame.updateScorePlayer(scorePlayer);
    mainFrame.refresh();
    selectedPlayerCards.clear();
  }

  private int getSumOfSelectedBoardCards() {
    return getSelectedBoardCardNumbers().stream()
            .mapToInt(Integer::intValue)
            .sum();
  }

  private void playerClickOnBoardCard(ActionEvent actionEvent) {
    CardButton card = (CardButton) actionEvent.getSource();
    card.onCardClick();
    if (card.isSelected()) {
      selectedPlayerCards.add(card);
    } else {
      selectedPlayerCards.remove(card);
    }
  }

  private void mazeClick(ActionEvent actionEvent) {
    if (mazeIcons.isEmpty()) {
      final long totalCPU = scoreCpu.getTotal(scorePlayer);
      final long totalPlayer = scorePlayer.getTotal(scoreCpu);
      String msg = totalCPU > totalPlayer ? "You Lose!" : "You win!";
      JOptionPane.showMessageDialog(mainFrame, "No more cards! - " + msg, "Game Over", JOptionPane.WARNING_MESSAGE);
      start();
    }
    deal();
  }

  private void computerChoice() {
    List<CardButton> cpuCards = mainFrame.getCpuCards();
    List<CardButton> boardCards = mainFrame.getBoardCards();
    List<Integer> cards = boardCards.stream()
        .map(CardButton::getNumber)
        .sorted().collect(Collectors.toList());

    List<Integer> selectedCards = new ArrayList<>();
    CardButton selectedCard = null;
    for (CardButton c: cpuCards) {
      List<Integer> subsetSumCards = choiceStrategy.choiceCards(cards,15 - c.getNumber());
      if (subsetSumCards.size() > selectedCards.size()) {
        selectedCards = subsetSumCards;
        selectedCard = c;
      }
    }

    Map<Integer, List<CardButton>> map = boardCards.stream()
        .collect(Collectors.groupingBy(CardButton::getNumber));

    List<CardButton> result = new ArrayList<>();
    selectedCards.forEach(sc -> {
      List<CardButton> cardButtons = map.get(sc);
      if (cardButtons.size() == 1) {
        result.add(cardButtons.get(0));
      } else {
        CardButton card = cardButtons.stream()
            .filter(c -> c.getPalo().equals("O"))
            .findFirst()
            .orElse(cardButtons.get(0));
        result.add(card);
      }
    });

    if (result.isEmpty()) {
      CardButton card = cpuCards.get(0);
      CardButton newCard = card.clone2();
      newCard.addActionListener(this::playerClickOnBoardCard);
      mainFrame.addBoardCard(newCard, false);
      mainFrame.removeCpuCard(card);
    } else {
      final boolean isEscoba = result.size()  == boardCards.size();
      scoreCpu.update(isEscoba ? 1 : 0, result);
      mainFrame.removeCpuCard(selectedCard);
      mainFrame.removeBoardCards(result);
    }

    mainFrame.updateScoreCpu(scoreCpu);
    mainFrame.refresh();
  }

  private List<Integer> getSelectedBoardCardNumbers() {
    return mainFrame.getBoardCards().stream()
        .filter(AbstractButton::isSelected)
        .map(CardButton::getNumber)
        .collect(Collectors.toList());
  }

}
