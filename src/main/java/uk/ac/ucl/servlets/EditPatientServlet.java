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
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/editPatient/*")
public class EditPatientServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    // Should really be using PATCH but forms only support GET and POST
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            res.sendRedirect("/patients");
            return;
        }

        Model model = Model.getInstance();
        String id = pathInfo.substring(1);

        Map<String, String> values = new LinkedHashMap<>();
        for (String col : model.getDataFrame().getColumnNames()) {
            String value = req.getParameter(col);
            values.put(col, value != null ? value : "");
        }

        try {
            model.editPatient(id, values);
        } catch (IllegalArgumentException e) {
            String encoded = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            res.sendRedirect("/patients?error=" + encoded);
            return;
        }

        res.sendRedirect("/patient/" + id);
    }
}
