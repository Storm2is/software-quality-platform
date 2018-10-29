$('#originalCode').numberedtextarea();
$('#submitReviewBtn').on('click', function () {
    var form = $("#submitReviewForm").serializeArray();
    var data = objectifyForm(form);
    $.ajax({
        url: 'http://localhost:8080/review/file/' + $("#fileId").val(),
        dataType: 'application/json',
        data: JSON.stringify(data),
        async: false,
        cache: false,
        contentType: 'application/json; charset=utf-8',
        method: 'POST',
        type: 'POST',
        success: function (data) {
            console.log("Success");
        }
    });
    window.location = "/code/files";
});

function objectifyForm(formArray) {//serialize data function
    var returnArray = {};
    for (var i = 0; i < formArray.length; i++) {
        returnArray[formArray[i]['name']] = formArray[i]['value'];
    }
    return returnArray;
}


