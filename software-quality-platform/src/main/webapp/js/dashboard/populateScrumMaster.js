//initialize
var initData= data[data.length-1];
populateScrumMasterDashboard(initData);

// Populate drop Select Period drop down menu
data.map(function (d) {
  var str = "<a class='dropdown-item' href='#'" + "value='" + d.period.id + "'>"+d.period.name+" : " + d.period.start + " - " +  d.period.end+"</a>";
  $( ".sprintPeriod" ).append( str );
});
//End initialization-------------------------
$(document).on('click', '.sprintPeriod a', function() {
   var key = $(this).attr("value"); //read value
   var periodData= getDashboardObj(key);
   populateScrumMasterDashboard(periodData);
});

function populateUserTable(usersData){
  clearTable();
  usersData.map(function (d) {
    //Setup header
    $( ".usersTable thead tr" ).append("<th scope='col'>"+d.user.username+"</th>");
    //Setup Num of uploadedFiles
    $( ".usersTable tbody #filesUploaded").append("<td>"+d.uploadedFiles +"</td>");
    //Setup Num of reviewedFiles
    $( ".usersTable tbody #filesReviewed").append("<td>"+d.reviewedFiles +"</td>");
    //Setup Badges
   var badgesHtml="<td>";
    d.badges.map(function (badge){
      var card=generateCardHtml(badge);
      badgesHtml+=card;
      badgesHtml+="<br>";
    });
    $( ".usersTable tbody #userBadges").append(badgesHtml);
  });
}

function clearTable(){
  $( ".usersTable thead tr" ).empty()
                              .append("<th scope='col'>\\</th>");
  $( ".usersTable tbody #filesUploaded" ).empty()
                              .append("<td>Number of files uploaded :</td>");
  $( ".usersTable tbody #filesReviewed").empty()
                              .append("<td>Number of files reviewed :</td>");
  $( ".usersTable tbody #userBadges").empty()
                              .append("<td>User badges :</td>");
}

function populateScrumMasterDashboard(data)
{
  $( "#selectedPeriod" ).text("Selected period: "+ data.period.start+" - " + data.period.end);

  //get sprint goal
  $( ".sprintGoalTxt" ).val(data.sprintGoal[data.sprintGoal.length-1].value);

  //Plot time Series
  plotTimeSeries(data.acceptedCode,data.sprintGoal);

  //Plot pie Chart
  plotPieChart(data.quality);
  //Populate table

  populateUserTable(data.users);
}

function populateUserBadges(data)
{
  //to add badges use .userBadges
  $( ".userBadges" ).empty();
  data.badges.map(function(d) {
    var bagesDiv = $( ".userBadges" );
    var imgSrc="";
    switch(d) {
     case 'Master Coder':  // if (x === 'value1')
       imgSrc="/img/master_coder.PNG";
       break;
     case 'Master Reviewer':  // if (x === 'value2')
       imgSrc="/img/master_reviewer.PNG";
       break;
     case 'Super Star':  // if (x === 'value3')
       imgSrc="/img/super_star.PNG";
       break;
     case 'Best Coder':  // if (x === 'value3')
       imgSrc="/img/best_coder.PNG";
       break;
     default:
       imgSrc="";
       break;
   }

    var html="<div class='card cardBadge'>"+
             "<img class='card-img-top cardImage' src=" + imgSrc +" alt='" + d + "Card image cap'>"+
             "<div class='card-body'>" +
             "<h7 class='card-title'>" + d + "</h7>" +
             "</div>" +
             "</div>";
   bagesDiv.append(html);
  }
  );
}

function getDashboardObj(key){
  console.log(data);
  return data.filter(function(d){return d.period.id == key})[0];
}

function generateCardHtml(str){
  var imgSrc=getImgSrc(str);

  var html="<div class='card cardBadge'>"+
           "<img class='card-img-top cardImage' src=" + imgSrc +" alt='" + str + "'>"+
           "<div class='card-body'>" +
           "<h7 class='card-title'>" + str + "</h7>" +
           "</div>" +
           "</div>";
  return html;
}

function getImgSrc(str){
  var imgSrc="";
  switch(str) {
     case 'Master Coder':
       imgSrc="/img/master_coder.PNG";
       break;
     case 'Master Reviewer':
       imgSrc="/img/master_reviewer.PNG";
       break;
     case 'Super Star':
       imgSrc="/img/super_star.PNG";
       break;
     case 'Best Coder':
       imgSrc="/img/best_coder.PNG";
       break;
     default:
       imgSrc="";
       break;
 }
 return imgSrc;
}
