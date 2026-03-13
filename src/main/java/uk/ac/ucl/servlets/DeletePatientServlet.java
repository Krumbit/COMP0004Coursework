package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/deletePatient/*")
public class DeletePatientServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            res.sendRedirect("/patients");
            return;
        }

        Model model = Model.getInstance();
        model.loadData("data/patients100.csv");

        String id = pathInfo.substring(1);

        try {
            model.removePatient(id);
        } catch (IllegalArgumentException e) {
            String encoded = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            res.sendRedirect("/patients?error="+encoded);
            return;
        }

        res.sendRedirect("/patients");
    }
}
