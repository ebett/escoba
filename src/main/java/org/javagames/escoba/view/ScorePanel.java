package org.javagames.escoba.view;

import org.javagames.escoba.core.Score;

import javax.swing.*;
import java.awt.*;

import static org.javagames.escoba.view.MainFrame.BOARD_BG_COLOR;

/**
 * Panel to display the scores for player and CPU.
 */
public class ScorePanel extends JPanel {

    private static final Font FONT_TITLE = new Font("Serif", Font.ITALIC, 30);
    private static final Font FONT_SCORE = new Font("Serif", Font.BOLD, 18);

    private JLabel escobasPlayerLbl;
    private JLabel cardsPlayerLbl;
    private JLabel goldensPlayerLbl;
    private JLabel sevensPlayerLbl;

    private JLabel escobasCpuLbl;
    private JLabel cardsCpuLbl;
    private JLabel goldensCpuLbl;
    private JLabel sevensCpuLbl;

    public ScorePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(BOARD_BG_COLOR);
        setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel title = new JLabel("Escoba of 15");
        title.setFont(FONT_TITLE);
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);

        add(Box.createVerticalStrut(20));

        add(createLabel("Player Score:")).setForeground(Color.ORANGE);

        escobasPlayerLbl = createLabel("Escobas: 0");
        add(escobasPlayerLbl);

        sevensPlayerLbl = createLabel("Sevens: 0");
        add(sevensPlayerLbl);

        cardsPlayerLbl = createLabel("Cards: 0");
        add(cardsPlayerLbl);

        goldensPlayerLbl = createLabel("Goldens: 0");
        add(goldensPlayerLbl);

        add(Box.createVerticalStrut(40));

        add(createLabel("CPU Score:")).setForeground(Color.ORANGE);
        escobasCpuLbl = createLabel("Escobas: 0");
        add(escobasCpuLbl);

        sevensCpuLbl = createLabel("Sevens: 0");
        add(sevensCpuLbl);

        cardsCpuLbl = createLabel("Cards: 0");
        add(cardsCpuLbl);

        goldensCpuLbl = createLabel("Goldens: 0");
        add(goldensCpuLbl);

        add(Box.createVerticalStrut(40));
    }

    private JLabel createLabel(final String title) {
        JLabel lbl = new JLabel(title);
        lbl.setFont(FONT_SCORE);
        lbl.setForeground(Color.WHITE);
        Dimension size = new Dimension(150, 30);
        lbl.setMinimumSize(size);
        lbl.setPreferredSize(size);
        lbl.setMaximumSize(size);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    public void setScorePlayer(final Score score) {
        if (score == null) return;
        escobasPlayerLbl.setText(String.format("Escobas: %d", score.getEscobas()));
        cardsPlayerLbl.setText(String.format("Cards: %d", score.getCards()));
        goldensPlayerLbl.setText(String.format("Goldens: %d", score.getGolden()));
        sevensPlayerLbl.setText(String.format("Sevens: %d", score.getSevens()));
    }

    public void setScoreCpu(final Score score) {
        if (score == null) return;
        escobasCpuLbl.setText(String.format("Escobas: %d", score.getEscobas()));
        cardsCpuLbl.setText(String.format("Cards: %d", score.getCards()));
        goldensCpuLbl.setText(String.format("Goldens: %d", score.getGolden()));
        sevensCpuLbl.setText(String.format("Sevens: %d", score.getSevens()));
    }
}