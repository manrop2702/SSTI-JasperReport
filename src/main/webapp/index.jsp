<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>

<br><br>
<form action="upload" method="post" enctype="multipart/form-data">
    Select File: <input type="file" name="file" />
    <input type="submit" value="Upload" />
</form>
</body>
</html>