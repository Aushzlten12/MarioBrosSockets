package src.main.mariobros.gui.server;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import src.main.mariobros.game.ServerGame;
import src.main.mariobros.net.Server;

public class ServerMainFrame extends JFrame {
    private Server server;

    public ServerMainFrame() {
        super("Server Manager");

        add(new ServerMenuPanel());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                if (server != null) {
                    server.closeServer();
                }
                System.exit(0);
            }
        });

        pack();
        setVisible(true);
    }

    public void startServer(final int port) {
        getContentPane().removeAll();

        System.out.println("Starting server.");

        server = new Server(new ServerGame(), port);

        add(new ServerRunningPanel(server));

        pack();
        revalidate();
        repaint();

        server.run();
    }

    public static void main(final String[] args) {
        new ServerMainFrame();
    }
}
