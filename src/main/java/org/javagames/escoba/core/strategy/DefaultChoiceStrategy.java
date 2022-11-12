package org.javagames.escoba.core.strategy;

import java.util.ArrayList;
import java.util.List;

public final class DefaultChoiceStrategy implements ChoiceStrategy {

  @Override
  public List<Integer> choiceCards(List<Integer> cards, int sum) {
    return subsetSum(cards, sum);
  }

  // Returns true if there is a subset of cards[] with sun equal to given sum
  private static List<Integer> subsetSum(List<Integer> cards, int sum) {
    final int n = cards.size();

    // The value of subset[i][j] will be
    // true if there is a subset of
    // cards[0..j-1] with sum equal to i
    boolean subset[][] = new boolean[sum + 1][n + 1];

    // If sum is 0, then answer is true
    for (int i = 0; i <= n; i++) {
      subset[0][i] = true;
    }

    // If sum is not 0 and cards is empty, then answer is false
    for (int i = 1; i <= sum; i++) {
      subset[i][0] = false;
    }

    // Fill the subset table in bottom up manner
    for (int i = 1; i <= sum; i++) {
      for (int j = 1; j <= n; j++) {
        subset[i][j] = subset[i][j - 1];
        if (i >= cards.get(j - 1)) {
          subset[i][j] =  subset[i][j] || subset[i - cards.get(j - 1)][j - 1];
        }
      }
    }

    List<Integer> result = new ArrayList<>();
    if (subset[sum][n]) {
      search(subset, cards, result, sum, n);
    }
    return result;
  }

  /**
   * Searches the solution containing more cards.
   *
   * @param subset subset matrix.
   * @param cards card list.
   * @param result list of cards for the solution.
   * @param i current row position
   * @param j current column position
   */
  private static void search(boolean subset[][], List<Integer> cards, List<Integer> result, int i, int j) {
    if (i> 0 && j > 0) {
      List<Integer> ra = new ArrayList<>();
      List<Integer> rb = new ArrayList<>();
      if (subset[i][j - 1]) {
        search(subset, cards, ra, i, j-1);
      }
      if (i >= cards.get(j - 1)) {
        if (subset[i - cards.get(j - 1)][j - 1]) {
          search(subset, cards, rb, i - cards.get(j - 1), j-1);
          rb.add(cards.get(j - 1));
        }
      }
      result.addAll(ra.size() > rb.size() ?  ra : rb);
    }
  }
}
