<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript">
    $(document).ready(function (){
        $('#Classifier1GetAll').click(function (){
            $.ajax({
                type:'GET',
                url:'/api/classifier1',
                headers:{
                    Accept : "application/json; charset=utf8",
                    "Content-Type" : "application/json; charset=utf8"
                },
                success: function (result) {
                    var table = document.getElementById("myTable");
                    table.innerHTML = "";
                    var header = table.insertRow(0);
                    var cell1Header = header.insertCell(0);
                    var cell2Header = header.insertCell(1);
                    var cell3Header = header.insertCell(2);
                    var cell4Header = header.insertCell(3);
                    var cell5Header = header.insertCell(4);
                    var cell6Header = header.insertCell(5);
                    var cell7Header = header.insertCell(6);

                    cell1Header.innerHTML = "Id";
                    cell2Header.innerHTML = "Column2";
                    cell3Header.innerHTML = "Column3";
                    cell4Header.innerHTML = "Column4";
                    cell5Header.innerHTML = "Column5";
                    cell6Header.innerHTML = "Column6";
                    cell7Header.innerHTML = "Column7";

                    for (let i = 0; i < result.length; i++) {
                        var row = table.insertRow(table.rows.length);
                        var cell1 = row.insertCell(0);
                        var cell2 = row.insertCell(1);
                        var cell3 = row.insertCell(2);
                        var cell4 = row.insertCell(3);
                        var cell5 = row.insertCell(4);
                        var cell6 = row.insertCell(5);
                        var cell7 = row.insertCell(6);

                        cell1.innerHTML = result[i].id;
                        cell2.innerHTML = result[i].column2;
                        cell3.innerHTML = result[i].column3;
                        cell4.innerHTML = result[i].column4;
                        cell5.innerHTML = result[i].column5;
                        cell6.innerHTML = result[i].column6;
                        cell7.innerHTML = result[i].column7;
                    }
                }
            })
        })
    })
</script>
<script>
    function onDirectoryChange(value) {
        $.ajax({
            type:'GET',
            url:'/api/directories/' + value,
            headers:{
                Accept : "application/json; charset=utf8",
                "Content-Type" : "application/json; charset=utf8"
            },
            success: function (result) {
                var table = document.getElementById("myTable");
                table.innerHTML = "";
                var header = table.insertRow(0);
                var cell1Header = header.insertCell(0);
                var cell2Header = header.insertCell(1);
                var cell3Header = header.insertCell(2);
                var cell4Header = header.insertCell(3);
                var cell5Header = header.insertCell(4);

                cell1Header.innerHTML = "Id";
                cell2Header.innerHTML = "Column2";
                cell3Header.innerHTML = "Column3";
                cell4Header.innerHTML = "Column4";
                cell5Header.innerHTML = "Column5";

                for (let i = 0; i < result.length; i++) {
                    var row = table.insertRow(table.rows.length);
                    var cell1 = row.insertCell(0);
                    var cell2 = row.insertCell(1);
                    var cell3 = row.insertCell(2);
                    var cell4 = row.insertCell(3);
                    var cell5 = row.insertCell(4);

                    cell1.innerHTML = result[i].id;
                    cell2.innerHTML = result[i].column2;
                    cell3.innerHTML = result[i].column3;
                    cell4.innerHTML = result[i].column4;
                    cell5.innerHTML = result[i].column5;
                }
            }
        })
    }

    function onClassifierChange(value) {
        $.ajax({
            type:'GET',
            url:'/api/classifiers/' + value,
            headers:{
                Accept : "application/json; charset=utf8",
                "Content-Type" : "application/json; charset=utf8"
            },
            success: function (result) {
                var table = document.getElementById("myTable");
                table.innerHTML = "";
                var header = table.insertRow(0);
                var cell1Header = header.insertCell(0);
                var cell2Header = header.insertCell(1);
                var cell3Header = header.insertCell(2);
                var cell4Header = header.insertCell(3);
                var cell5Header = header.insertCell(4);
                var cell6Header = header.insertCell(5);
                var cell7Header = header.insertCell(6);

                cell1Header.innerHTML = "Id";
                cell2Header.innerHTML = "Column2";
                cell3Header.innerHTML = "Column3";
                cell4Header.innerHTML = "Column4";
                cell5Header.innerHTML = "Column5";
                cell6Header.innerHTML = "Column6";
                cell7Header.innerHTML = "Column7";

                for (let i = 0; i < result.length; i++) {
                    var row = table.insertRow(table.rows.length);
                    var cell1 = row.insertCell(0);
                    var cell2 = row.insertCell(1);
                    var cell3 = row.insertCell(2);
                    var cell4 = row.insertCell(3);
                    var cell5 = row.insertCell(4);
                    var cell6 = row.insertCell(5);
                    var cell7 = row.insertCell(6);

                    cell1.innerHTML = result[i].id;
                    cell2.innerHTML = result[i].column2;
                    cell3.innerHTML = result[i].column3;
                    cell4.innerHTML = result[i].column4;
                    cell5.innerHTML = result[i].column5;
                    cell6.innerHTML = result[i].column6;
                    cell7.innerHTML = result[i].column7;
                }
            }
        })
    }

    function loadDirectoryNames() {
        $.ajax({
            type:'GET',
            url:'/api/directories',
            headers:{
                Accept : "application/json; charset=utf8",
                "Content-Type" : "application/json; charset=utf8"
            },
            success: function (result) {
                var array = document.getElementById("DocumentsArray");
                while (array.firstChild) {
                    array.removeChild(array.firstChild);
                }
                for (let i = 0; i < result.length; i++) {
                    var button = document.createElement('button');
                    button.innerText = result[i];
                    button.classList.add("document");
                    button.setAttribute("onClick", "onDirectoryChange('" + result[i] + "')");
                    array.appendChild(button);
                }
            }
        })
    }

    function loadClassifierNames() {
        $.ajax({
            type:'GET',
            url:'/api/classifiers',
            headers:{
                Accept : "application/json; charset=utf8",
                "Content-Type" : "application/json; charset=utf8"
            },
            success: function (result) {
                var array = document.getElementById("DocumentsArray");
                while (array.firstChild) {
                    array.removeChild(array.firstChild);
                }
                for (let i = 0; i < result.length; i++) {
                    var button = document.createElement('button');
                    button.innerText = result[i];
                    button.classList.add("document");
                    button.setAttribute("onClick", "onClassifierChange('" + result[i] + "')");
                    array.appendChild(button);
                }
            }
        })
    }
</script>
<head>
    <style><%@include file="/WEB-INF/jsp/style.css"%></style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
</head>
    <title>Title</title>
</head>
<body style="margin: 0">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<div class="header1">
    <div>
        <div class="header1Text" onclick="loadDirectoryNames()">Справочники</div>
        <div class="header1Text" onclick="loadClassifierNames()">Классификаторы</div>
        <div class="list-choice">
            <div class="list-choice-title">Справки</div>
            <div class="list-choice-objects">
                <label>
                    <input type="radio" name="name"/>
                    <span style="width: 100px">О прог</span>
                </label>
                <label>
                    <input type="radio" name="name"/>
                    <span style="width: 100%">Контакты</span>
                </label>
                <label>
                    <input type="radio" name="name"/>
                    <span style="width: 100%">Норматив</span>
                </label>
            </div>
        </div>
    </div>
    <div style="margin-right: 5%; padding: 8px;">
        <label>
            <input class="searchField" type="text" placeholder="Поиск..." style="color: rgb(128,128,128); text-indent: 6px">
            </input>
        </label>
    </div>
</div>
<div style="display: flex; flex-direction: row">
    <div>
        <div class="box" id="DocumentsArray">
            <div class="list-group">
                <a href="#" class="list-group-item list-group-item-action active" aria-current="true">
                    The current link item
                </a>
                <a href="#" class="list-group-item list-group-item-action">A second link item</a>
                <a href="#" class="list-group-item list-group-item-action">A third link item</a>
                <a href="#" class="list-group-item list-group-item-action">A fourth link item</a>
                <a class="list-group-item list-group-item-action disabled" aria-disabled="true">A disabled link item</a>
                <a href="#" class="list-group-item list-group-item-action active" aria-current="true">
                    The current link item
                </a>
                <a href="#" class="list-group-item list-group-item-action">A second link item</a>
                <a href="#" class="list-group-item list-group-item-action">A third link item</a>
                <a href="#" class="list-group-item list-group-item-action">A fourth link item</a>
                <a class="list-group-item list-group-item-action disabled" aria-disabled="true">A disabled link item</a>
                <a href="#" class="list-group-item list-group-item-action active" aria-current="true">
                    The current link item
                </a>
                <a href="#" class="list-group-item list-group-item-action">A second link item</a>
                <a href="#" class="list-group-item list-group-item-action">A third link item</a>
                <a href="#" class="list-group-item list-group-item-action">A fourth link item</a>
                <a class="list-group-item list-group-item-action disabled" aria-disabled="true">A disabled link item</a>
            </div>
        </div>
    </div>
    <div style="margin-left: auto; margin-right: auto">
        <table class="table table-striped" id="myTable"></table>
    </div>
</div>
</body>
</html>