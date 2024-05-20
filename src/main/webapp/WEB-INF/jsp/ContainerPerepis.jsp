<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <style><%@include file="/WEB-INF/jsp/perepisStyle.css"%></style>
    <title>Перепись контейнеров</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</head>
<script>

    let selectedTables = [];

    function downloadSelectedFiles() {
        const a = document.createElement('a');
        document.body.appendChild(a);
        selectedTables.forEach((el) => {

            a.href = "http://127.0.0.1:8080/api/perepis/" + el.table + "/" + el.year + "/excel";
            a.download = el + ".excel";
            a.click();
        })
        document.body.removeChild(a);
    }

    $('#myModal').on('shown.bs.modal', function () {
        $('#myInput').trigger('focus')
    })

    document.addEventListener('DOMContentLoaded', function() {
        const inputs = Array.from(document.getElementById('tree').getElementsByTagName('input'));
        inputs.forEach(el => {
            el.addEventListener('change', (event) => {
                var value = event.currentTarget.value;
                var name = event.currentTarget.name;
                var year = event
                    .currentTarget
                    .parentElement
                    .parentElement
                    .parentElement
                    .getElementsByTagName("summary")
                    .item(0)
                    .innerText;
                if (event.currentTarget.checked) {
                    selectedTables.push({"year": year, "table": value, "name":name})
                }
                else {
                    selectedTables = selectedTables.filter(element => {
                        return !(element.year === year && element.table === value);
                    })
                }
                document.getElementById("tableCount").innerHTML = "Выбрано файлов: " + selectedTables.length;
                console.log(selectedTables);
            })
        });

    }, false);

    function showModal() {
        $('#exampleModal').modal('show');
        const body = document.getElementById("modalBody");

        Array.from(body.children).forEach(el => {
            if (el.innerHTML !== "Будут удалены следующие файлы:") {
                body.removeChild(el);
            }
        })

        selectedTables.forEach(el => {
            var p = document.createElement("p");
            p.innerText = el.name + ".xlsx (" + el.year + ")";
            body.appendChild(p);
        })
    }

    function deleteSelectedFilesFromDB() {

        $.ajax({
            type:'DELETE',
            url:'/api/perepis/tables',
            headers:{
                Accept : "application/json; charset=utf8",
                "Content-Type" : "application/json; charset=utf8"
            },
            data: JSON.stringify(selectedTables),
            success: function (result) {
                $('#statusModal').modal('show');
            }
        })
    }

</script>
<body>
<h4>
    Файлы:
</h4>
<ul class="tree" id="tree">
        <li>
            <details>
                <summary>2023</summary>
                <ul>
                    <li style="display: flex; flex-direction: row">
                        <input type="checkbox" id="pk2_2023" value="pk2" name="ПК-2" style="margin-right: 5px"/>
                        ПК-2.xlsx
                    </li>
                    <li style="display: flex; flex-direction: row">
                        <input type="checkbox" id="pk3_2023" value="pk3" name="ПК-3" style="margin-right: 5px"/>
                        ПК-3.xlsx
                    </li>
                    <li style="display: flex; flex-direction: row">
                        <input type="checkbox" id="pk4_2023" value="pk4" name="ПК-4" style="margin-right: 5px"/>
                        ПК-4.xlsx
                    </li>
                    <li style="display: flex; flex-direction: row">
                        <input type="checkbox" id="pk8_2023" value="pk8" name="ПК-8" style="margin-right: 5px"/>
                        ПК-8.xlsx
                    </li>
                    <li style="display: flex; flex-direction: row">
                        <input type="checkbox" id="pk17_2023" value="pk17" name="ПК-17" style="margin-right: 5px"/>
                        ПК-17.xlsx
                    </li>
                </ul>
            </details>
        </li>
    <li>
        <details>
            <summary>2022</summary>
            <ul>
                <li style="display: flex; flex-direction: row">
                    <input type="checkbox" id="pk2_2022" value="pk2" name="ПК-2" style="margin-right: 5px"/>
                    ПК-2.xlsx
                </li>
                <li style="display: flex; flex-direction: row">
                    <input type="checkbox" id="pk3_2022" value="pk3" name="ПК-3" style="margin-right: 5px"/>
                    ПК-3.xlsx
                </li>
                <li style="display: flex; flex-direction: row">
                    <input type="checkbox" id="pk4_2022" value="pk4" name="ПК-4" style="margin-right: 5px"/>
                    ПК-4.xlsx
                </li>
                <li style="display: flex; flex-direction: row">
                    <input type="checkbox" id="pk8_2022" value="pk8" name="ПК-8" style="margin-right: 5px"/>
                    ПК-8.xlsx
                </li>
                <li style="display: flex; flex-direction: row">
                    <input type="checkbox" id="pk17_2022" value="pk17" name="ПК-17" style="margin-right: 5px"/>
                    ПК-17.xlsx
                </li>
            </ul>
        </details>
    </li>
    <li>
        <details>
            <summary>2021</summary>
            <ul>
                <li style="display: flex; flex-direction: row">
                    <input type="checkbox" id="pk2_2021" value="pk2" name="ПК-2" style="margin-right: 5px"/>
                    ПК-2.xlsx
                </li>
                <li style="display: flex; flex-direction: row">
                    <input type="checkbox" id="pk3_2021" value="pk3" name="ПК-3" style="margin-right: 5px"/>
                    ПК-3.xlsx
                </li>
                <li style="display: flex; flex-direction: row">
                    <input type="checkbox" id="pk4_2021" value="pk4" name="ПК-4" style="margin-right: 5px"/>
                    ПК-4.xlsx
                </li>
                <li style="display: flex; flex-direction: row">
                    <input type="checkbox" id="pk8_2021" value="pk8" name="ПК-8" style="margin-right: 5px"/>
                    ПК-8.xlsx
                </li>
                <li style="display: flex; flex-direction: row">
                    <input type="checkbox" id="pk17_2021" value="pk17" name="ПК-17" style="margin-right: 5px"/>
                    ПК-17.xlsx
                </li>
            </ul>
        </details>
    </li>
    <li>
        <details>
            <summary>2020</summary>
            <ul>
                <li style="display: flex; flex-direction: row">
                    <input type="checkbox" id="pk2_2020" value="pk2" name="ПК-2" style="margin-right: 5px"/>
                    ПК-2.xlsx
                </li>
                <li style="display: flex; flex-direction: row">
                    <input type="checkbox" id="pk3_2020" value="pk3" name="ПК-3" style="margin-right: 5px"/>
                    ПК-3.xlsx
                </li>
                <li style="display: flex; flex-direction: row">
                    <input type="checkbox" id="pk4_2020" value="pk4" name="ПК-4" style="margin-right: 5px"/>
                    ПК-4.xlsx
                </li>
                <li style="display: flex; flex-direction: row">
                    <input type="checkbox" id="pk8_2020" value="pk8" name="ПК-8" style="margin-right: 5px"/>
                    ПК-8.xlsx
                </li>
                <li style="display: flex; flex-direction: row">
                    <input type="checkbox" id="pk17_2020" value="pk17" name="ПК-17" style="margin-right: 5px"/>
                    ПК-17.xlsx
                </li>
            </ul>
        </details>
    </li>
</ul>
<h6 id="tableCount">
    Количество Файлов: 0
</h6>
<div class="download-row">
    <button class="download-button" onclick="downloadSelectedFiles()">Скачать</button>
    <button class="delete-button" onclick="showModal()">Удалить из БД</button>
</div>
<div class="modal" tabindex="-1" role="dialog" id="exampleModal">
    <div class="modal-dialog modal-dialog-scrollable" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Подтверждение</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" id="modalBody">
                <p>Будут удалены следующие файлы:</p>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" style="background-color: red" onclick="deleteSelectedFilesFromDB()" data-dismiss="modal">Удалить</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Отмена</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade bd-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" id="statusModal">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            Успешно
        </div>
    </div>
</div>
</body>
</html>