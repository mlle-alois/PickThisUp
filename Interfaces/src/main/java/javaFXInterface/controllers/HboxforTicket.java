package javaFXInterface.controllers;


import Models.StatusModel;
import Models.Ticket;
import Models.UserModel;
import Requete.Body;
import Requete.TaskService;
import Requete.TicketsService;
import Requete.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

 import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;
import java.text.DateFormat;
import java.util.List;

public class HboxforTicket {
    private Ticket ticket;
    private User user;
    private HBox hbox;
    private List<GridPane> gridPanes;
    private BorderPaneController borderPaneController;
    private StatusModel[] status;
    private TicketsService ticketsService;

    @SneakyThrows
    public HboxforTicket(Ticket ticket, User user, BorderPaneController borderPaneController){
        this.ticket = ticket;
        this.user = user;
        this.borderPaneController = borderPaneController;
        this.hbox = new HBox();
        this.ticketsService = new TicketsService(user);
        fetchStatus();
    }

    public HBox getFilledHbox(){

        fillHbox();
        return this.hbox;
    }

    private void fillHbox(){
        setHboxShape();
        addName();
        addCreationDate();
        addMember();
        addStatus();
        addDetailsButton();
        addArchiveAndClotureVerticalButtons();

    }

    private void addName(){
        Label label = new Label(ticket.ticketName);
        setStrictSizeToLabel(label,120);
        hbox.getChildren().add(label);
        addSeparator();
    }

    private void addDetailsButton(){
        Button button = new Button("Details");

        EventHandler<ActionEvent> buttonDetailsHandler = event -> {

            if(event.getSource() == button) {
                Stage newStage;
                Parent root = null;
                Body body;
                newStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddTicketToBoard.fxml"));

                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                AddTicketController popupController = loader.getController();
                newStage.setScene(new Scene(root));
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.initOwner(button.getScene().getWindow());
                newStage.setTitle("PickThisUp");
                newStage.getIcons().add(new Image("/logo.PNG"));
                newStage.showAndWait();
                // add the task to the database
              /*  body = new Body();
                body.addValueToBody("name",popupController.getName());
                body.addValueToBody("description",popupController.getDescription());
                body.addValueToBody("listId",String.valueOf(liste.listId));
                TaskService taskService = new TaskService(user);
                try {
                    taskService.addTask(body);
                    borderPaneController.setBorderPane();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }*/
                event.consume();
            }
        };

        button.setOnAction(buttonDetailsHandler);
        hbox.getChildren().add(button);
        addSeparator();
    }
    private void addArchiveAndClotureVerticalButtons(){
        Button archiveButton = new Button("Archiver");
        Button clotureButton = new Button("Cloturer");

        hbox.getChildren().addAll(archiveButton,clotureButton);
    }

    private void addSeparator() {
        Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);
        separator.setStyle("-fx-max-width: 2;");
        hbox.getChildren().add(separator);
    }

    private void addStatus() {
        Label statusLibelle = new Label("Statut :");

        Label status = new Label(getCurrentStatusLibelle());
        status.setStyle("-fx-font-weight: bold");
        hbox.getChildren().addAll(statusLibelle,status);
        addSeparator();

    }
    @SneakyThrows
    private void addMember(){
        Label membersLibelle = new Label("Membres :");

        Label finalMemberLabel = new Label(getMembersString());
        setStrictSizeToLabel(finalMemberLabel,120);

        hbox.getChildren().addAll(membersLibelle,finalMemberLabel);
        addSeparator();
    }
    @SneakyThrows
    private String getMembersString(){
        StringBuilder membersString = new StringBuilder();

        Body body = new Body();
        UserModel[] members = ticketsService.getMembersByTicketId(body, ticket.ticketId);
        for (int i = 0; i < members.length; i++){
            membersString.append(members[i].firstname);
            if(i != members.length-1)
                membersString.append(",");
        }
        return membersString.toString();
    }

    private String getCurrentStatusLibelle() {
        String currentStatus = null;
        for(int i = 0; i < status.length; i++){
            if (status[i].statusId == ticket.statusId)
                currentStatus = status[i].statusLibelle;
        }
        return currentStatus;
    }

    private void fetchStatus() throws JsonProcessingException {
        Body body = new Body();
        status = ticketsService.getTicketsStatus(body);
    }

    private void addCreationDate(){
        Label dateOuverture = new Label("Date d'ouverture :");

        DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
                DateFormat.SHORT,
                DateFormat.SHORT);

        Label label = new Label(shortDateFormat.format(ticket.ticketCreationDate));
        hbox.getChildren().addAll(dateOuverture,label);
        addSeparator();
    }
    private void setHboxShape(){
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10,10,10,10));
        hbox.setStyle("-fx-border-color: black; -fx-background-color: lightgray;");

    }

    private void setStrictSizeToLabel(Label label,int size){
        label.setMinWidth(size);
    }


}

