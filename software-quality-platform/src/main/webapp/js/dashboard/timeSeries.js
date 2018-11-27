function plotTimeSeries(progressData,goalData) {
  //Clear all previous timeSeries
  d3.selectAll("#timeSeries").remove();

  var margin = {top: 50, right: 20, bottom: 100, left: 90},
      width = 600 - margin.left - margin.right,
      height = 500 - margin.top - margin.bottom;

  var parseTime = d3.timeParse("%d.%m.%Y");
  //data.map(function(d){console.log(parseTime(d.date));console.log("y=",d.value)});

  // X-axis for time
  var x = d3.scaleTime()
      .domain(d3.extent(progressData, function(d) { return parseTime(d.date) }))
      .range([0, width]);

  // Y-axis for data
  var y = d3.scaleLinear()
      //.domain(d3.extent(data, function(d) { return d.value })) //automatic domain set to min max of data.value
      .domain([0,100])
      .range([height, 0]);

  //Set scale for X-axis
  var xAxis = d3.axisBottom(x)
                .ticks(d3.timeWeek )
                .tickFormat( d3.timeFormat("%b %d"));

  //Set scalefor Y-axis
  var yAxis = d3.axisLeft(y);


  //Set up svg element
  var svg = d3.select(".timeSeries").append("svg")
      .attr("width", width + margin.left + margin.right)
      .attr("height", height + margin.top + margin.bottom)
      //.attr("style", "border:1px solid lightgray")
      .attr("id","timeSeries");

  var g = svg.append("g")
      .attr("transform",
            "translate(" + margin.left + "," + margin.top + ")"
      );

  //draw X-axis
  g.append("g").call(xAxis)
    .attr("transform", "translate(0," + height + ")")
    .append("text") //add label
    .attr("fill", "#000")
    .attr("x", width/2 )
    .attr("y", 30 )
    .style("text-anchor", "middle")
    .text("Period");

  //draw Y-axis
  g.append("g")
    .call(yAxis)
    .append("text") //add label
    .attr("fill", "#000")
    .attr("transform", "rotate(-90)")
    .attr("y", 0 - margin.left/2)
    .attr("x",0 - (height / 2))
    .attr("dy", "1em")
    .attr("text-anchor", "middle")
    .text("# of lines accepted, %");

  var progressLine = d3.line()
     .x(function(d) { return x( parseTime(d.date) )})
     .y(function(d) { return y( d.value)})
     .curve(d3.curveCatmullRom.alpha(1));

  var goalLine = d3.line()
      .x(function(d) { return x( parseTime(d.date) )})
      .y(function(d) { return y( d.value)})
      .curve(d3.curveCatmullRom.alpha(1));

  g.append("path")
       .datum(progressData)
       .attr("class", "line")
       .attr("fill", "none")
       .attr("stroke", "steelblue")
       .attr("stroke-linejoin", "round")
       .attr("stroke-linecap", "round")
       .attr("stroke-width", 1.5)
       .attr("d", progressLine);

  g.append("path")
      .datum(goalData)
      .attr("class", "line")
      .attr("fill", "none")
      .attr("stroke", "red")
      .attr("stroke-linejoin", "round")
      .attr("stroke-linecap", "round")
      .attr("stroke-width", 1.5)
      .attr("d", goalLine);

  // Add the scatterplot
  g.append("g").selectAll("dot")
     .data(progressData)
     .enter()
              .append("circle")
              .attr("cx", function(d) { return x(parseTime(d.date)) })
              .attr("cy", function(d) { return y(d.value) })
              .attr("r", 3.5)
              .attr("style","stroke: blue; fill: white");

  // Add line helpers
  g.append("g").selectAll("dashLine")
    .data(goalData)
    .enter()
                .append("line")
                .attr("x1", function(d) { return x(parseTime(d.date)) })
                .attr("y1", function(d) { return y(d.value) })
                .attr("x2", function(d) { return x(parseTime(d.date)) })
                .attr("y2", height)
                .attr("stroke-width", 2)
                .attr("stroke-width",1)
                .attr("stroke-dasharray",4)
                .attr("style","stroke: rgb(138, 195, 235)");

  g.append("text")
      .attr("x", (width / 2))
      .attr("y", 0 - (margin.top / 2))
      .attr("text-anchor", "middle")
      .style("font-size", "16px")
      .style("text-decoration", "underline")
      .text("Progress vs Date Graph");

//<rect x="50" y="20" width="150" height="150"
var legend = g.append("g")
  .attr("class","legend")
  //.attr("transform","translate("+width/2+","+height+50+")");
  .attr("transform","translate("+(0)+","+(height+30)+")");

  legend.append("g")
        .attr("class","legend")
        .style("font-size","12px")
        .style("fill","steelblue")
        .append("rect")
        .attr("transform","translate(5,5)")
          .attr("x","10")
          .attr("y","10")
          .attr("width","30")
          .attr("height","3");

  legend.append("g")
        .attr("class","legend")
        .style("font-size","12px")
        .style("fill","red")
        .append("rect")
        .attr("transform","translate(5,25)")
          .attr("x","10")
          .attr("y","10")
          .attr("width","30")
          .attr("height","3");

  legend.append("g")
        .attr("class","legend")
        .append("text")
        .attr('x', 50)              // NEW
        .attr('y', 20)              // NEW
        .text("% accepted lines")

  legend.append("g")
        .attr("class","legend")
        .append("text")
        .attr('x', 50)              // NEW
        .attr('y', 40)              // NEW
        .text("% sprint goal")
}
