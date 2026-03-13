package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.DataFrame;
import uk.ac.ucl.model.Model;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

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
        model.loadData("data/patients100.csv");

        String id = pathInfo.substring(1);
        Optional<Integer> row = model.findById(id);
        if (row.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            req.getRequestDispatcher("/not-found.jsp").forward(req, res);
            return;
        }

        Map<String, String> values = new LinkedHashMap<>();
        for (String col : model.getDataFrame().getColumnNames()) {
            String value = req.getParameter(col);
            values.put(col, value != null ? value : "");
        }

        model.editPatient(row.get(), values);

        req.setAttribute("dataFrame", model.getDataFrame());
        req.setAttribute("row", row.get());
        req.setAttribute("showSavedAlert", true);
        req.getRequestDispatcher("/patient.jsp").forward(req, res);
    }
}
