<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <style><%@include file="/WEB-INF/jsp/style.css"%></style>
    <title>Перепись контейнеров</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</head>
<script>

    function downloadSelectedFiles() {
        var selected = [];

        if (document.getElementById("pk2").checked)
            selected.push("pk2")
        if (document.getElementById("pk3").checked)
            selected.push("pk3")
        if (document.getElementById("pk4").checked)
            selected.push("pk4")
        if (document.getElementById("pk8").checked)
            selected.push("pk8")
        if (document.getElementById("pk17").checked)
            selected.push("pk17")

        console.log(selected);

        const a = document.createElement('a');
        document.body.appendChild(a);
        selected.forEach((el) => {

            a.href = "http://127.0.0.1:8080/api/perepis/" + el + "/excel";
            a.download = el + ".excel"
            a.click();
        })
        document.body.removeChild(a);
    }
</script>
<body>
<label for="year">Год переписи:</label>
<select id="year">
    <option value="2023">2023</option>
    <option value="2022">2022</option>
    <option value="2021">2021</option>
    <option value="2020">2020</option>
</select>
<div>
    <input type="checkbox" id="pk2" value="pk2" />
    <label for="pk2">ПК-2</label>
</div>
<div>
    <input type="checkbox" id="pk3" value="pk3" />
    <label for="pk3">ПК-3</label>
</div>
<div>
    <input type="checkbox" id="pk4" value="pk4" />
    <label for="pk4">ПК-4</label>
</div>
<div>
    <input type="checkbox" id="pk8" value="pk8" />
    <label for="pk8">ПК-8</label>
</div>
<div>
    <input type="checkbox" id="pk17" value="pk17" />
    <label for="pk17">ПК-17</label>
</div>
<button onclick="downloadSelectedFiles()">Подтвердить</button>
</body>
</html>