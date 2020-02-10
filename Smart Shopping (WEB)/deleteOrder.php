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
$storage = $firebase->getStorage();
$database = $firebase->getDatabase();

session_start();
$temp1 = $_SESSION['Category'];
$temp2 = $_SESSION['Brand'];
$temp3 = $_SESSION['ID'];

$database->getReference('Admin_Orders')->getChild($temp2)->getChild($temp3)->remove();
if ($temp1 == "Wearable")
{
	header("Location:orderWearable.php");
}
if ($temp1 == "Wearable")
{
	header("Location:orderElectronics.php");
}
?>
</body>
</html>