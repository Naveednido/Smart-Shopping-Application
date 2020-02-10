<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title> User Class </title>
</head>

<body>
<?php
require_once './vendor/autoload.php';
use Kreait\Firebase\Factory;
use Kreait\Firebase\ServiceAccount;

$database;
class Users 
{
	public $name;
	public $regno;
	//public $database;
	//public $dbname='users';
	public function __construct()
	{
		$acc = ServiceAccount::fromJsonFile(__DIR__.'/secret/php-tutorial-ce2d1-6a8a38a5125c.json');
		$firebase = (new Factory)->withServiceAccount($acc)->create();
		$this->database=$firebase->getDatabase();
	}
	
	public function setName(string $name)
	{
		$this->name = $name;
	}
	
	public function setRegno(int $regno)
	{
		$this->regno = $regno;
	}
	
	public function insert(object $data)
	{
		if (empty($data)||!isset($data))
		{
			return false;
		}
		
		else
		{
			$this->database->getReference('users')->getChild($this->regno)->set($data);
			return true;
		}
	}
	public function delete(int $value)
	{
		/*if (empty($value)||!isset($value))
		{
			return false;
		}*/
		
			$this->database->getReference('users')->getChild($this->value)->remove();
			return true;
		
		/*else
		{
			return false;
		}*/
	}
}
	
	$users = new Users();
	$users->setName('Naveed');
	$users->setRegno(3193);
	var_dump($users->insert($users));
	//var_dump($users->delete(3193));
?>
</body>
</html>