package javaFXInterface.controllers;

import Enum.InterfaceCode;
import javafx.stage.Stage;

import java.io.IOException;

public class ContentPanelController {

    public static void setContentPaneByInterfaceCode(InterfaceCode interfaceCode, Stage window) throws IOException {
        ConnectionSBController connectionSBController = new ConnectionSBController();
        switch (interfaceCode) {
            case CONNECTION -> {
                connectionSBController.switchToUML("/Connection.fxml", window);
            }
            case INSCRIPTION -> {
            }
            case FORGOT_PASSWORD -> {
            }
        }
    }

}
