<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title> UpdateProcess </title>
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

if ($temp1 == "Wearable")
{
	$database->getReference('Products')->getChild($temp1)->getChild($temp2)->getChild($_POST['pcategory'])->getChild($_POST['pid'])->getChild('product_price')->set($_POST['nprice']);
	$database->getReference('Products')->getChild($temp1)->getChild($temp2)->getChild($_POST['pcategory'])->getChild($_POST['pid'])->getChild('product_description')->set($_POST['ndesc']);
    header("Location:updateWearable.html");
}
elseif ($temp1 == "Electronics")
{
	$database->getReference('Products')->getChild($temp1)->getChild($temp2)->getChild($_POST['pcategory'])->getChild($_POST['pid'])->getChild('product_price')->set($_POST['nprice']);
	$database->getReference('Products')->getChild($temp1)->getChild($temp2)->getChild($_POST['pcategory'])->getChild($_POST['pid'])->getChild('product_description')->set($_POST['ndesc']);
    header("Location:updateElectronics.html");
}

?>
</body>
</html>