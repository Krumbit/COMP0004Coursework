<%@ page import="uk.ac.ucl.model.DataFrame" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    DataFrame dataFrame = (DataFrame) request.getAttribute("dataFrame");
    Boolean showSavedAlert = (Boolean) request.getAttribute("showSavedAlert");
    int row = (int) request.getAttribute("row");
%>

<html>
<head>
    <title>Patient Details</title>
    <jsp:include page="/meta.jsp"/>
</head>
<body>
<main>
    <% if (Boolean.TRUE.equals(showSavedAlert)) {%>
    <div class="alert alert-success" role="alert">
        Saved changes!
    </div>
    <%}%>
    <a href="/patients">← All patients</a>
    <h1 class="my-4">Patient Info</h1>
    <div class="container row">
        <form action="/editPatient/<%= dataFrame.getValue("ID", row) %>" method="post">
            <% for (String col : dataFrame.getColumnNames()) { %>
            <div class="mb-2">
                <label class="form-label"><%= col %></label>
                <input class="form-control" type="text" name="<%= col %>" value="<%= dataFrame.getValue(col, row) %>" <%= col.equals("ID") ? "readonly" : "" %>>
            </div>
            <% } %>
            <button class="btn btn-warning" type="submit">Save Edits</button>
        </form>
    </div>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
</body>
</html>