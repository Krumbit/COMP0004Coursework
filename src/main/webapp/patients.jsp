<%@ page import="uk.ac.ucl.model.DataFrame" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    DataFrame dataFrame = (DataFrame) request.getAttribute("dataFrame");
    @SuppressWarnings("unchecked")
    List<String> columnNames = (List<String>) request.getAttribute("visibleColumns");
    @SuppressWarnings("unchecked")
    List<Integer> rows = (List<Integer>) request.getAttribute("visibleRows");
    String error = (String) request.getAttribute("error");

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
    <% if (error != null && !error.isEmpty()) { %>
    <div class="alert alert-danger" role="alert"><%= error %></div>
    <% } %>

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
        <a href="/patients" class="btn btn-secondary square h-100 reset-button">⟳</a>
    </form>

    <div class="w-100">
        <div class="d-flex justify-content-between align-items-center my-2">
            <span>Results: <%=rows != null ? rows.size() : 0 %></span>
            <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addPatientModal">
                Add
            </button>
        </div>
        <div class="modal fade" id="addPatientModal" tabindex="-1" aria-labelledby="addPatientModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable">
                <form class="modal-content" action="/addPatient" method="post">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="addPatientModalLabel">Add patient</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <% for (String col : dataFrame.getColumnNames()) {
                            if (col.equals("ID")) continue;
                            boolean isRequired = dataFrame.isColumnRequired(col);
                        %>
                            <div class="mb-3">
                                <label id="<%=col%>" class="form-label" for="<%=col%>">
                                    <%=col%>
                                    <% if (isRequired) { %><span class="text-danger">*</span><%}%>
                                </label>
                                <input type="text" id="<%=col%>" name="<%=col%>" class="form-control" <%= isRequired ? "required" : "" %>>
                            </div>
                        <%}%>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-success">Add</button>
                    </div>
                </form>
            </div>
        </div>
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
                    <th scope="col">DETAILS</th>
                </tr>
                </thead>
                <tbody>
                <% for (int row : rows) { %>
                <tr>
                    <% for (String col : columnNames) { %>
                    <td><%= dataFrame.getValue(col, row)%></td>
                    <% } %>
                    <td>
                        <a class="btn btn-primary btn-sm" href="/patient/<%= dataFrame.getValue("ID", row) %>">View</a>
                    </td>
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
