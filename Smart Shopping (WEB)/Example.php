<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title> PHP Tutorial </title>
</head>

<body>
<?php
require_once './vendor/autoload.php';
use Kreait\Firebase\Factory;
use Kreait\Firebase\ServiceAccount;

$serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/secret/php-tutorial-ce2d1-6a8a38a5125c.json');

$firebase = (new Factory)

    ->withServiceAccount($serviceAccount)
    
    ->create();

$database = $firebase->getDatabase();
die(print_r($database));
?>
</body>
</html>