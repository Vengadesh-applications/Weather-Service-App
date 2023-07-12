<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Weather Live</title>
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
	<h1 align="center">~~~ Weather Live Update ~~~</h1>


	<h2 align="center">
		City : ${liveData.cityName}
		</h4>

		<table align="center">
			<thead>
				<tr>
					<th>City</th>
					<th>Date</th>
					<th>Temperature</th>
					<th>Humidity</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>${liveData.cityName}</td>
					<td>${liveData.dateTime}</td>
					<td>${liveData.temperature}</td>
					<td>${liveData.humidity}</td>
				</tr>
			</tbody>
		</table>
</body>
</html>
