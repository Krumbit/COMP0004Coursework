<%@ page import="uk.ac.ucl.model.DataFrame" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  DataFrame dataFrame = (DataFrame) request.getAttribute("dataFrame");
  @SuppressWarnings("unchecked")
  List<String> columnNames = (List<String>) request.getAttribute("visibleColumns");
  @SuppressWarnings("unchecked")
  List<Integer> rows = (List<Integer>) request.getAttribute("visibleRows");

  String search = request.getParameter("search");
  String searchParam = (search != null && !search.isEmpty()) ? "&search=" + search : "";

%>

<html>
  <head>
    <title>Patient Data</title>
    <jsp:include page="/meta.jsp"/>
  </head>
  <body>
    <main class="container-sm px-5">
      <h1 class="mb-4">Patient Data</h1>

      <form class="hstack gap-2 align-self-center w-75">
        <div class="vstack">
          <input
            aria-label="Search"
            id="search"
            type="search"
            name="search"
            value="<%= search != null ? search : "" %>"
            placeholder="Enter a search query..."
            class="form-control"
          >
        </div>
        <button type="submit" class="btn btn-primary">Search</button>
        <a href="/patients" class="btn btn-secondary square h-100">⟳</a>
      </form>

      <div class="w-100">
        <span>Results: <%=rows.size() %></span>
        <div class="overflow-x-scroll w-100">
          <table class="table table-hover table-bordered">
            <thead>
            <tr>
              <% for (String col : columnNames) { %>
              <th scope="col">
                <%= col%>
                <div class="sort-buttons">
                  <a
                    class="btn btn-sm btn-light square h-100"
                    href="/patients?sort=<%=col %>&direction=asc<%= searchParam %>"
                  >↑</a>
                  <a
                    class="btn btn-sm btn-light square h-100"
                    href="/patients?sort=<%=col %>&direction=desc<%= searchParam %>"
                  >↓</a>
                </div>
              </th>
              <% } %>
            </tr>
            </thead>
            <tbody>
            <% for (int row : rows) { %>
            <tr>
              <% for (String col : columnNames) { %>
              <td><%= dataFrame.getValue(col, row)%></td>
              <% } %>
            </tr>
            <% } %>
            </tbody>
          </table>
        </div>
      </div>
    </main>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
  </body>
</html>
