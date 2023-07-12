<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Weather Service</title>
</head>
<body>
    <h1>Weather Service</h1>
    <form method="get" action="forecast">
        <label for="cityInput">Enter city:</label>
        <input type="text" id="cityInput" name="city" required>
        <button type="submit">Get Forecast</button>
    </form>
    <br>
    <form method="get" action="live">
        <label for="cityInput">Enter city:</label>
        <input type="text" id="cityInput" name="city" required>
        <button type="submit">Get Live Data</button>
    </form>
</body>
</html>