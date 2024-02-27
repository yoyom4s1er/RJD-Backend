<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <style><%@include file="/WEB-INF/jsp/style.css"%></style>
    <title>Справочники и Классификаторы</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</head>
<script>

    const serverAdress = "127.0.0.1:8080";
    const tableInfo = [
        {
            "tableName":"directory1",
            "url":"directories/directory1",
            "parserFunction":"directory1Parser",
            "columns":[
                "Id",
                "Column2",
                "Column3",
                "Column4",
                "Column5"
            ],
            "dataTypes":[
                "number",
                "string",
                "number",
                "number",
                "number"
            ]
        },
        {
            "tableName":"k1",
            "url":"classifiers/k1",
            "parserFunction":"k1Parser",
            "columns":[
                "Id",
                "Column2",
                "Column3",
                "Column4",
                "Column5",
                "Column6",
                "Column7"
            ],
            "dataTypes":[
                "number",
                "string",
                "number",
                "number",
                "number",
                "number",
                "number"
            ]
        },
        {
            "tableName":"k2",
            "url":"classifiers/k2",
            "parserFunction":"k2Parser",
            "columns":[
                "Id",
                "Column2",
                "Column3",
                "Column4",
                "Column5",
                "Column6",
                "Column7"
            ],
            "dataTypes":[
                "number",
                "string",
                "number",
                "number",
                "number",
                "number",
                "number"
            ]
        }
    ]

    var columnOrders = [];

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
                document.getElementById("TableName").innerHTML = value;
                table.innerHTML = "";
                var currentTable = tableInfo.find(value => value.tableName === table.name);

                var excelRef = document.getElementById("ExcelRef");
                excelRef.style = "visibility: visible";
                //excelRef.action = "/api/" + currentTable.url + "/excel";
                table.innerHTML = "";

                var header = table.insertRow(0);
                header.style = "user-select: none";

                for (let i = 0; i < 5; i++) {
                    var cellHeader = header.insertCell(i);
                    var bElement = document.createElement("b");

                    var sortIcon = document.createElement("i");
                    sortIcon.classList.add("fa");
                    sortIcon.classList.add("fa-fw");
                    sortIcon.classList.add("fa-sort");
                    sortIcon.id = "SortIcon-" + i;

                    cellHeader.appendChild(bElement);
                    cellHeader.appendChild(sortIcon);
                    bElement.innerHTML = currentTable.columns[i];
                    cellHeader.setAttribute("onClick", "sortTable(" + i +")");
                }
                var bElement = document.createElement("b");
                bElement.innerHTML = "Действия";
                header.insertCell(5).appendChild(bElement);

                columnOrders = [0,0,0,0,0];

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
                document.getElementById("TableName").innerHTML = value;
                var currentTable = tableInfo.find(value => value.tableName === table.name);

                var excelRef = document.getElementById("ExcelRef");
                excelRef.style = "visibility: visible";
                //excelRef.action = "/api/" + currentTable.url + "/excel";
                table.innerHTML = "";

                var header = table.insertRow(0);
                header.style = "user-select: none";

                for (let i = 0; i < 7; i++) {
                    var cellHeader = header.insertCell(i);
                    var bElement = document.createElement("b");

                    var sortIcon = document.createElement("i");
                    sortIcon.classList.add("fa");
                    sortIcon.classList.add("fa-fw");
                    sortIcon.classList.add("fa-sort");
                    sortIcon.id = "SortIcon-" + i;

                    cellHeader.appendChild(bElement);
                    cellHeader.appendChild(sortIcon);
                    bElement.innerHTML = currentTable.columns[i];
                    cellHeader.setAttribute("onClick", "sortTable(" + i +")");
                }
                var bElement = document.createElement("b");
                bElement.innerHTML = "Действия";
                header.insertCell(7).appendChild(bElement);

                columnOrders = [0,0,0,0,0,0,0];

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
                document.getElementById("TableName").innerHTML = "";
                document.getElementById("ExcelRef").style = "visibility: hidden";
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
                document.getElementById("TableName").innerHTML = "";
                document.getElementById("ExcelRef").style = "visibility: hidden";
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

    function findRowById(id) {
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
        var cells = findRowById(id).cells;

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
        var cells = findRowById(id).cells;

        var oldField = oldValues.find(value => parseInt(value[0]) === id);

        for (let i = 1; i < cells.length - 1; i++) {
            cells[i].removeChild(cells[i].firstChild);
            cells[i].innerHTML = oldField[i];
        }

        cells[cells.length - 1].remove();

        addButtonsToRow(findRowById(id), id);

        oldValues = oldValues.filter(value => parseInt(value[0]) !== id);
    }

    function updateFieldQuery(id) {
        var table = document.getElementById("myTable");
        var currentTableInfo = tableInfo.find(value => value.tableName === table.name);

        var data = window[currentTableInfo.parserFunction](findRowById(id));

        cells = findRowById(id).cells;

        $.ajax({
            type:'PUT',
            url:'/api/' + currentTableInfo.url,
            headers:{
                Accept : "application/json; charset=utf8",
                "Content-Type" : "application/json; charset=utf8"
            },
            data: JSON.stringify(data),
            success: function (result) {

                for (let i = 1; i < cells.length - 1; i++) {
                    var string = cells[i].firstChild.value;
                    cells[i].removeChild(cells[i].firstChild);

                    cells[i].innerHTML = string;
                }

                cells[cells.length - 1].remove();

                addButtonsToRow(findRowById(id), id);

                oldValues = oldValues.filter(value => parseInt(value[0]) !== id);
            }
        })
    }

    function deleteFieldQuery(id) {
        var table = document.getElementById("myTable");
        var currentTableInfo = tableInfo.find(value => value.tableName === table.name);

        $.ajax({
            type:'DELETE',
            url:'/api/' + currentTableInfo.url + "/" + id,
            headers:{
                Accept : "application/json; charset=utf8",
                "Content-Type" : "application/json; charset=utf8"
            },
            success: function (result) {
                findRowById(id).remove();
            }
        })
    }

    function sortTable(columnNumber) {
        restoreSortingTable();
        var table = document.getElementById("myTable");
        var currentTableInfo = tableInfo.find(value => value.tableName === table.name);
        var icon = document.getElementById("SortIcon-" + columnNumber);
        icon.classList.remove("fa-sort");
        icon.classList.remove("fa-sort-asc");
        icon.classList.remove("fa-sort-desc");

        if (columnOrders[columnNumber] !== 1) {
            columnOrders[columnNumber] = 1;
            icon.classList.add("fa-sort-desc");
        }
        else {
            columnOrders[columnNumber] = -1;
            icon.classList.add("fa-sort-asc");
        }

        for (let i = 0; i < columnOrders.length; i++) {
            if (i !== columnNumber) {
                columnOrders[i] = 0;
                icon.classList.add("fa-sort");
                let otherIcon = document.getElementById("SortIcon-"+i);
                otherIcon.classList.remove("fa-sort-asc");
                otherIcon.classList.remove("fa-sort-desc");
                otherIcon.classList.add("fa-sort");
            }
        }

        var isSorting = true;
        while (isSorting) {
            var rows = table.rows;
            isSorting = false;

            for (let i = 2; i < rows.length - 1; i++) {
                if (currentTableInfo.dataTypes[columnNumber] === "number") {
                    var value1 = parseInt(rows[i].cells[columnNumber].innerHTML);
                    var value2 = parseInt(rows[i+1].cells[columnNumber].innerHTML);

                    if (columnOrders[columnNumber] !== 1) {
                        if (value1 < value2) {
                            rows[i].parentNode.insertBefore(rows[i+1], rows[i]);
                            isSorting = true;
                        }
                    }
                    else {
                        if (value1 > value2) {
                            rows[i].parentNode.insertBefore(rows[i+1], rows[i]);
                            isSorting = true;
                        }
                    }

                }
                else {
                    var value1 = rows[i].cells[columnNumber].innerHTML;
                    var value2 = rows[i+1].cells[columnNumber].innerHTML;

                    if (columnOrders[columnNumber] !== 1) {
                        if (strcmp(value1, value2) === -1) {
                            rows[i].parentNode.insertBefore(rows[i+1], rows[i]);
                            isSorting = true;
                        }
                    }
                    else {
                        if (strcmp(value1, value2) === 1) {
                            rows[i].parentNode.insertBefore(rows[i+1], rows[i]);
                            isSorting = true;
                        }
                    }
                }
            }
        }
    }

    function restoreSortingTable() {
        let columnNumber = 0;
        var table = document.getElementById("myTable");
        var currentTableInfo = tableInfo.find(value => value.tableName === table.name);

        var isSorting = true;
        while (isSorting) {
            var rows = table.rows;
            isSorting = false;

            for (let i = 2; i < rows.length - 1; i++) {
                if (currentTableInfo.dataTypes[columnNumber] === "number") {
                    var value1 = parseInt(rows[i].cells[columnNumber].innerHTML);
                    var value2 = parseInt(rows[i+1].cells[columnNumber].innerHTML);

                    if (value1 > value2) {
                        rows[i].parentNode.insertBefore(rows[i+1], rows[i]);
                        isSorting = true;
                    }

                }
                else {
                    var value1 = rows[i].cells[columnNumber].innerHTML;
                    var value2 = rows[i+1].cells[columnNumber].innerHTML;

                    if (strcmp(value1, value2) === 1) {
                        rows[i].parentNode.insertBefore(rows[i+1], rows[i]);
                        isSorting = true;
                    }
                }
            }
        }
    }

    function getExcelURL() {
        var table = document.getElementById("myTable");
        let currentTable = tableInfo.find(value => value.tableName === table.name);
        let url = "http://127.0.0.1:8080/api/" + currentTable.url + "/excel";
        let value = columnOrders.find((value, index) => {
            if (value !== 0) {
                return 1;
            }
        })

        if (value === undefined) {
            value = 0;
        }

        let sortedColumn = columnOrders.findIndex(el => el === value);
        let sortOrder = columnOrders[sortedColumn] === -1 ? "DESC" : "ASC";
        url+= "?sortOrder=" + sortOrder + "&sortColumnName=" + currentTable.columns[sortedColumn];

        console.log(url);

        const a = document.createElement('a');
        a.href = url;
        a.download = url.split('/').pop();
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
    }

    function strcmp(a, b)
    {
        return (a<b?-1:(a>b?1:0));
    }
</script>
<body style="margin: 0">
<div class="header1">
    <div style="display: flex; flex-direction: row">
        <div class="header1Text" onclick="loadDirectoryNames()">Справочники</div>
        <div class="header1Text" onclick="loadClassifierNames()">Классификаторы</div>
        <div class="header1Text" onclick="sortTable()">Справки</div>
    </div>
    <div style="display: flex; align-items: center; margin-right: 50px">
        <label style="margin-bottom: 0">
            <input class="searchField" type="text" placeholder="Поиск..." style="color: rgb(128,128,128); text-indent: 6px" oninput="searchQuery()" id="SearchInput">
            </input>
        </label>
        <div class="list-group searchMenu" id="SearchMenu"></div>
    </div>
</div>
<div style="display: flex; flex-direction: row; height: 90%; position: relative">
    <div class="box">
        <div class="list-group" id="DocumentsArray">
        </div>
    </div>
    <div class="container-for-table-and-extra">
        <div style="display: flex; flex-direction: row; justify-content: space-between">
            <h2 id="TableName"></h2>
            <form class="excel-form" id="ExcelRef" action="javascript:getExcelURL();" style="visibility: hidden">
                <input class="export-excel-button" type="submit" value="Экспорт в Excel" />
            </form>
        </div>
        <div class="container-table" id="containerTable">
            <table class="table table-striped table-bordered table-custom" id="myTable"></table>
        </div>
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