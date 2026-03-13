package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.DataFrame;
import uk.ac.ucl.model.Model;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/patients")
public class PatientsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Model model = Model.getInstance();
        model.loadData("data/patients100.csv");
        DataFrame dataFrame = model.getDataFrame();

        String query = req.getParameter("search");
        String sortCol = req.getParameter("sort");
        String sortDir = req.getParameter("direction");
        String error = req.getParameter("error");

        if (query == null) {
            query = "";
        }

        if (sortDir == null || !List.of("asc", "desc").contains(sortDir)) {
            sortDir = "desc";
        }

        if (error == null) {
            error = "";
        }

        req.setAttribute("dataFrame", dataFrame);
        req.setAttribute("search", query);
        req.setAttribute("visibleColumns", Model.VISIBLE_COLUMNS);
        req.setAttribute("visibleRows", model.getRows(query, sortCol, sortDir));
        req.setAttribute("error", URLDecoder.decode(error, StandardCharsets.UTF_8));
        req.getRequestDispatcher("patients.jsp").forward(req, res);
    }
}
