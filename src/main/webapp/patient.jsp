<%@ page import="uk.ac.ucl.model.DataFrame" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    DataFrame dataFrame = (DataFrame) request.getAttribute("dataFrame");
    int row = (int) request.getAttribute("row");
%>

<html>
<head>
    <title>Patient Details</title>
    <jsp:include page="/meta.jsp"/>
</head>
<body>
<main>
    <a href="/patients">← All patients</a>
    <h1 class="my-4">Patient Info</h1>
    <div class="container row">
        <% for (String col : dataFrame.getColumnNames()) { %>
        <div class="col-md-6 mb-2">
            <strong><%= col %></strong>: <%= dataFrame.getValue(col, row) %>
        </div>
        <% } %>
    </div>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
</body>
</html>