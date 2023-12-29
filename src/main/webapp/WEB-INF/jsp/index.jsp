<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
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

                header.classList.add("table-header");

                getAddField(table);

                for (let i = 0; i < result.length; i++) {
                    var row = table.insertRow(table.rows.length);
                    var cell1 = row.insertCell(0);
                    var cell2 = row.insertCell(1);
                    var cell3 = row.insertCell(2);
                    var cell4 = row.insertCell(3);
                    var cell5 = row.insertCell(4);

                    cell1.style = "font-weight: bold";

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
                table.name = "classifiers/" + value;
                table.innerHTML = "";
                var header = table.insertRow(0);
                var cell1Header = header.insertCell(0);
                var cell2Header = header.insertCell(1);
                var cell3Header = header.insertCell(2);
                var cell4Header = header.insertCell(3);
                var cell5Header = header.insertCell(4);
                var cell6Header = header.insertCell(5);
                var cell7Header = header.insertCell(6);
                var cell8Header = header.insertCell(7);

                cell1Header.innerHTML = "Id";
                cell2Header.innerHTML = "Column2";
                cell3Header.innerHTML = "Column3";
                cell4Header.innerHTML = "Column4";
                cell5Header.innerHTML = "Column5";
                cell6Header.innerHTML = "Column6";
                cell7Header.innerHTML = "Column7";
                cell8Header.innerHTML = "Действия";

                header.classList.add("table-header");

                getAddField(table);

                oldValues = [];

                for (let i = 0; i < result.length; i++) {
                    var row = table.insertRow(table.rows.length);
                    var cell1 = row.insertCell(0);
                    var cell2 = row.insertCell(1);
                    var cell3 = row.insertCell(2);
                    var cell4 = row.insertCell(3);
                    var cell5 = row.insertCell(4);
                    var cell6 = row.insertCell(5);
                    var cell7 = row.insertCell(6);
                    var cell8 = row.insertCell(7);

                    cell1.style = "font-weight: bold";

                    cell1.innerHTML = result[i].id;
                    cell2.innerHTML = result[i].column2;
                    cell3.innerHTML = result[i].column3;
                    cell4.innerHTML = result[i].column4;
                    cell5.innerHTML = result[i].column5;
                    cell6.innerHTML = result[i].column6;
                    cell7.innerHTML = result[i].column7;

                    var updateButton = document.createElement("button");
                    updateButton.innerHTML = "Изменить";
                    updateButton.setAttribute("onClick", "updateFieldUI(" + result[i].id + ")");
                    updateButton.classList.add("update-button");
                    cell8.appendChild(updateButton);
                }
            }
        })
    }

    function getAddField(table) {
        var row = table.insertRow(table.rows.length);
        var rowCount = table.rows[0].cells.length;

        for (let i = 0; i < rowCount; i++) {
            row.insertCell(i);
        }

        for (let i = 1; i < row.cells.length - 1; i++) {
            var input = document.createElement("input");
            input.classList.add("form-control");
            input.classList.add("add-form");
            row.cells[i].innerHTML = "";
            row.cells[i].appendChild(input);
            row.cells[i].style.padding = "4px";
        }

        var addButton = document.createElement("button");
        addButton.innerHTML = "Добавить";
        addButton.classList.add("add-button");
        addButton.setAttribute("onClick", "addK1Query()");
        row.cells[row.cells.length - 1].appendChild(addButton);
        row.cells[row.cells.length - 1].style.height = row.style.height + "px";
    }

    function addK1Query() {
        var table = document.getElementById("myTable");
        var cells = table.rows[1].cells;

        var array = [];
        array[0] = cells[0].innerHTML;
        for (let i = 1; i < cells.length - 1; i++) {
            array[i] = cells[i].children[0].value;
        }

        var jsonBody = {
            "column2": array[1],
            "column3": parseInt(array[2]),
            "column4": parseInt(array[3]),
            "column5": parseInt(array[4]),
            "column6": parseInt(array[5]),
            "column7": parseInt(array[6])
        }

        $.ajax({
            type:'POST',
            url:'/api/classifiers/k1',
            headers:{
                Accept : "application/json; charset=utf8",
                "Content-Type" : "application/json; charset=utf8"
            },
            data: JSON.stringify(jsonBody),
            success: function (result) {

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
                    var ref = document.createElement('a');
                    ref.innerText = result[i];
                    ref.classList.add("list-group-item");
                    ref.classList.add("list-group-item-action");
                    ref.setAttribute("onClick", "onDirectoryChange('" + result[i] + "')");
                    ref.setAttribute("href", "#");
                    array.appendChild(ref);
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
                    var ref = document.createElement('a');
                    ref.innerText = result[i];
                    ref.classList.add("list-group-item");
                    ref.classList.add("list-group-item-action");
                    ref.classList.add("hrefStyle");
                    ref.setAttribute("onClick", "onClassifierChange('" + result[i] + "')");
                    ref.setAttribute("href", "#");
                    array.appendChild(ref);
                }
            }
        })
    }

    function searchQuery() {
        var val = document.getElementById("SearchInput").value;
        if (val.length === 0) {
            var array = document.getElementById("SearchMenu");
            while (array.firstChild) {
                array.removeChild(array.firstChild);
            }
            return;
        }
        $.ajax({
            type:'GET',
            url:'/api/search/tableNames',
            data: {
                "query" : val
            },
            headers:{
                Accept : "application/json; charset=utf8",
                "Content-Type" : "application/json; charset=utf8"
            },
            success: function (result) {
                var array = document.getElementById("SearchMenu");
                while (array.firstChild) {
                    array.removeChild(array.firstChild);
                }

                var count = result.length;

                if (count > 6) {
                    count = 6
                }
                for (let i = 0; i < count; i++) {
                    var ref = document.createElement('a');
                    ref.innerText = result[i];
                    ref.classList.add("list-group-item");
                    ref.classList.add("list-group-item-action");
                    if (result[i].startsWith("directory")) {
                        ref.setAttribute("onClick", "onDirectoryChange('" + result[i] + "')");
                    }
                    if (result[i].startsWith("k")) {
                        ref.setAttribute("onClick", "onClassifierChange('" + result[i] + "')");
                    }
                    ref.setAttribute("href", "#");
                    array.appendChild(ref);
                }
            }
        })
    }

    $(document).click(function(event) {
        var targetEl = event.target.id;
        if (targetEl !== "SearchInput") {
            var array = document.getElementById("SearchMenu");
            while (array.firstChild) {
                array.removeChild(array.firstChild);
            }
        }
    });

    var oldValues = [];

    function updateFieldUI(id) {
        var table = document.getElementById("myTable");
        var cells = table.rows[id + 1].cells;

        var oldField = []
        oldField.push(cells[0].innerHTML)

        for (let i = 1; i < cells.length - 1; i++) {
            var input = document.createElement("input");
            input.classList.add("form-control");
            input.value = cells[i].innerHTML;
            cells[i].innerHTML = "";
            cells[i].appendChild(input);

            oldField.push(cells[i].firstChild.value);
        }

        oldValues.push(oldField);

        while (cells[cells.length - 1].firstChild) {
            cells[cells.length - 1].removeChild(cells[cells.length - 1].firstChild);
        }

        var confirmButton = document.createElement("button");
        confirmButton.innerHTML = "Применить";
        confirmButton.classList.add("confirm-button");
        confirmButton.setAttribute("onClick", "updateFieldQuery(" + id + ")");
        cells[cells.length - 1].appendChild(confirmButton);

        var cancelButton = document.createElement("button");
        cancelButton.innerHTML = "Отменить";
        cancelButton.classList.add("cancel-button");
        cancelButton.setAttribute("onClick", "CancelUpdate(" + id + ")");
        cells[cells.length - 1].appendChild(cancelButton);
    }

    function CancelUpdate(id) {
        var table = document.getElementById("myTable");
        var cells = table.rows[id + 1].cells;

        var oldField = oldValues.find(value => parseInt(value[0]) === id);

        console.log(oldField)

        for (let i = 1; i < cells.length - 1; i++) {
            cells[i].removeChild(cells[i].firstChild);
            cells[i].innerHTML = oldField[i];
        }

        while (cells[cells.length - 1].firstChild) {
            cells[cells.length - 1].removeChild(cells[cells.length - 1].firstChild);
        }

        var updateButton = document.createElement("button");
        updateButton.innerHTML = "Изменить";
        updateButton.setAttribute("onClick", "updateFieldUI(" + id + ")");
        updateButton.classList.add("update-button");
        cells[cells.length - 1].appendChild(updateButton);

        oldValues = oldValues.filter(value => parseInt(value[0]) !== id);
    }

    function updateFieldQuery(id) {
        var table = document.getElementById("myTable");
        var cells = table.rows[id + 1].cells;

        var array = [];
        array[0] = cells[0].innerHTML;
        for (let i = 1; i < cells.length - 1; i++) {
            array[i] = cells[i].children[0].value;
        }

        var jsonBody = {
            "id": parseInt(array[0]),
            "column2": array[1],
            "column3": parseInt(array[2]),
            "column4": parseInt(array[3]),
            "column5": parseInt(array[4]),
            "column6": parseInt(array[5]),
            "column7": parseInt(array[6])
        }

        $.ajax({
            type:'PUT',
            url:'/api/classifiers/k1',
            headers:{
                Accept : "application/json; charset=utf8",
                "Content-Type" : "application/json; charset=utf8"
            },
            data: JSON.stringify(jsonBody),
            success: function (result) {

                for (let i = 1; i < cells.length - 1; i++) {
                    cells[i].removeChild(cells[i].firstChild);
                    cells[i].innerHTML = array[i];
                }

                while (cells[cells.length - 1].firstChild) {
                    cells[cells.length - 1].removeChild(cells[cells.length - 1].firstChild);
                }

                var updateButton = document.createElement("button");
                updateButton.innerHTML = "Изменить";
                updateButton.setAttribute("onClick", "updateFieldUI(" + array[0] + ")");
                updateButton.classList.add("update-button");
                cells[cells.length - 1].appendChild(updateButton);

                oldValues = oldValues.filter(value => parseInt(value[0]) !== id);
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
    <div style="margin-right: 5%; padding-top: 8px; padding-bottom: 8px">
        <label>
            <input class="searchField" type="text" placeholder="Поиск..." style="color: rgb(128,128,128); text-indent: 6px" oninput="searchQuery()" id="SearchInput">
            </input>
        </label>
        <div class="list-group searchMenu" id="SearchMenu"></div>
    </div>
</div>
<div style="display: flex; flex-direction: row; height: 90%">
    <div class="box">
        <div class="list-group" id="DocumentsArray">
        </div>
    </div>
    <div class="container-table">
        <table class="table table-striped table-bordered table-custom" id="myTable"></table>
    </div>
</div>
</body>
</html>