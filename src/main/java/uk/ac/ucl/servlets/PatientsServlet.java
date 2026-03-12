package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.DataFrame;
import uk.ac.ucl.model.Model;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        List<Integer> visibleRows;
        if (query != null && !query.trim().isEmpty()) {
            visibleRows = model.searchFor(query);
            req.setAttribute("search", query);
        } else {
            visibleRows = IntStream.range(0, dataFrame.getRowCount()).boxed().collect(Collectors.toList());
            req.setAttribute("search", "");
        }

        if (sortCol != null && !sortCol.trim().isEmpty() && dataFrame.getColumnNames().contains(sortCol.toUpperCase(Locale.ROOT))) {
            if (sortDir == null || !List.of("asc", "desc").contains(sortDir)) {
                sortDir = "desc";
            }

            Comparator<Integer> comparator = Comparator.comparing(row ->
                    dataFrame.getValue(sortCol, row)
            );

            if (sortDir.equals("desc")) {
                comparator = comparator.reversed();
            }

            visibleRows.sort(comparator);
        }

        req.setAttribute("dataFrame", dataFrame);
        req.setAttribute("visibleColumns", Model.VISIBLE_COLUMNS);
        req.setAttribute("visibleRows", visibleRows);
        req.getRequestDispatcher("patients.jsp").forward(req, res);
    }
}
