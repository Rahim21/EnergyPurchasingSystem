session_start();
if(!issset($_SESSION['login_session'])){
header("Location:login.php");
exit();
}