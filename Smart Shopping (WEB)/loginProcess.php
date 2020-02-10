<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
</head>

<body>
<?php
require_once './vendor/autoload.php';
use Kreait\Firebase\Factory;
use Kreait\Firebase\ServiceAccount;

set_time_limit(50);

$acc = ServiceAccount::fromJsonFile(__DIR__.'/secret/smartshoppingapplication-4164e-da95c6bf84e8.json');
$firebase = (new Factory)->withServiceAccount($acc)->create();
$auth = $firebase->getAuth();
$database = $firebase->getDatabase();

$email = $_POST['email'];
$password = $_POST['password'];

$user = $auth->verifyPassword($email,$password);
if ($user)
{
	$value1;
	$value2;
	$data = $database->getReference('Admin')->getValue();
	$i = 0;
	foreach($data as $key => $data1)
	{
		if ($email == $data1['email'])
		{
			$value1 = $data1['brand_category'];
			$value2 = $data1['brand'];
		}
		$i++;
	}
	session_start();
	$_SESSION['Category']= $value1;
	$_SESSION['Brand']= $value2;
	if ($value1 == "Wearable")
	{
		header("Location:wearable.html");
	}
	elseif ($value1 == "Electronics")
	{
		header("Location:electronics.html");
	}
	else
	{
		header("Location:login.html");
	}
	//$_SESSION['admin']=true;
}
else
{
	//$_SESSION['admin']=false;
	header("Location:login.html");
}

?>
</body>
</html>