package org.javagames.escoba.core;

import org.javagames.escoba.view.CardButton;

import java.util.List;

public class Score {
    private int cards;
    private int golden;
    private int escobas;
    private int sevens;

    void reset() {
        cards = 0;
        golden = 0;
        escobas = 0;
        sevens = 0;
    }

    public int getCards() {
        return cards;
    }

    public int getEscobas() {
        return escobas;
    }

    public int getGolden() {
        return golden;
    }

    public int getSevens() {
        return sevens;
    }

    public long getTotal(Score other) {
        int value = 0;
        if (cards > other.cards) {
            value++;
        }

        if (golden > other.golden) {
            value += 2;
        }

        if (sevens > other.sevens) {
            value++;
        }

        value += escobas;

        return value;
    }

    public void update(final int escobas, final List<CardButton> cards) {
        this.escobas += escobas;
        this.cards += cards.size();
        this.sevens += cards.stream()
                .map(CardButton::getNumber)
                .filter(Integer.valueOf(7)::equals)
                .count();
        this.golden += cards.stream()
                .map(CardButton::getPalo)
                .filter("O"::equals)
                .count();
    }
}
