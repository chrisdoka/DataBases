package Servlets;

import Databases.CarRecordings;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.JSON_Converter;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "addCarServlet", value = "/addCarServlet")
public class addCarServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSON_Converter jc = new JSON_Converter();
         String json = jc.getJSONFromAjax(request.getReader());
        System.out.println(json);
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        CarRecordings est = new CarRecordings();
        
         try {
            est.addCarFromJSON(json);
        } catch (Exception e) {
            System.err.println("add Car Exception");
            System.err.println(e.getMessage());
        }
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(200);
        } catch (Exception e) {
            System.err.println("add Car Servlet exception! ");
            System.err.println(e.getMessage());
            response.setStatus(403);
        }
        System.out.println(response.getWriter());
    }
}
