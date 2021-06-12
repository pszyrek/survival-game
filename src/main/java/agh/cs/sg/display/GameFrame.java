package agh.cs.sg.display;

import agh.cs.sg.Game;
import agh.cs.sg.GameConfiguration;

import javax.swing.*;
import java.awt.event.*;

public class GameFrame extends JFrame implements ActionListener {
    Game game;

    private final JMenuBar menuBar;
    private final JMenuItem start;
    private final JMenuItem pause;
    private final JMenuItem stats;
    private final GamePanel panel;
    private GameStatsPopup gameStatsPopup;

    public boolean isPaused;

    private final int width;
    private final int height;

    public GameFrame(Game game) {
        super(GameConfiguration.title);
        this.game = game;

        this.width = GameConfiguration.width * GameConfiguration.tileSize;
        this.height = GameConfiguration.height * GameConfiguration.tileSize;
        int tileSize = GameConfiguration.tileSize;

        JMenu execute = new JMenu("Execute");

        menuBar = new JMenuBar();
        menuBar.add(execute);

        pause = new JMenuItem("Pause");
        pause.addActionListener(this);

        start = new JMenuItem("Continue");
        start.addActionListener(this);

        execute.add(pause);
        execute.add(start);

        JMenu show = new JMenu("Show");
        menuBar.add(show);

        stats = new JMenuItem("Stats");
        stats.addActionListener(this);

        show.add(stats);

        this.panel = new GamePanel(width, height, tileSize, game.world, this);

        this.add(this.panel);

        createDisplay();
    }

    private void createDisplay() {
        this.setVisible(true);
        this.setSize(width, height + menuBar.getHeight());
        this.setResizable(false);
        this.setJMenuBar(menuBar);
        this.pack();

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                game.exit();
                GameFrame.super.dispose();
            }
        });
    }

    public GamePanel getGamePanel() {
        return this.panel;
    }

    public void updateStatsPopup() {
        if(this.gameStatsPopup != null) {
            this.gameStatsPopup.update();
        }
    }

    public void removePopup() {
        this.gameStatsPopup = null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == pause) {
            game.pause();
            this.isPaused = true;
        }

        if(e.getSource() == start) {
            game.resume();
            this.isPaused = false;
        }

        if(e.getSource() == stats) {
            gameStatsPopup = new GameStatsPopup(game, this);
        }
    }
}
