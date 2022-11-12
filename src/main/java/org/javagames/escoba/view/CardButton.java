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
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (cardImage == null && !hidden) return;
    final Image image = (hidden ? backGroundIcon: cardImage).getImage();
    g.drawImage(image, 5, 5, getWidth() - 10, getHeight() - 10, this);
  }

  public void setCardImage(ImageIcon cardImage) {
    this.cardImage = cardImage;
    repaint();
  }

  public void setHidden(boolean hidden) {
    this.hidden = hidden;
  }

  @Override
  public void setIcon(Icon icon) {
    setCardImage((ImageIcon) icon);
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
        return Integer.parseInt(n.substring(0, 1));
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
