package Servlets;

import Databases.ClientRecordings;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.JSON_Converter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "addNewVehicle", value = "/addNewVehicle")
public class addNewVehicle extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Read JSON data from the request
        try (BufferedReader reader = request.getReader()) {
            JSON_Converter jc = new JSON_Converter();
            String json = jc.getJSONFromAjax(reader);

            System.out.println("Received JSON data: " + json);

            // Convert JSON to JsonObject using Gson
            JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);

            // Assuming you have a ClientRecordings class
            ClientRecordings clientRecordings = new ClientRecordings();

            try {
                // Assuming you have a method to add a client from JSON
                clientRecordings.addClientFromJSON(json);
                sendJsonResponse(response, 200, "Registration successful");
            } catch (Exception e) {
                e.printStackTrace();
                sendJsonResponse(response, 500, "Internal Server Error");
            }
        } catch (IOException e) {
            e.printStackTrace();
            sendJsonResponse(response, 400, "Bad Request");
        }
    }

    private void sendJsonResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);

        try (PrintWriter out = response.getWriter()) {
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("status", status);
            jsonResponse.addProperty("message", message);
            out.print(jsonResponse);
        }
    }
}
