<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Weather Forecast</title>
<style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }

        th, td {
            padding: 5px;
        }

        .highlight {
            border: 2px solid red;
        }
    </style>
</head>
<body>

	<h1 align="center">~~~ Weather Forecast ~~~</h1>

	<h2 align="center">City : ${city}</h2>

	
	<table align="center">
		<thead>
			<tr>
				<th>Date</th>
				<th>Temperature</th>
				<th>Humidity</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${weatherDetailsList}" var="WeatherDetailsEntity">
				<tr>
					<td>${WeatherDetailsEntity.dateTime}</td>
					<td>${WeatherDetailsEntity.temperature}</td>
					<td>${WeatherDetailsEntity.humidity}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</body>
</html>
