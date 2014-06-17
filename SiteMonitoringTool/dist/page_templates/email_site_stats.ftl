<html>
<head>

<title>Fetch - Site Monitoring Tool</title>
	
	
</head>
<body>
	
		<h1>Fetch - Site Monitoring Statistics</h1>
		<h3>Statistics so far..</h3>
		
		<#list stats as stat>
		
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
				<#if stat.errorLogs??>
					<tr>
						<td colspan="2">
						<label class="bold">Error Logs: </label>
								<ul>
									<#list stat.errorLogs as err>
										<li><label class="bold">${err.time?datetime} - </label>${err.message}</li>
									</#list>
								</ul>						
						</td>
					</tr>
				</#if>
			<tbody>	
			</table>
			
		</#list>
	</div>
	
</body>
</html>