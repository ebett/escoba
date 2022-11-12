package org.javagames.escoba.core.strategy;

import java.util.List;

public interface ChoiceStrategy {

    List<Integer> choiceCards(List<Integer> cards, int sum);
}
