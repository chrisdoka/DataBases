package Servlets;

import Databases.ClientRecordings;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "Servlet", value = "/Servlet")
public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Read JSON data from the request
            BufferedReader reader = request.getReader();
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            String json = jsonBuilder.toString();
            System.out.println("Received JSON data: " + json);

            // Parse JSON and handle it (e.g., store in a database)
            JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
            ClientRecordings clientRecordings = new ClientRecordings();
            clientRecordings.addClientFromJSON(json);

            // Send a success response to the client
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("Registration Successful");
        } catch (Exception e) {
            // Log the error using System.out
            System.err.println("Error processing registration request");
            e.printStackTrace(System.err);

            // Respond with an appropriate error message to the client
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal Server Error");
        }
    }
}
