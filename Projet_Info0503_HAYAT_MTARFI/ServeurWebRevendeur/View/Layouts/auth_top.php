<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>SALE - Système d'Achat de L'Energie</title>

    <!-- Favicons -->
    <link rel="apple-touch-icon" sizes="180x180" href="image/favicon/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="image/favicon/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="image/favicon/favicon-16x16.png">
    <link rel="manifest" href="image/favicon/site.webmanifest">

    <!-- Main CSS File -->
    <link rel="stylesheet" href="CSS/auth.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css">
</head>

<body>
    <div class="limiter">
        <div class="container-login100">
            <div class="wrap-login100">
            <!-- <a class="navbar-brand m-0" href="/">
                <img src="image/logo.png" class="navbar-brand-img h-100" alt="main_logo">
                <span class="ms-1 font-weight-bold">SALE</span></a> -->
                <div class="login100-pic js-tilt">
                    <?php if ($_SERVER['REQUEST_URI'] === '/login') : ?>
                        <img src="image/landing_img6.svg" alt="IMG">
                    <?php else : ?>
                        <img src="image/landing_img4.svg" alt="IMG">
                    <?php endif; ?>
                </div>