function plotPieChart(data){
  d3.selectAll("#pieChart").remove();

  var margin = {top: 50, right: 20, bottom: 60, left: 250},
      width = 750 - margin.left - margin.right,
      height = 400 - margin.top - margin.bottom,
      innerRadius = 50,
      radius = Math.min(width-100, height) / 2;

  var svg=d3.select(".pieChart")
            .append("svg")
            .attr("width", width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom)
            //.attr("style", "border:1px solid lightgray")
            .attr("id","pieChart");

  const tRight= (width + margin.left + margin.right)/ 2;
  const tDown= (height + margin.top + margin.bottom) / 2;
  g = svg.append("g").attr("transform", "translate(" + tRight + "," + tDown + ")");

  g.append("text")
    .attr("x", 0)
    .attr("y", 0-tDown+margin.top/2)
    //.attr("dy", "1em")
    .attr("text-anchor", "middle")
    .style("font-size", "16px")
    .style("text-decoration", "underline")
    .text("Code quality");

  var color = d3.scaleOrdinal(['#4daf4a','#377eb8','#ff7f00','#984ea3','#e41a1c']);

  // Generate the pie
  var pie = d3.pie()
              .value(function(d){return d.value})
              .padAngle(0.03); //Set up gap between sectors

  // Generate the arcs
  var arc = d3.arc()
            .innerRadius(innerRadius)
            .outerRadius(radius);

  //Generate groups
  var arcs = g.selectAll("arc")
            .data(pie(data))
            .enter()
            .append("g")
            .attr("class", "arc")

  //Draw arc paths
  arcs.append("path")
    .attr("fill", function(d, i) {
        return color(i);
    })
    .attr("d", arc);

  arcs.append("svg:text")                                     //add a label to each slice
      .attr("transform", function(d) {                    //set the label's origin to the center of the arc
                  //we have to make sure to set these before calling arc.centroid
                  d.innerRadius = innerRadius;
                  d.outerRadius = radius;
                  return "translate(" + arc.centroid(d) + ")";        //this gives us a pair of coordinates like [50, 50]
              })
      .attr("text-anchor", "middle")                          //center the text on it's origin
      .text(function(d, i) { return data[i].label; });        //get the label from our original data array

      var legendRectSize = 18;                                  // NEW
      var legendSpacing = 4;                                    // NEW
      console.log(color.domain())
      var legend = g.selectAll('.legend')                     // NEW
        .data(color.domain())                                   // NEW
        .enter()                                                // NEW
        .append('g')                                            // NEW
        .attr('class', 'legend')                                // NEW
        .attr('transform', function(d, i) {                     // NEW
          var height = legendRectSize + legendSpacing;          // NEW
          var offset =  height * color.domain().length / 2;     // NEW
          var horz = -2 * legendRectSize+tRight-230;                       // NEW
          var vert = i * height - offset -tDown+70;                       // NEW
          return 'translate(' + horz + ',' + vert + ')';        // NEW
        });

        legend.append('rect')                                     // NEW
            .attr('width', legendRectSize)                          // NEW
            .attr('height', legendRectSize)                         // NEW
            .style('fill', color)                                   // NEW
            .style('stroke', color);                                // NEW

          legend.append('text')                                     // NEW
            .attr('x', legendRectSize + legendSpacing)              // NEW
            .attr('y', legendRectSize - legendSpacing)              // NEW
            .text(function(d, i) { return transformLegend(data[i].label); });

            function transformLegend(str){
                var legend="";
                switch(str.toUpperCase()) {
                   case 'GOOD':
                     legend="less 10% of annotation";
                     break;
                   case 'MEDIUM':
                     legend="from 10% of annotation to 20%";
                     break;
                   case 'BAD':
                     legend="more 10% of annotation";
                     break;
                   default:
                     legend="";
                     break;
               }
               return legend;
            }
}
