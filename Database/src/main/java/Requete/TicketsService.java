package Requete;

import Models.*;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.http.HttpResponse;

public class TicketsService {

    private final DatabaseService databaseService;

    private static final String getTickets = "ticket";
    private static final String getTicket = "ticket/get";
    private static final String getStatus = "ticket/status";
    private static final String getTicketsForStatus = "ticket/getByStatus";

    public TicketsService(User user) {
        this.databaseService = new DatabaseService(user);
    }

    public Ticket[] getTickets(Body body) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body, getTickets);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), Ticket[].class);
        }
        return new Ticket[0];
    }

    public Status[] getStatus(Body body) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body, getStatus);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), Status[].class);
        }
        return new Status[0];
    }

    public Ticket[] getTicketsByStatus(Body body) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body, getTicketsForStatus);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), Ticket[].class);
        }
        return new Ticket[0];
    }
}
