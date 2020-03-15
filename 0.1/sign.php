<?php
ob_start();
session_start();    //它通过在服务器上存储用户信息以便随后使用,离开网站后删除
?>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>登录check文件</title>
</head>
<body>
<h3>登录check界面</h3>

<?php
//连接数据库
include('conn.php');

//从表格获取数据
$id = trim($_POST['id']);
$passwd = trim($_POST['passwd']);


$_SESSION['id'] = $id;
if($id==' '){
    echo"用户名不能为空！";
}
elseif ($passwd==' '){
    echo"密码不能为空！";
}
else {

    //从数据库获取数据
    $sql = "select passwd from user where id = '$id' ";
    $result = mysqli_query($link, $sql);
    $row = mysqli_fetch_array($result);     //从结果集中取得一行作为关联数组，或数字数组，或二者兼有

    if ($row[passwd] == $passwd) {

       // echo "<script>location.href='user.php?id=$id';</script>";   //跳转到user.php用户中心页面
        header("Location:user.php");	//跳转到用户中心页面

        echo "密码正确,登陆成功！";
    } else {
        echo '登录失败,请检查密码或用户名是否正确';
    }
}
mysqli_close($link); //关闭连接
ob_end_flush();
?>
</body>
</html>