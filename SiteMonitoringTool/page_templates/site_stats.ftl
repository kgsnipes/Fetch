<html>
<head>

<meta http-equiv="refresh" content="30">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">



<title>Fetch - Site Monitoring Tool</title>
	<style>
		table thead tr td
		{
			font-weight:bold;
		}
		label.bold
		{
			font-weight:bold;
		}
	</style>
	
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
	
</head>
<body>
	<div class="container">
		<div class="col-md-12 col-lg-12 col-xs-12 col-sm-12">
		<h1>Fetch - Site Monitoring Statistics</h1>
		<h3>Statistics so far..</h3>
		
		<#list stats as stat>
		<div class="col-md-7 col-lg-7 col-xs-7 col-sm-7">
			<table class="table table-striped  table-condensed table-hover">
			<thead>
				<tr>
					<td>Site URL : ${stat.URI}</td>
					<td></td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Polled </td><td>${stat.pollCount} times</td>
				</tr>
				<tr>
					<td>Success Percentage </td><td><label class="bold">${stat.successPercentage} %</label></td>
				</tr>
				<tr>
					<td>Failure Percentage </td><td><label class="bold">${stat.errorPercentage} % </label></td>
				</tr>
				<tr>
					<td>Latest Failure </td><td><#if stat.lastFailurePoint??>  ${stat.lastFailurePoint?datetime} <#else> NA </#if></td>
				</tr>
				<tr>
					<td>Latest Failure Message </td><td><#if stat.lastFailureMessage??>  ${stat.lastFailureMessage} <#else> NA </#if></td>
				</tr>
				<tr>
					<td>Average latency </td><td>${stat.averageLatency} Seconds</td>
				</tr>
			<tbody>	
			</table>
			</div>
			<div class="col-md-5 col-lg-5 col-xs-5 col-sm-5">
				<div id="chartRender${stat_index}"></div>
			</div>
		</#list>
	</div>
	
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
	<script>
		var allChartTables=[];
		function drawChart()
		{
			$.each(allChartTables,function(k,v){
			
				v();
			});
		}
		
		
		 google.load('visualization', '1.0', {'packages':['corechart']});

	      // Set a callback to run when the Google Visualization API is loaded.
	      google.setOnLoadCallback(drawChart);
      
      
	</script>
	<script>
	<#list stats as stat>
	
			allChartTables.push(function(){
			
			var data = new google.visualization.DataTable();
	        data.addColumn('string', 'Hits/Misses');
	        data.addColumn('number', 'Percentage');
	      
	        data.addRows([
	          ['Success', ${stat.successPercentage}],
	          ['Failure', ${stat.errorPercentage}],
	     
	        ]);
	
	        // Set chart options
	        var options = {'title':'Hits/Misses',
	                       'width':400,
	                       'height':300};
	
	        // Instantiate and draw our chart, passing in some options.
	        var chart = new google.visualization.PieChart(document.getElementById("chartRender${stat_index}"));
	        chart.draw(data, options);
			
			});
					
		</#list>
	</script>
</body>
</html>