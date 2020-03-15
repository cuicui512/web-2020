<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>连接数据库</title>
</head>

<body>
	<?php
	$link =mysqli_connect("localhost","web","123456","web");
	if (!$link) {
		die ('连接失败：' . mysqli_error());
	}
	//echo '连接数据库成功！<br>';
	//echo '服务器信息：<br>' .mysqli_get_host_info($link);
	//mysqli_close($link); //关闭连接,从别的页面调用之后在其页尾关闭
	?>
</body>
</html>