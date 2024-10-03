package src.main.mariobros.gui.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import src.main.mariobros.net.Server;

public class ServerMenuPanel extends JPanel {
    private final JTextField portNumber;

    public ServerMenuPanel() {

        portNumber = new JTextField("" + Server.DEFAULT_PORT_NUMBER);
        add(portNumber);

        add(new JButton("Start Server") {
            {
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        ((ServerMainFrame) ServerMenuPanel.this.getTopLevelAncestor())
                                .startServer(Integer.parseInt(portNumber.getText()));
                    }
                });
            }
        });
    }
}
