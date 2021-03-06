package CLIInterface.Models;

import CLIInterface.Controllers.BoardController;
import CLIInterface.Menu.BoardMenu;
import Models.Board;
import Models.Liste;
import Services.Body;
import Services.ListService;
import Services.UserService;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class BoardModel {

    public static Scanner clavier = new Scanner(System.in);

    /**
     * affiche les listes et les différentes actions possibles
     * @param board
     * @param window
     * @param user
     * @throws IOException
     */
    public static void printBoardListsAndActionMenu(Board board, Stage window, UserService user) throws IOException {
        ListService listService = new ListService(user);

        Body body = new Body();
        body.addValueToBody("", String.valueOf(board.boardId));
        Liste[] lists = listService.getListesFromBoard(body);

        int value = -1;

        do {
            try {
                List<String> menu = new ArrayList<>();
                menu.add("Tableau : " + board.boardName);
                int i = 1;
                for (Liste list : lists) {
                    menu.add(i + ". " + list.listName);
                    i += 1;
                }
                menu.add(i + ". Modifier le tableau");
                i += 1;
                menu.add(i + ". Supprimer le tableau");
                i += 1;
                menu.add(i + ". Ajouter une liste");
                i += 1;
                menu.add(i + ". Retour");

                for (String chaine : menu) {
                    System.out.println(chaine);
                }
                value = Integer.parseInt(clavier.next());
                if (value < 1 || value > i) {
                    System.out.println("Veuillez saisir un nombre présent dans le menu");
                    value = -1;
                }
            } catch (Exception e) {
                System.out.println("Veuillez saisir un numérique");
            }
        } while (value == -1);
        switchBoardListsAndActionMenu(value, window, user, board, lists);
    }

    /**
     * dirige vers la bonne action selon la valeur choisie
     * @param value
     * @param window
     * @param user
     * @param board
     * @param lists
     * @throws IOException
     */
    public static void switchBoardListsAndActionMenu(int value, Stage window, UserService user, Board board, Liste[] lists) throws IOException {
        if (value == lists.length + 4) {
            BoardMenu boardMenu = new BoardMenu();
            boardMenu.printBoardMenu(window, user);
        } else if (value == lists.length + 1) {
            BoardModel.updateBoardTreatment(window, user, board);
        } else if (value == lists.length + 2) {
            BoardModel.deleteBoardTreatment(window, user, board);
        } else if (value == lists.length + 3) {
            ListeModel.addListTreatment(window, user, board);
        } else {
            Liste list = lists[value - 1];

            ListeModel.printTaskListsAndActionMenu(list, window, user, board);
        }
    }

    /**
     * affichage du traitement d'un ajout de tableau
     * @param window
     * @param user
     * @throws IOException
     */
    public static void addBoardTreatment(Stage window, UserService user) throws IOException {
        String name = "";
        do {
            System.out.println("Nom du nouveau tableau :");
            name = clavier.nextLine();

            if (name.equals("")) {
                System.out.println("Veuillez saisir un nom");
            }
        } while (name.equals(""));

        BoardController boardController = new BoardController(user);
        boardController.addBoard(name);

        BoardMenu boardMenu = new BoardMenu();
        boardMenu.printBoardMenu(window, user);
    }

    /**
     * affichage du traitement d'une mise à jour de tableau
     * @param window
     * @param user
     * @param board
     * @throws IOException
     */
    public static void updateBoardTreatment(Stage window, UserService user, Board board) throws IOException {

        System.out.println("Nom du tableau :");
        clavier.nextLine();
        String name = clavier.nextLine();

        if (!name.equals("")) {
            BoardController boardController = new BoardController(user);
            board = boardController.updateBoard(board.boardId, name);
        }

        BoardModel.printBoardListsAndActionMenu(board, window, user);
    }

    /**
     * affichage du traitement de délétion de tableau
     * @param window
     * @param user
     * @param board
     * @throws IOException
     */
    public static void deleteBoardTreatment(Stage window, UserService user, Board board) throws IOException {

        String validation = "";
        do {
            System.out.println("Voulez-vous vraiment supprimer ce tableau (o/n) :");
            clavier.nextLine();
            validation = clavier.nextLine();

            if (!validation.toLowerCase(Locale.ROOT).equals("o") && !validation.toLowerCase(Locale.ROOT).equals("n")) {
                System.out.println("Veuillez saisir une valeur valide (o/n)");
                validation = "";
            }
        } while (validation.equals(""));

        if (validation.toLowerCase(Locale.ROOT).equals("o")) {
            BoardController boardController = new BoardController(user);
            boardController.deleteBoard(board.boardId);

            BoardMenu boardMenu = new BoardMenu();
            boardMenu.printBoardMenu(window, user);
        } else {
            BoardModel.printBoardListsAndActionMenu(board, window, user);
        }
    }
}
