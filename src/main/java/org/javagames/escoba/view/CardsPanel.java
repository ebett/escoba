package org.javagames.escoba.view;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.javagames.escoba.view.MainFrame.BOARD_BG_COLOR;

public class CardsPanel extends JPanel {
    CardsPanel() {
        super(new FlowLayout(FlowLayout.CENTER, 10, 20));
        setBackground(BOARD_BG_COLOR);
        add(Box.createVerticalStrut(120));
    }

    public void addCards(final List<CardButton> cardButtons) {
        for (CardButton c: cardButtons) {
            add(c);
        }
    }

    public void addCard(final CardButton cardButton) {
        add(cardButton);
    }

    public List<CardButton> getCards() {
        return Arrays.stream(getComponents())
                .filter(CardButton.class::isInstance)
                .map(CardButton.class::cast)
                .collect(Collectors.toList());
    }

    public boolean isEmpty() {
        return Arrays.stream(getComponents())
                .filter(CardButton.class::isInstance)
                .findAny().isEmpty();
    }
}