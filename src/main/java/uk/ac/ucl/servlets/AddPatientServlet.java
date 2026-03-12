package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/addPatient")
public class AddPatientServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Model model = Model.getInstance();

        Map<String, String> values = new LinkedHashMap<>();
        for (String col : model.getDataFrame().getColumnNames()) {
            String value = req.getParameter(col);
            values.put(col, value != null ? value : "");
        }

        model.addPatient(values);
        res.sendRedirect("/patients");
    }
}
