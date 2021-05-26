package agh.cs.sg.display;

import agh.cs.sg.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Display extends JFrame implements ActionListener {
    Game game;

    private Canvas canvas;
    private JMenuBar menuBar;
    private JMenu execute;
    private JMenuItem start, pause;

    private int width, height;

    public Display(String title, int width, int height, Game game) {
        super(title);

        this.game = game;
        this.width = width;
        this.height = height;
        createDisplay();
    }

    private void createDisplay() {
        menuBar = new JMenuBar();
        execute = new JMenu("Execute");
        pause = new JMenuItem("Pause");
        pause.addActionListener(this);
        start = new JMenuItem("Continue");
        start.addActionListener(this);

        execute.add(pause);
        execute.add(start);

        menuBar.add(execute);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));

        getContentPane().add(canvas);
        super.setVisible(true);
        super.setSize(width, height + menuBar.getHeight());
        super.setResizable(false);
        super.setJMenuBar(menuBar);
        super.pack();

        super.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                game.exit();
                Display.super.dispose();
            }
        });
    }

    public Canvas getCanvas() {
        return canvas;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == pause) {
            game.pause();
        }

        if(e.getSource() == start) {
            game.resume();
        }
    }
}
