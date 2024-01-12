<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <style><%@include file="/WEB-INF/jsp/style.css"%></style>
    <title>ИВЦ ЖА</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<script>

    const tableInfo = [
        {
            "tableName":"directory1",
            "url":"directories/directory1",
            "parserFunction":"directory1Parser"
        },
        {
            "tableName":"k1",
            "url":"classifiers/k1",
            "parserFunction":"k1Parser"
        },
        {
            "tableName":"k2",
            "url":"classifiers/k2",
            "parserFunction":"k2Parser"
        }
    ]

    function directory1Parser(row) {
        var cells = row.cells;

        var array = [];
        array[0] = cells[0].innerHTML;
        for (let i = 1; i < 5; i++) {
            array[i] = cells[i].children[0].value;
        }

        var regExp = /[a-zA-Zа-яА-Я]/g;
        for (let i = 2; i < array.length; i++) {
            if (regExp.test(array[i])) {
                $('#myModal').modal('show');
                throw new Error("Запись в таблицу не была добавлена");
            }
        }

        var jsonBody = {
            "id": array[0],
            "column2": array[1],
            "column3": parseInt(array[2]),
            "column4": parseInt(array[3]),
            "column5": parseInt(array[4])
        }

        return jsonBody;
    }

    function k1Parser(row) {
        var cells = row.cells;

        var array = [];
        array[0] = cells[0].innerHTML;
        for (let i = 1; i < 7; i++) {
            array[i] = cells[i].children[0].value;
        }

        var regExp = /[a-zA-Zа-яА-Я]/g;
        for (let i = 2; i < array.length; i++) {
            if (regExp.test(array[i])) {
                $('#myModal').modal('show');
                throw new Error("Запись в таблицу не была добавлена");
            }
        }

        var jsonBody = {
            "id": array[0],
            "column2": array[1],
            "column3": parseInt(array[2]),
            "column4": parseInt(array[3]),
            "column5": parseInt(array[4]),
            "column6": parseInt(array[5]),
            "column7": parseInt(array[6])
        }

        return jsonBody;
    }

    function k2Parser(row) {
        var cells = row.cells;

        var array = [];
        array[0] = cells[0].innerHTML;
        for (let i = 1; i < 7; i++) {
            array[i] = cells[i].children[0].value;
        }

        var regExp = /[a-zA-Zа-яА-Я]/g;
        for (let i = 2; i < array.length; i++) {
            if (regExp.test(array[i])) {
                $('#myModal').modal('show');
                throw new Error("Запись в таблицу не была добавлена");
            }
        }

        var jsonBody = {
            "id": array[0],
            "column2": array[1],
            "column3": parseInt(array[2]),
            "column4": parseInt(array[3]),
            "column5": parseInt(array[4]),
            "column6": parseInt(array[5]),
            "column7": parseInt(array[6])
        }

        return jsonBody;
    }

    function addRowsToTable(table, result) {
        var columns = Object.keys(result[0]);

        for (let i = 0; i < result.length; i++) {
            var row = table.insertRow(table.rows.length);
            for (let j = 0; j < columns.length; j++) {
                var cell = row.insertCell(j);
                cell.innerHTML = result[i][columns[j]];
            }

            addButtonsToRow(row, result[i].id);
        }
    }

    function addButtonsToRow(row, id) {
        var lastCell = row.insertCell(row.cells.length);
        var updateButton = document.createElement("button");
        updateButton.innerHTML = "Изменить";
        updateButton.setAttribute("onClick", "updateFieldUI(" + id + ")");
        updateButton.classList.add("update-button");
        lastCell.appendChild(updateButton);

        var deleteButton = document.createElement("button");
        deleteButton.innerHTML = "Удалить";
        deleteButton.setAttribute("onClick", "deleteFieldQuery(" + id + ")");
        deleteButton.classList.add("delete-button");
        lastCell.appendChild(deleteButton);
    }

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
                table.name = value;
                table.innerHTML = "";
                var header = table.insertRow(0);
                var cell1Header = header.insertCell(0);
                var cell2Header = header.insertCell(1);
                var cell3Header = header.insertCell(2);
                var cell4Header = header.insertCell(3);
                var cell5Header = header.insertCell(4);
                var cell6Header = header.insertCell(5);

                cell1Header.innerHTML = "Id";
                cell2Header.innerHTML = "Column2";
                cell3Header.innerHTML = "Column3";
                cell4Header.innerHTML = "Column4";
                cell5Header.innerHTML = "Column5";
                cell6Header.innerHTML = "Действия";

                header.classList.add("table-header");

                createAddField(table);
                oldValues = [];
                addRowsToTable(table, result);

                activateListGroupElement("Directory_" + value);
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
                table.name = value;
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

                createAddField(table);

                oldValues = [];

                addRowsToTable(table, result);

                activateListGroupElement("Classifier_" + value);
            }
        })
    }

    function activateListGroupElement(id) {
        var list = document.getElementById("DocumentsArray");
        list.childNodes.forEach((item) => {
            if (item.classList.contains("active")) {
                item.classList.remove("active");
            }
        })
        document.getElementById(id).classList.add("active");
    }

    function createAddField(table) {
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
        addButton.setAttribute("onClick", "addQuery()");
        row.cells[row.cells.length - 1].appendChild(addButton);
        row.cells[row.cells.length - 1].style.height = row.style.height + "px";
    }

    function addQuery() {
        var table = document.getElementById("myTable");
        var currentTable = tableInfo.find(value => value.tableName === table.name);

        var data = window[currentTable.parserFunction](table.rows[1]);

        $.ajax({
            type:'POST',
            url:'/api/' + currentTable.url,
            headers:{
                Accept : "application/json; charset=utf8",
                "Content-Type" : "application/json; charset=utf8"
            },
            data: JSON.stringify(data),
            success: function (result) {
                var table = document.getElementById("myTable");
                var data = [result];
                addRowsToTable(table, data);

                var container = document.getElementById("containerTable");
                container.scroll({ top: container.scrollHeight - container.clientHeight, behavior: 'smooth' });
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
                document.getElementById("myTable").innerHTML = "";
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
                    ref.setAttribute("id", "Directory_" + result[i]);
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
                document.getElementById("myTable").innerHTML = "";
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
                    ref.setAttribute("id", "Classifier_" + result[i]);
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
                        ref.setAttribute("id", "Directory_" + result[i]);
                    }
                    if (result[i].startsWith("k")) {
                        ref.setAttribute("onClick", "onClassifierChange('" + result[i] + "')");
                        ref.setAttribute("id", "Classifier_" + result[i]);
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
            if (array.firstChild !== null) {
                while (array.firstChild) {
                    array.removeChild(array.firstChild);
                }
            }
        }
    });

    var oldValues = [];

    function findRowIndexById(id) {
        var table = document.getElementById("myTable");
        for (let i = 0; i < table.rows.length; i++) {
            if (table.rows[i].firstChild.innerHTML === id.toString()) {
                return table.rows[i];
            }
        }

        return 0;
    }

    function updateFieldUI(id) {
        var table = document.getElementById("myTable");
        var cells = findRowIndexById(id).cells;

        console.log(findRowIndexById(id));

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
        var cells = findRowIndexById(id).cells;

        var oldField = oldValues.find(value => parseInt(value[0]) === id);

        for (let i = 1; i < cells.length - 1; i++) {
            cells[i].removeChild(cells[i].firstChild);
            cells[i].innerHTML = oldField[i];
        }

        cells[cells.length - 1].remove();

        addButtonsToRow(findRowIndexById(id), id);

        oldValues = oldValues.filter(value => parseInt(value[0]) !== id);
    }

    function updateFieldQuery(id) {
        var table = document.getElementById("myTable");
        var cells = findRowIndexById(id).cells;

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

                cells[cells.length - 1].remove();

                addButtonsToRow(findRowIndexById(id), id);

                oldValues = oldValues.filter(value => parseInt(value[0]) !== id);
            }
        })
    }

    function deleteFieldQuery(id) {
        $.ajax({
            type:'DELETE',
            url:'/api/classifiers/k1/' + id,
            headers:{
                Accept : "application/json; charset=utf8",
                "Content-Type" : "application/json; charset=utf8"
            },
            success: function (result) {
                var table = document.getElementById("myTable");
                findRowIndexById(id).remove();
            }
        })
    }
</script>
<body style="margin: 0">
<div class="header1">
    <div style="display: flex; flex-direction: row">
        <div class="header1Text" onclick="loadDirectoryNames()">Справочники</div>
        <div class="header1Text" onclick="loadClassifierNames()">Классификаторы</div>
        <div class="header1Text" onclick="">Справки</div>
    </div>
    <div style="display: flex; align-items: center; margin-right: 50px">
        <label style="margin-bottom: 0">
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
    <div class="container-table" id="containerTable">
        <table class="table table-striped table-bordered table-custom" id="myTable"></table>
    </div>
</div>
<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Ошибка</h4>
            </div>
            <div class="modal-body">
                <p>Что-то пошло не так...</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
            </div>
        </div>

    </div>
</div>
</body>
</html>