/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    $("#uploadBtn").click(function () {
        var data = new FormData();
        var tags = $('#tags').val();
        console.log(tags);
        console.log($('#fileSelector')[0].files[0]);
        data.append("file", $('#fileSelector')[0].files[0]);
        data.append("tags", tags);
        console.log(data);
        $.ajax({
            url: 'http://localhost:8080/code/upload',
            data: data,
            cache: false,
            contentType: false,
            async: false,
            processData: false,
            method: 'POST',
            type: 'POST', // For jQuery < 1.9
            success: function (data) {
                $("#fileContent").attr("data-fileid", data.fileId);
                var data = {
                    "fileid": data.fileid,
                    "username": ""
                };
                var fileName = $("#fileSelector").val().split('\\').pop();
                $.ajax({
                    type: "GET",
                    url: "http://localhost:8080/code/filecontent/" + fileName,
                    async: false,
                    success: function (data) {
                        $("#fileContent").html(data);
                        $("#myModal").modal("show");
                    },
                    dataType: "html"
                });
            }
        });

    });
});

var fileId = null; // used for readypush
//Post Ready Push
$(document).ready(function () {
    $("#pushButton").on('click', function () {
        var fileid = $("#fileContent").data("fileid");
        //var userid = $("#username").attr("user");
        var file = {
            "fileid": fileid,
            "userid": 1
        };

        $.ajax({
            url: 'http://localhost:8080/code/push',
            dataType: 'application/json',
            data: JSON.stringify(file),
            async: false,
            cache: false,
            contentType: 'application/json; charset=utf-8',
            method: 'POST',
            type: 'POST',
            success: function (data) {
            }
        });
        window.location = "/code/files";
    });
});

// Get the modal
var modal = document.getElementById('myModal');
// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks on <span> (x), close the modal
span.onclick = function () {
    modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function (event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

//All below method exist only for test reasons
//Post for save file in system
$(document).ready(function () {
    $("#testUploadBtn").click(function () {
        var data = new FormData();
        var tags = $('#tags').val();
        console.log(tags);
        console.log($('#fileSelector')[0].files[0]);
        data.append("file", $('#fileSelector')[0].files[0]);
        data.append("tags", tags);
        console.log(data);
        $.ajax({
            url: 'http://localhost:8080/',
            data: data,
            cache: false,
            contentType: false,
            processData: false,
            method: 'POST',
            type: 'POST', // For jQuery < 1.9
            success: function (data) {

                console.log(data);
            }
        });
    });
});

//GET REQUEST
$(document).ready(function () {
    $("#testOpenModalBtn").click(function () {
        var fileName = $("#fileSelector").val().split('\\').pop();
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/filecontent/" + fileName,
            //url: "http://localhost:8080/filecontent/PushCodeController.java",
            success: function (data) {
                //console.log(fileName);
                $("#fileContent").html(data);
            },
            dataType: "html"
        });
        modal.style.display = "block";
    });
});