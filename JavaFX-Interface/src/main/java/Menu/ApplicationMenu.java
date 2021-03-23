package Menu;

import javax.swing.*;
import java.awt.*;

public class ApplicationMenu {

    private final JMenuBar menuBar;
    private final JMenuBar emptyMenuBar;
    private final JMenu boardMenu;
    private final JMenuItem newBoard;
    private final JMenu ticketsMenu;
    private final JMenuItem allTickets;
    private final JMenuItem openedTickets;
    private final JMenuItem closedTickets;
    private final JMenuItem archivedTickets;
    private final JMenu deconnectionMenu;
    private final JMenuItem deconnection;

    public ApplicationMenu() {
        this.menuBar = new JMenuBar();
        this.emptyMenuBar = new JMenuBar();

        this.boardMenu = new JMenu("Tableaux");
        this.newBoard = new JMenuItem("Nouveau tableau");
        this.boardMenu.add(newBoard);
        //TODO ajouter les tableaux dynamiquement
        this.menuBar.add(boardMenu);

        //menu tickets
        this.ticketsMenu = new JMenu("Tickets");
        this.allTickets = new JMenuItem("Tous les tickets");
        this.ticketsMenu.add(allTickets);
        this.openedTickets = new JMenuItem("Tickets ouverts");
        this.ticketsMenu.add(openedTickets);
        this.closedTickets = new JMenuItem("Tickets fermés");
        this.ticketsMenu.add(closedTickets);
        this.archivedTickets = new JMenuItem("Tickets archivés");
        this.ticketsMenu.add(archivedTickets);
        this.menuBar.add(ticketsMenu);

        //menu Deconnexion
        this.deconnectionMenu = new JMenu("Deconnexion");
        this.deconnection = new JMenuItem("Deconnexion");
        this.deconnectionMenu.add(deconnection);
        this.menuBar.add(deconnectionMenu);
    }

    public JMenuBar getmenuBar() {
        return this.menuBar;
    }

    public JMenuBar getEmptyMenuBar() {
        return this.emptyMenuBar;
    }
}
