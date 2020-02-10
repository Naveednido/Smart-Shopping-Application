<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
</head>

<body>
<?php
require_once './vendor/autoload.php';
use Kreait\Firebase\Factory;
use Kreait\Firebase\ServiceAccount;

set_time_limit(50);

$auth;
$storage;
$database;
class Admin
{
	public $username;
	public $email;
	public $brand_category;
	public $brand;
	public $brand_image;
	public $mobile;
	public $address;
	public function __construct()
	{
		$acc = ServiceAccount::fromJsonFile(__DIR__.'/secret/smartshoppingapplication-4164e-da95c6bf84e8.json');
		$firebase = (new Factory)->withServiceAccount($acc)->create();
		$this->auth = $firebase->getAuth();
		$this->storage = $firebase->getStorage();
		$this->database = $firebase->getDatabase();
		
		
			$Email = $_POST['email'];
            $Password = $_POST['password'];
            $user = $this->auth->createUserWithEmailAndPassword($Email,$Password);
			if ($user)
			{
				$imageName = $_POST['aimage'];
				$imagefile = file_get_contents(__DIR__.'/Images/'.$imageName);
				$uploadedObject = $this->storage->getBucket()->upload($imagefile, [
				"name" => $imageName
				]);
				$expiresAt = new \DateTime('tomorrow');
				$this->brand_image = $uploadedObject->signedUrl($expiresAt).PHP_EOL;
				// Direct access
				//$this->brand_image = $this->storage->getBucket()->object($imageName)->signedUrl($expiresAt);
			}
	}
	
	public function setEmail(string $email)
	{
		$this->email = $email;
	}
	
	public function setUsername(string $username)
	{
		$this->username = $username;
	}
	
	public function setCategory(string $brand_category)
	{
		$this->brand_category = $brand_category;
	}
	
	public function setBrand(string $brand)
	{
		$this->brand = $brand;
	}
	
	public function setBrandImage(string $brand_image)
	{
		$this->brand_image = $brand_image;
	}
	
	public function setMobile(string $mobile)
	
	{
		$this->mobile = $mobile;
	}
	
	public function setAddress(string $address)
	{
		$this->address = $address;
	}
	
	public function insert(object $data)
	{
		if (empty($data)||!isset($data))
		{
			echo "Invalid Data";
			//return false;
		}
		
		else
		{
			$this->database->getReference('Admin')->getChild($this->brand)->set($data);
			header("Location:login.html");
			//return true;
		}
	}
}
	
	$admin = new Admin();
	$admin->setUsername($_POST['username']);
	$admin->setEmail($_POST['email']);
	$admin->setCategory($_POST['bcategory']);
	$admin->setBrand($_POST['brand']);
	$admin->setMobile($_POST['mobile']);
	$admin->setAddress($_POST['address']);
	var_dump($admin->insert($admin));
?>
</body>
</html>