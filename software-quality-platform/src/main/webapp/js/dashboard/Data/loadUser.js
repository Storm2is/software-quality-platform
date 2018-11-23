// var data = $.get("/dashboard/getUserData")
var data = [
  {
    period : {id : 1, name: "Period 1", start : "10.01.2018", end : "20.01.2018"},
    uploadedFiles : 10, reviewedFiles : 8,
    badges : ["Master Coder","Master Reviewer","Super Star","Best Coder"],
    quality : [ { label : "good", value : 10}, { label : "medium", value : 15} , { label : "bad", value : 75 }],
    sprintGoal : [{value : 0, date : "10.01.2018"}, {value : 50, date : "20.01.2018"}],
    acceptedCode : [{value : 0, date : "10.01.2018"}, {value : 50, date : "20.01.2018"}]
  },
  {
    period : {id : 2, name: "Period 2", start : "21.01.2018", end : "10.02.2018"},
    uploadedFiles : 20, reviewedFiles : 18,
    badges : ["Master Reviewer"],
    quality : [ { label : "good", value : 30}, { label : "medium", value : 25} , { label : "bad", value : 45 }],
    sprintGoal : [ {value : 0, date : "10.01.2018"}, {value : 50, date : "20.01.2018"}, {value : 60, date : "10.02.2018"}],
    acceptedCode : [ {value : 0, date : "10.01.2018"}, {value : 50, date : "20.01.2018"}, {value : 55, date : "10.02.2018"}]
  },
  {
    period : {id : 3,  name: "Period 3", start : "11.02.2018", end : "22.02.2018"},
    uploadedFiles : 5, reviewedFiles : 2,
    badges : ["Master Coder","Super Star"],
    quality : [ { label : "good", value : 50}, { label : "medium", value : 15} , { label : "bad", value : 35 }],
    sprintGoal : [{value : 0, date : "10.01.2018"}, {value : 50, date : "20.01.2018"}, {value : 60, date : "10.02.2018"}, {value : 90, date : "22.02.2018"}],
    acceptedCode : [{value : 0, date : "10.01.2018"}, {value : 50, date : "20.01.2018"}, {value : 55, date : "10.02.2018"}, {value : 80, date : "22.02.2018"}]
  }
];
