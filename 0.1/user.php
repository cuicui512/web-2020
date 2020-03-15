<?php
session_start();     //它通过在服务器上存储用户信息以便随后使用,离开网站后删除,一般放在HTML前面
?><!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>user文件</title>
</head>

<body>
	<h3>user用户中心</h3>
	<?php
	//error_reporting(0); //禁止显示PHP警告提示

	//连接数据库
	include('conn.php');

	$id = $_SESSION['id'];
	echo '欢迎您'.$id.'<br />';
    unset($_SESSION['id']);
	//从数据库获取信息
	$sql = "select * from user where id = '$id' ";
	$result = mysqli_query($link,$sql) or die('mysql query error');
	while($row = mysqli_fetch_array($result)){
		echo 'ID：',$row[id],'<br />';
		echo '用户名：',$row[name],'<br />';
		echo '现在是北京时间：'.date('Y-m-d H:i:s'),'<br />';
        echo '刷新可隐藏信息,10s后自动隐藏','<br />';

	}
	

	mysqli_close($link); //关闭连接
	?>
	<a href="index.html"target='_blank'>首页</a>      <!--跳转到首页-->
</body>
<meta http-equiv="refresh" content="10">        <!--定时10s刷新页面-->
</html>