<html>
<head>
	<title>KG - Site Monitor - Email Notification</title>
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
	
</head>
<body>
	<h1>PANIC !! PANIC !! ${stat.URI} IS DOWN</h1>
	<h3>Statistics so far..</h3>
	<table border="1">
			<thead>
				<tr>
					<td colspan="2">Site URL : ${stat.URI}</td>
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
</body>
</html>