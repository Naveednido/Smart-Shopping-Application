<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title> saleProcess </title>
</head>

<body>
<?php
require_once './vendor/autoload.php';
use Kreait\Firebase\Factory;
use Kreait\Firebase\ServiceAccount;

set_time_limit(50);

$storage;
$database;
class Sale
{
	public $sale_type;
	public $sale_image;
	public $price_off;
	public $start_date;
	public $end_date;
	
	public function __construct()
	{
		$acc = ServiceAccount::fromJsonFile(__DIR__.'/secret/smartshoppingapplication-4164e-da95c6bf84e8.json');
		$firebase = (new Factory)->withServiceAccount($acc)->create();
		$this->storage = $firebase->getStorage();
		$this->database = $firebase->getDatabase();
		
		$imageName = $_POST['simage'];
		$imagefile = file_get_contents(__DIR__.'/Images/'.$imageName);
		$uploadedObject = $this->storage->getBucket()->upload($imagefile, [
		'name' => $imageName
		]);
		
		$expiresAt = new \DateTime('tomorrow');
		$this->sale_image = $uploadedObject->signedUrl($expiresAt).PHP_EOL;
		// Direct access
		//$this->sale_image = $this->storage->getBucket()->object($imageName)->signedUrl($expiresAt);
	}
	
	public function setType(string $sale_type)
	{
		$this->sale_type = $sale_type;
	}
	
	public function setURL(string $sale_image)
	{
		$this->sale_image = $sale_image;
	}
	
	public function setPriceOff(string $price_off)
	{
		$this->price_off = $price_off;
	}
	
	public function setStartDate(string $start_date)
	{
		$this->start_date = $start_date;
	}
	
	public function setEndDate(string $end_date)
	{
		$this->end_date = $end_date;
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
				$this->database->getReference('Sale')->getChild($temp2)->set($data);
				header("Location:saleWearable.html");
			}
			elseif ($temp1=="Electronics")
			{
				$this->database->getReference('Sale')->getChild($temp2)->set($data);
				header("Location:saleElectronics.html");
			}
			
		}
	}
}

    $sale = new Sale();
	$sale->setType($_POST['stype']);
	$sale->setPriceOff($_POST['poff']);
	$sale->setStartDate($_POST['sdate']);
	$sale->setEndDate($_POST['edate']);
	var_dump($sale->insertProduct($sale));
?>
</body>
</html>