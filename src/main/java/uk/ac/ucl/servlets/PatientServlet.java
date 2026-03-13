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
import java.util.Map;

@WebServlet("/patient/*")
public class PatientServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            res.sendRedirect("/patients");
            return;
        }

        Model model = Model.getInstance();
        String id = pathInfo.substring(1);

        try {
            Map<String, String> data = model.getPatientData(id);
            req.setAttribute("id", id);
            req.setAttribute("data", data);
            req.getRequestDispatcher("/patient.jsp").forward(req, res);
        } catch (IllegalArgumentException e) {
            String encoded = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            res.sendRedirect("/patients?error=" + encoded);
        }
    }
}
