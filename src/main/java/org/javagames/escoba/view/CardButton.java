package org.javagames.escoba.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Represents a button for a card in the Escoba game.
 */
public class CardButton extends JButton {

  private ImageIcon cardImage;
  private final ImageIcon backGroundIcon;
  private boolean hidden;

  public CardButton(final ImageIcon backGroundIcon) {
    setPreferredSize(new Dimension(100, 200));
    this.backGroundIcon = backGroundIcon;
  }

  public void onCardClick() {
    setSelected(!isSelected());
    if (isSelected()) {
      setBorder(BorderFactory.createLineBorder(Color.YELLOW.darker(), 4, true));
    } else {
      setBorder(null);
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (cardImage == null && !hidden) return;
    final Image image = (hidden ? backGroundIcon : cardImage).getImage();
    g.drawImage(image, 5, 5, getWidth() - 10, getHeight() - 10, this);
  }

  public void setCardImage(final ImageIcon cardImage) {
    this.cardImage = cardImage;
    repaint();
  }

  public void setHidden(final boolean hidden) {
    this.hidden = hidden;
  }

  public boolean isHidden() {
    return hidden;
  }

  @Override
  public void setIcon(final Icon icon) {
    if (icon instanceof ImageIcon) {
      setCardImage((ImageIcon) icon);
    }
  }

  public ImageIcon getCardImage() {
    return cardImage;
  }

  public String getCard() {
    return getNumber() + getPalo();
  }

  public int getNumber() {
    return Optional.ofNullable(cardImage)
        .map(ImageIcon::getDescription)
        .map(n -> {
          if (n.startsWith("A")) return 1;
          if (n.startsWith("S")) return 8;
          if (n.startsWith("C")) return 9;
          if (n.startsWith("R")) return 10;
          try {
            return Integer.parseInt(n.substring(0, 1));
          } catch (NumberFormatException e) {
            return 0;
          }
        }).orElse(0);
  }

  public String getPalo() {
    return Optional.ofNullable(cardImage)
        .map(ImageIcon::getDescription)
        .map(n -> n.substring(1, 2)).orElse("");
  }

  public CardButton clone2() {
    CardButton newCard = new CardButton(backGroundIcon);
    newCard.setCardImage(getCardImage());
    return newCard;
  }
}