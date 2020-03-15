<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>数据库查看</title>
</head>

<body>
	
	<h3>user表</h3>
	<?php
	error_reporting(0); //禁止显示PHP警告提示
	//连接数据库
	include('conn.php');
	
	
	//从数据库中查找
	$sql = "select * from user ";
	$result = mysqli_query($link,$sql) or die('mysql query error');
	while($row = mysqli_fetch_array($result)){
		echo '用户ID：',$row[id],'<br />';
		echo '用户名：',$row[name],'<br />';
		echo '<br />';
	}
    mysqli_close($link); //关闭连接
	?>
	</body>

</html>