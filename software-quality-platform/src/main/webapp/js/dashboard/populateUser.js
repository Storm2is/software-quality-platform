//initialize
var initData = data[data.length - 1];
populateUserDashboard(initData);
// Populate drop Select Period drop down menu
data.map(function (d) {
    var str = "<a class='dropdown-item' href='#'" + "value='" + d.period.id + "'>" + d.period.name + " : " + d.period.start + " - " + d.period.end + "</a>";
    $(".sprintPeriod").append(str);
});
//End initialization-------------------------

$(document).on('click', '.sprintPeriod a', function () {
    var key = $(this).attr("value"); //read value
    var periodData = getDashboardObj(key);
    populateUserDashboard(periodData);
});

function populateUserDashboard(data) {
    $("#selectedPeriod").text("Selected period: " + data.period.start + " - " + data.period.end);

    populateMyResults(data);

    populateUserBadges(data);

    //Plot time Series
    plotTimeSeries(data.acceptedCode, data.sprintGoal);

    //Plot pie Chart
    plotPieChart(data.quality);
}

function populateMyResults(data)
{
    //to populate .myDashboard data use #filesUloaded #filesReviewed
    $(".myDashboard #filesUloaded").text(data.uploadedFiles);
    $(".myDashboard #filesReviewed").text(data.reviewedFiles);
}

function populateUserBadges(data)
{
    //to add badges use .userBadges
    $(".userBadges").empty();
    data.badges.map(function (d) {
        var bagesDiv = $(".userBadges");
        bagesDiv.append(generateCardHtml(d));
    }
    );
}

function generateCardHtml(str) {
    var imgSrc = getImgSrc(str);

    var html = "<div class='card' style= 'margin-bottom: 30px;'>" +
            "<div class='card-header text-center'>" + str + "</div>" +
            "<div class='card-body'>" +
            "<img class='card-img-top cardImage' src=" + imgSrc + " alt='" + str + "Card image cap'>" +
            "</div>" +
            "</div>";
    return html;
}

function getImgSrc(str) {
    var imgSrc = "";
    switch (str) {
        case 'Master Coder':
            imgSrc = "/img/master_coder.PNG";
            break;
        case 'Master Reviewer':
            imgSrc = "/img/master_reviewer.PNG";
            break;
        case 'Super Star':
            imgSrc = "/img/super_star.PNG";
            break;
        case 'Best Coder':
            imgSrc = "/img/best_coder.PNG";
            break;
        default:
            imgSrc = "";
            break;
    }
    return imgSrc;
}

function getDashboardObj(key) {
    return data.filter(function (d) {
        return d.period.id == key
    })[0];
}
