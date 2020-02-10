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

$storage;
$database;
class Product
{
	public $product_category;
	public $product_name;
	public $product_id;
	public $imageurl;
	public $product_price;
	public $product_description;
	
	public function __construct()
	{
		$acc = ServiceAccount::fromJsonFile(__DIR__.'/secret/smartshoppingapplication-4164e-da95c6bf84e8.json');
		$firebase = (new Factory)->withServiceAccount($acc)->create();
		$this->storage = $firebase->getStorage();
		$this->database = $firebase->getDatabase();
		
		$imageName = $_POST['pimage'];
		$imagefile = file_get_contents(__DIR__.'/Images/'.$imageName);
		$uploadedObject = $this->storage->getBucket()->upload($imagefile, [
		'name' => $imageName
		]);
		
		$expiresAt = new \DateTime('tomorrow');
		$this->imageurl = $uploadedObject->signedUrl($expiresAt).PHP_EOL;
		// Direct access
		//$this->imageurl = $this->storage->getBucket()->object($imageName)->signedUrl($expiresAt);
	}
	
	public function setCategory(string $product_category)
	{
		$this->product_category = $product_category;
	}
	
	public function setName(string $product_name)
	{
		$this->product_name = $product_name;
	}
	
	public function setId(string $product_id)
	{
		$this->product_id = $product_id;
	}
	
	public function setURL(string $imageurl)
	{
		$this->imageurl = $imageurl;
	}
	
	public function setPrice(string $product_price)
	{
		$this->product_price = $product_price;
	}
	
	public function setDescription(string $product_description)
	{
		$this->product_description = $product_description;
	}
	
	public function insertProduct(object $data)
	{
		if (empty($data)||!isset($data))
		{
			echo "Invalid Data";
		}
		
		else
		{
			session_start();
			$temp1 = $_SESSION['Category'];
			$temp2 = $_SESSION['Brand'];
			if ($temp1=="Wearable")
			{
				$this->database->getReference('Products')->getChild($temp1)->getChild($temp2)->getChild($this->product_category)->getChild($this->product_id)->set($data);
			header("Location:wearable.html");
			}
			elseif ($temp1=="Electronics")
			{
				$this->database->getReference('Products')->getChild($temp1)->getChild($temp2)->getChild($this->product_category)->getChild($this->product_id)->set($data);
			header("Location:electronics.html");
			}
			
		}
	}
}

    $products = new Product();
	$products->setCategory($_POST['pcategory']);
	$products->setName($_POST['pname']);
	$products->setId($_POST['pid']);
	$products->setPrice($_POST['pprice']);
	$products->setDescription($_POST['pdesc']);
	var_dump($products->insertProduct($products));
?>
</body>
</html>