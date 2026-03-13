<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String id = (String) request.getAttribute("id");
    @SuppressWarnings("unchecked")
    Map<String, String> data = (Map<String, String>) request.getAttribute("data");
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
        <form action="/editPatient/<%= id %>" method="post">
            <% for (Map.Entry<String, String> entry : data.entrySet()) {
                String col = entry.getKey();
                String value = entry.getValue();
            %>
            <div class="mb-2">
                <label class="form-label"><%= col %></label>
                <input class="form-control" type="text" name="<%= col %>" value="<%= value %>" <%= col.equals("ID") ? "readonly" : "" %>>
            </div>
            <% } %>
            <button class="btn btn-warning" type="submit">Save Edits</button>
            <a href="/deletePatient/<%= id %>" class="btn btn-danger">Delete</a>
        </form>
    </div>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
</body>
</html>