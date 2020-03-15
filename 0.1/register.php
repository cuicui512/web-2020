<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>注册文件</title>
</head>

<body>
	<h3>注册界面</h3>
	<form action="register.php" method="post">  <!--action是表格数据传到哪个页面-->
	手机号：<input type="text" name="id"><br />  <!--text属性输入文本-->
	用户名：<input type="text" name="name"><br />  <!--text属性输入文本-->
	密码：<input type="password" name="passwd"><br />
	重复密码：<input type="password" name="repasswd"><br />   <!--password属性隐藏输入内容-->
	<input type="submit" value="提交">	
	</form>
	<?php
	//连接数据库
	include('conn.php');
	
	//判断密码是否一致
	if(trim($_POST['passwd']) != trim($_POST['repasswd'])){   //trim() 函数移除字符串两侧的空白字符
		exit('两次密码不一致,请重新填写');
		}
	
	
	
	//从表格获取数据
	$id = trim($_POST['id']);
	$name = trim($_POST['name']);
	$passwd = trim($_POST['passwd']);  	
	
	
	
	//将数据写入数据库
	$sql = "insert into user(id,name,passwd) values( '$id','$name','$passwd')";
	$result = mysqli_query($link,$sql);
	if($result){
		echo ('注册成功，用户ID：'.$id);
		//echo "<script>location.href='sign.php';</script>";   //自动跳转到登录页面
	}else{
		echo '未注册';
	}
	
	mysqli_close($link); //关闭连接

	?>
	<a href="signin.php"target='_self'>登录</a>      <!--点击跳转到signin.php页面,在本页面打开-->
</body>
</html>