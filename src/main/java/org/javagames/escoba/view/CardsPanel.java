package org.javagames.escoba.view;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.javagames.escoba.view.MainFrame.BOARD_BG_COLOR;

/**
 * Panel to display card buttons in the Escoba game.
 */
public final class CardsPanel extends JPanel {

    public CardsPanel() {
        super(new FlowLayout(FlowLayout.CENTER, 10, 20));
        setBackground(BOARD_BG_COLOR);
        add(Box.createVerticalStrut(120));
    }

    /**
     * Adds multiple card buttons to the panel.
     * @param cardButtons List of CardButton to add.
     */
    public void addCards(final List<CardButton> cardButtons) {
        if (cardButtons == null) return;
        for (CardButton c : cardButtons) {
            if (c != null) {
                add(c);
            }
        }
    }

    /**
     * Adds a single card button to the panel.
     * @param cardButton CardButton to add.
     */
    public void addCard(final CardButton cardButton) {
        if (cardButton != null) {
            add(cardButton);
        }
    }

    /**
     * Returns a list of CardButton components currently in the panel.
     * @return List of CardButton.
     */
    public List<CardButton> getCards() {
        return Arrays.stream(getComponents())
            .filter(CardButton.class::isInstance)
            .map(CardButton.class::cast)
            .collect(Collectors.toList());
    }

    /**
     * Checks if the panel contains any CardButton components.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return getCards().isEmpty();
    }
}