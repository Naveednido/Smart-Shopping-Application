<!DOCTYPE html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />
    <link rel="icon" href="img/favicon.png" type="image/png" />
    <title>Wearable Orders</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="css/bootstrap.css" />
    <link rel="stylesheet" href="mystyle/linericon/style.css" />
    <link rel="stylesheet" href="css/font-awesome.min.css" />
    <link rel="stylesheet" href="css/themify-icons.css" />
    <link rel="stylesheet" href="mystyle/owl-carousel/owl.carousel.min.css" />
    <link rel="stylesheet" href="mystyle/lightbox/simpleLightbox.css" />
    <link rel="stylesheet" href="mystyle/nice-select/css/nice-select.css" />
    <link rel="stylesheet" href="mystyle/animate-css/animate.css" />
    <link rel="stylesheet" href="mystyle/jquery-ui/jquery-ui.css" />
    <!-- main css -->
    <link rel="stylesheet" href="css/style.css" />
    <link rel="stylesheet" href="css/responsive.css" />
    
    <style type="text/css">
    
    #form{
           min-height: 700px;
           padding: 5px 40px 40px 40px;
         }
    .registration{
                   font-size: 55px;
                   font-family: Agency FB;
                   font-weight: 700;
                   border-bottom-style: ridge;
                  }
     .text{
            height: 43px;
          }
     label{
            font-size: 18px;
			font-weight: bold;
          }
     .btn-primary{
                   width: 49%;
                 }
     .btn-danger{
                  width: 49%;
                }
	 #para1{
		     font-size: 20px;
		   }
</style>
  </head>

  <body>
  <?php
  session_start();
  $temp1 = $_SESSION['Brand'];
  $temp2 = $_SESSION['Category'];
  ?>
    <!--================Header Menu Area =================-->
    <header class="header_area">
      <div class="top_menu">
        <div class="container">
          <div class="row">
            <div class="col-lg-7">
              <div class="float-left">
                <p>Phone: +923345440746</p>
                <p>email: madmobiledevelopment360@gmail.com</p>
              </div>
            </div>
            <div class="col-lg-5">
              <div class="float-right">
                <ul class="right_side">
                  <li>
                    <a href="cart.html">
                      gift card
                    </a>
                  </li>
                  <li>
                    <a href="#">
                      track order
                    </a>
                  </li>
                  <li>
                    <a href="#">
                      Contact Us
                    </a>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="main_menu">
        <div class="container">
          <nav class="navbar navbar-expand-lg navbar-light w-100">
            <!-- Brand and toggle get grouped for better mobile display -->
            <a class="navbar-brand logo_h" href="index.html">
              <img src="img/mylogo.png" alt="" />
            </a>
            <button
              class="navbar-toggler"
              type="button"
              data-toggle="collapse"
              data-target="#navbarSupportedContent"
              aria-controls="navbarSupportedContent"
              aria-expanded="false"
              aria-label="Toggle navigation"
            >
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div
              class="collapse navbar-collapse offset w-100"
              id="navbarSupportedContent"
            >
              <div class="row w-100 mr-0">
                <div class="col-lg-7 pr-0">
                  <ul class="nav navbar-nav center_nav pull-right">
                    <li class="nav-item">
                      <a class="nav-link" href="index.html">Home</a>
                    </li>
                    <li class="nav-item active submenu dropdown">
                      <a
                        href="#"
                        class="nav-link dropdown-toggle"
                        data-toggle="dropdown"
                        role="button"
                        aria-haspopup="true"
                        aria-expanded="false"
                        >Product</a
                      >
                      <ul class="dropdown-menu">
                        <li class="nav-item">
                          <a class="nav-link" href="wearable.html">Add Product</a>
                        </li>
                        <li class="nav-item">
                          <a class="nav-link" href="removeWearable.html">Remove Product</a>
                        </li>
                        <li class="nav-item">
                          <a class="nav-link" href="updateWearable.html">Update Product</a>
                        </li>
                      </ul>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href="saleWearable.html">Sale</a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href="orderWearable.php">Order</a>
                    </li>
                  </ul>
                </div>

                <div class="col-lg-5 pr-0">
                  <ul class="nav navbar-nav navbar-right right_nav pull-right">
                    <li class="nav-item">
                      <a href="#" class="icons">
                        <i class="ti-search" aria-hidden="true"></i>
                      </a>
                    </li>

                    <li class="nav-item">
                      <a href="#" class="icons">
                        <i class="ti-shopping-cart"></i>
                      </a>
                    </li>

                    <li class="nav-item">
                      <a href="#" class="icons">
                        <i class="ti-user" aria-hidden="true"></i>
                      </a>
                    </li>

                    <li class="nav-item">
                      <a href="#" class="icons">
                        <i class="ti-heart" aria-hidden="true"></i>
                      </a>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </nav>
        </div>
      </div>
    </header>
    <!--================Header Menu Area =================-->

    <!--================Home Banner Area =================-->
    <section class="banner_area">
      <div class="banner_inner d-flex align-items-center">
        <div class="container">
          <div
            class="banner_content d-md-flex justify-content-between align-items-center"
          >
            <div class="mb-3 mb-md-0">
              <h2>User Orders</h2>
              <p>Very us move be blessed multiply night</p>
            </div>
            <div class="page_link">
              <a href="index.html">Home</a>
              <a href="orderWearable.php">User Orders</a>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!--================End Home Banner Area =================-->

    <!--================Tracking Box Area =================-->
    <section class="tracking_box_area section_gap">
        <div class="container">
            <div id="para1">
                <p>All the Orders of Users regarding your Brand displayed here.You can remove these Orders after delivery by pressing the "Remove" button.</p>
                <br>
            </div>
            <div class="col-md-12">
            <form action="deleteOrder.php" method="POST">
                 <table class="table table-bordered">
                       <thead>
                             <tr>
                                 <th scope="col">Remove Order</th>
                                 <th scope="col">User</th>
                                 <th scope="col">Product ID</th>
                                 <th scope="col">Product_Name</th>
                                 <th scope="col">Quantity</th>
                                 <th scope="col">Price</th>
                                 <th scope="col">Date of Order</th>
                                 <th scope="col">Phone</th>
                                 <th scope="col">Address</th>
                             </tr>
                       </thead>
                       <tbody>
                       
                       <?php
                        require_once './vendor/autoload.php';
                        use Kreait\Firebase\Factory;
                        use Kreait\Firebase\ServiceAccount;

                        $acc = ServiceAccount::fromJsonFile(__DIR__.'/secret/smartshoppingapplication-4164e-da95c6bf84e8.json');
                        $firebase = (new Factory)->withServiceAccount($acc)->create();
                        $storage=$firebase->getStorage();
                        $database=$firebase->getDatabase();

                        $data = $database->getReference('Admin_Orders')->getChild($temp1)->getValue();
                        $i = 0;
                        foreach($data as $key => $data1)
                        {
	                       
                       ?>
                       
                             <tr>
                                 <th><button type="submit" value="submit" class="btn submit_btn">Remove</button></th>
                                 <th><?php echo $data1['name']; ?></th>
                                 <th scope="row"><?php $_SESSION['ID']=$data1['product_id'];echo $data1['product_id'];?></th>
                                 <th><?php echo $data1['product_name']; ?></th>
                                 <th><?php echo $data1['quantity']; ?></th>
                                 <th><?php echo $data1['price']; ?></th>
                                 <th><?php echo $data1['Date_of_order']; ?></th>   
                                 <th><?php echo $data1['phone']; ?></th>
                                 <th><?php echo $data1['address']; ?></th>
                             </tr>
                             <?php
							     $i++;
							   }
							 ?>
                       </tbody>
                 </table>
                 </form>
            </div>
        </div>
    </section>
    <!--================End Tracking Box Area =================-->

    <!--================ start footer Area  =================-->
    <footer class="footer-area section_gap">
      <div class="container">
        <div class="row">
          <div class="col-lg-2 col-md-6 single-footer-widget">
            <h4>Top Products</h4>
            <ul>
              <li><a href="#">Managed Website</a></li>
              <li><a href="#">Manage Reputation</a></li>
              <li><a href="#">Power Tools</a></li>
              <li><a href="#">Marketing Service</a></li>
            </ul>
          </div>
          <div class="col-lg-2 col-md-6 single-footer-widget">
            <h4>Quick Links</h4>
            <ul>
              <li><a href="#">Jobs</a></li>
              <li><a href="#">Brand Assets</a></li>
              <li><a href="#">Investor Relations</a></li>
              <li><a href="#">Terms of Service</a></li>
            </ul>
          </div>
          <div class="col-lg-2 col-md-6 single-footer-widget">
            <h4>Features</h4>
            <ul>
              <li><a href="#">Jobs</a></li>
              <li><a href="#">Brand Assets</a></li>
              <li><a href="#">Investor Relations</a></li>
              <li><a href="#">Terms of Service</a></li>
            </ul>
          </div>
          <div class="col-lg-2 col-md-6 single-footer-widget">
            <h4>Resources</h4>
            <ul>
              <li><a href="#">Guides</a></li>
              <li><a href="#">Research</a></li>
              <li><a href="#">Experts</a></li>
              <li><a href="#">Agencies</a></li>
            </ul>
          </div>
          <div class="col-lg-4 col-md-6 single-footer-widget">
            <h4>Newsletter</h4>
            <p>You can trust us. we only send promo offers,</p>
            <div class="form-wrap" id="mc_embed_signup">
              <form target="_blank" action="https://spondonit.us12.list-manage.com/subscribe/post?u=1462626880ade1ac87bd9c93a&amp;id=92a4423d01"
                method="get" class="form-inline">
                <input class="form-control" name="EMAIL" placeholder="Your Email Address" onfocus="this.placeholder = ''"
                  onblur="this.placeholder = 'Your Email Address '" required type="email">
                <button class="click-btn btn btn-default">Subscribe</button>
                <div style="position: absolute; left: -5000px;">
                  <input name="b_36c4fd991d266f23781ded980_aefe40901a" tabindex="-1" value="" type="text">
                </div>
  
                <div class="info"></div>
              </form>
            </div>
          </div>
        </div>
        <div class="footer-bottom row align-items-center">
          <p class="footer-text m-0 col-lg-8 col-md-12"><!-- Link back to SmartShopping can't be removed. Template is licensed under CC BY 3.0. -->
Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="fa fa-heart-o" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">SmartShopping</a>
<!-- Link back to SmartShopping can't be removed. Template is licensed under CC BY 3.0. --></p>
          <div class="col-lg-4 col-md-12 footer-social">
            <a href="#"><i class="fa fa-facebook"></i></a>
            <a href="#"><i class="fa fa-twitter"></i></a>
            <a href="#"><i class="fa fa-dribbble"></i></a>
            <a href="#"><i class="fa fa-behance"></i></a>
          </div>
        </div>
      </div>
    </footer>
    <!--================ End footer Area  =================-->




    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="js/jquery-3.2.1.min.js"></script>
    <script src="js/popper.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/stellar.js"></script>
    <script src="mystyle/lightbox/simpleLightbox.min.js"></script>
    <script src="mystyle/nice-select/js/jquery.nice-select.min.js"></script>
    <script src="mystyle/isotope/imagesloaded.pkgd.min.js"></script>
    <script src="mystyle/isotope/isotope-min.js"></script>
    <script src="mystyle/owl-carousel/owl.carousel.min.js"></script>
    <script src="js/jquery.ajaxchimp.min.js"></script>
    <script src="js/mail-script.js"></script>
    <script src="mystyle/jquery-ui/jquery-ui.js"></script>
    <script src="mystyle/counter-up/jquery.waypoints.min.js"></script>
    <script src="mystyle/counter-up/jquery.counterup.js"></script>
    <script src="js/theme.js"></script>

</body>

</html>