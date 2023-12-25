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
    function onDirectoryChange() {
        $.ajax({
            type:'GET',
            url:'/api/directory' + document.getElementById("Selector1").value,
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

                document.getElementById("Selector2").value = "";
            }
        })
    }

    function onClassifierChange() {
        $.ajax({
            type:'GET',
            url:'/api/classifier' + document.getElementById("Selector2").value,
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

                document.getElementById("Selector1").value = "";
            }
        })
    }
</script>
<head>
    <style><%@include file="/WEB-INF/jsp/style.css"%></style>
    <title>Title</title>
</head>
<body>
<select style="text-align: center; font-weight: bold; border-radius: 5px" id="Selector1" onchange="onDirectoryChange()">
    <option value="" selected disabled hidden>Справочники</option>
    <option value="1">Справочник 1</option>
    <option value="2">Справочник 2</option>
</select>
<select style="text-align: center; font-weight: bold; border-radius: 5px" id="Selector2" onchange="onClassifierChange()">
    <option value="" selected disabled hidden>Классификаторы</option>
    <option value="1">Классификатор 1</option>
</select>
<div style="display: flex; margin-left: auto; margin-right: auto; margin-top: 10px;">
    <table style="margin-left: auto; margin-right: auto;" id="myTable">
    </table>
</div>
</body>
</html>