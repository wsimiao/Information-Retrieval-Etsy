<%@ page language="java" pageEncoding="gb2312"%>
<%@ page contentType="text/html;charset= gb2312"%>
<!DOCTYPE html >
<html>
<head>
	<link href="./css/bootstrap.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html charset=UTF8">
<title>Etsy Search</title>
<style>
body{
	background-image: url("./images/etsy.jpg");
	background-size: 100%;
}
.etsy_form{
	margin-top:500px;
	margin-left:500px;
}
</style>
</head>
<body>
<div class="etsy_form">
<form class="navbar-form navbar-left" role="search" action="Query" method ="post">
	<div class="form-group">
		<input type="text" name="SearchInput" class="form-control col-md-6" placeholder="Search">
	</div>
	<button type="submit" class="btn btn-default">Submit</button>
</form>
</div>
</body>
</html>