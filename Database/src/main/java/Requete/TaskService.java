package Requete;

import Models.Liste;
import Models.Task;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.http.HttpResponse;

public class TaskService {

    private final DatabaseService databaseService;

    private static String getTask = "task";
    private static String getTasksFromList ="task/list";

    public TaskService(User user) {
        this.databaseService = new DatabaseService(user);
    }


    public Task[] getTasksFromList (Body body) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body,getTasksFromList);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), Task[].class);
        }
        return new Task[0];
    }


    public boolean deleteTask(Body body) {
        return databaseService.DeleteRequest(body,getTask);
    }
}