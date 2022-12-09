<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>SALE - Système d'Achat de L'Energie</title>

    <!-- Favicons -->
    <link rel="apple-touch-icon" sizes="180x180" href="image/favicon/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="image/favicon/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="image/favicon/favicon-16x16.png">
    <link rel="manifest" href="image/favicon/site.webmanifest">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

    <!-- Vendor CSS Files -->
    <link href="assets/vendor/aos/aos.css" rel="stylesheet">
    <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="assets/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
    <link href="assets/vendor/remixicon/remixicon.css" rel="stylesheet">
    <link href="assets/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">

    <!-- Main CSS File -->
    <link rel="stylesheet" href="CSS/landing.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css">

</head>

<body>
    <!-- ======= Header ======= -->
    <header id="header" class="header fixed-top" style="background-color: transparent;">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
            $(window).scroll(function() {
                if ($(this).scrollTop() > 10) {
                    $('#header').addClass('header-scrolled');
                } else {
                    $('#header').removeClass('header-scrolled');
                }
            });
        </script>
        <div class="container-fluid container-xl d-flex align-items-center justify-content-between">

            <a href="" class="logo d-flex align-items-center">
                <img src="image/logo.png" alt="">
                <span>SALE</span>
            </a>

            <nav id="navbar" class="navbar">
                <ul>
                    <li><a class="nav-link scrollto active" href="#hero">Accueil</a></li>
                    <li><a class="nav-link scrollto" href="#services">Services</a></li>
                    <li><a class="nav-link scrollto" href="#team">Equipe</a></li>

                    <?php if ($user) : ?>
                        <li><a class="getstarted scrollto" href="login">Accéder au site</a></li>
                    <?php else : ?>
                        <li><a class="nav-link scrollto" href="register">S'inscrire</a></li>
                        <li><a class="getstarted scrollto" href="login">Se connecer</a></li>
                    <?php endif; ?>
                </ul>
                <i class="bi bi-list mobile-nav-toggle"></i>
            </nav><!-- .navbar -->

        </div>
    </header><!-- End Header -->

    <!-- ======= Hero Section ======= -->
    <section id="hero" class="hero d-flex align-items-center">

        <div class="container">
            <div class="row">
                <div class="col-lg-6 d-flex flex-column justify-content-center">
                    <h1 data-aos="fade-up">Choisissez automatiquement la meilleure offre pour vous en fonction de vos besoins et de vos préférences</h1>
                    <h2 data-aos="fade-up" data-aos-delay="400">Notre solution, un système d'achat d'énergie alimenté par notre algorithme</h2>
                    <div data-aos="fade-up" data-aos-delay="600">
                        <div class="text-center text-lg-start">
                            <a href="#about" class="btn-get-started scrollto d-inline-flex align-items-center justify-content-center align-self-center">
                                <span>Lire la suite</span>
                                <i class="bi bi-arrow-right"></i>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6 hero-img" data-aos="zoom-out" data-aos-delay="200">
                    <img src="image/landing_img2.svg" class="img-fluid" alt="">
                </div>
            </div>
        </div>

    </section><!-- End Hero -->

    <main id="main">
        <!-- ======= About Section ======= -->
        <section id="about" class="about">

            <div class="container" data-aos="fade-up">
                <div class="row gx-0">

                    <div class="col-lg-6 d-flex flex-column justify-content-center" data-aos="fade-up" data-aos-delay="200">
                        <div class="content">
                            <h3>A propos SALE</h3>
                            <h2>Il y a beaucoup d'entreprises qui vendent de l'énergie et il est difficile de savoir quel est le meilleur prix.</h2>
                            <p>
                                Choisit automatiquement la meilleure offre pour vous en fonction de vos besoins et de vos préférences. Il vous aide également à économiser de l'argent grâce à notre outil automatisé d'optimisation énergétique.
                            </p>
                            <div class="text-center text-lg-start">
                                <a href="/home" class="btn-read-more d-inline-flex align-items-center justify-content-center align-self-center">
                                    <span>Découvrir</span>
                                    <i class="bi bi-arrow-right"></i>
                                </a>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-6 d-flex align-items-center" data-aos="zoom-out" data-aos-delay="200">
                        <!-- taille de l'image plus petite avec contour arrondi mais carré et bleu -->
                        <img src="image/PONE2.png" class="img-fluid" style="border-radius: 20%; border: 5px solid #3e52e9;" alt="">
                    </div>

                </div>
            </div>

        </section><!-- End About Section -->

        <!-- ======= Services Section ======= -->
        <section id="services" class="services">

            <div class="container" data-aos="fade-up">

                <header class="section-header">
                    <h2>Services</h2>
                    <p>Salutations ! Nous sommes heureux de vous présenter notre système d'achat de l’énergie.</p>
                </header>

                <div class="row gy-4">

                    <div class="col-lg-4 col-md-6" data-aos="fade-up" data-aos-delay="200">
                        <div class="service-box blue">
                            <img id="landing" src="image/TARE1.png">
                            <p>Notre équipe est composée des meilleurs traders qui travaillent dur pour négocier les tarifs les plus compétitifs et offrir la meilleure qualité possible.</p>
                        </div>
                    </div>

                    <div class="col-lg-4 col-md-6" data-aos="fade-up" data-aos-delay="300">
                        <div class="service-box orange">
                            <img id="landing" src="image/PONE1.png">
                            <p>Nous avons aussi des producteurs qui produisent une variété d’options en matière d’approvisionnement en énergie, afin que nos clients puissent choisir le plan optimal pour leur entreprise ou foyer.</p>
                        </div>
                    </div>

                    <div class="col-lg-4 col-md-6" data-aos="fade-up" data-aos-delay="400">
                        <div class="service-box green">
                            <img id="landing" src="image/TARE3.png">
                            <p>Enfin, nous avons l'une des meilleures équipes techniques du secteur à bord pour garantir un site Revendeur convivial et intuitif qui permet aux consommateurs de faire facilement leur choix parmi toutes les options disponibles.</p>
                        </div>
                    </div>

                    <div class="col-lg-4 col-md-6" data-aos="fade-up" data-aos-delay="500">
                        <div class="service-box red">
                            <img id="landing" src="image/PRODUCTION2.png">
                            <p>nous offrons des solutions en matière d’énergie renouvelable telles que l’utilisation de nos sources d’énergies solaire ou encore notre système eolien qui sont très efficaces pour produire une quantité importante sans impacter notre environnement.</p>
                        </div>
                    </div>

                    <div class="col-lg-4 col-md-6" data-aos="fade-up" data-aos-delay="600">
                        <div class="service-box purple">
                            <img id="landing" src="image/PRODUCTION1.png">
                            <p>Grâce à notre nouveau matériel de pointe, vous pourrez bénéficier des avantages offerts par ces sources alternatives. Notre équipement high-tech vous permettra non seulement de réduire votre facture en matière d'utilisation des ressources naturelles, mais aussi de profiter pleinement du potentiel que ce type particulier peut offrir aux utilisateurs. Nous sommes convaincus qu’avec nos produits modernes et fiables, vos besoins en termes d'approvisionnement en énergie seront satisfaits!</p>
                        </div>
                    </div>

                    <div class="col-lg-4 col-md-6" data-aos="fade-up" data-aos-delay="700">
                        <div class="service-box pink">
                            <img id="landing" src="image/MARCHE.png">
                            <p>Un marché gros qui permet le stockage efficace et fiable à long terme. Notre objectif est simple : fournir aux clients un service personnalisable, abordable et durable pour satisfaire leurs exigences spécifiques en matière d’approvisionnement en énergie !</p>
                        </div>
                    </div>

                </div>

            </div>

        </section><!-- End Services Section -->

        <!-- ======= Team Section ======= -->
        <section id="team" class="team">

            <div class="container" data-aos="fade-up">

                <header class="section-header">
                    <h2>Equipe</h2>
                    <p>Notre équipe</p>
                </header>

                <div class="row gy-4" style="place-content: center;">

                    <div class="col-lg-3 col-md-6 d-flex align-items-stretch" data-aos="fade-up" data-aos-delay="100">
                        <div class="member">
                            <div class="member-img">
                                <img src="assets/img/team/team-1.jpg" class="img-fluid" alt="">
                            </div>
                            <div class="member-info">
                                <h4>Rahim HAYAT</h4>
                                <span>développeur</span>
                                <p><a href=""><i class="bi bi-github">Github</i></a><br>
                                    <a href=""><i class="bi bi-linkedin">Linkedin</i></a><br>
                                    Etudiant en 3ème année de Licence Informatique à l'Université de Reims Champagne-Ardenne
                                </p>
                            </div>
                        </div>
                    </div>


                    <div class="col-lg-3 col-md-6 d-flex align-items-stretch" data-aos="fade-up" data-aos-delay="400">
                        <div class="member">
                            <div class="member-img">
                                <img src="assets/img/team/team-4.jpg" class="img-fluid" alt="">
                            </div>
                            <div class="member-info">
                                <h4>Souhail MTARFI</h4>
                                <span>Développeur </span>
                                <p><a href="https://github.com/Suhail1929"><i class="bi bi-github">Github</i></a><br>
                                    <a href="https://www.linkedin.com/in/souhail-mtarfi-ab2140208/"><i class="bi bi-linkedin">Linkedin</i></a><br>
                                    Etudiant en 3ème année de Licence Informatique à l'Université de Reims Champagne-Ardenne
                                </p>
                            </div>
                        </div>
                    </div>

                </div>

            </div>

        </section><!-- End Team Section -->
    </main>

    <footer id="footer" class="footer">

        <div class="footer-top">
            <div class="container">
                <div class="row gy-4">
                    <div class="col-lg-5 col-md-12 footer-info">
                        <a href="index.html" class="logo d-flex align-items-center">
                            <img src="/image/logo.png" alt="">
                            <span>SALE</span>
                        </a>
                        <p>le meilleur système d'achat de l'énergie.</p>
                        <div class="social-links mt-3">
                            <a href="#" class="twitter"><i class="bi bi-twitter"></i></a>
                            <a href="#" class="facebook"><i class="bi bi-facebook"></i></a>
                            <a href="#" class="instagram"><i class="bi bi-instagram"></i></a>
                            <a href="#" class="linkedin"><i class="bi bi-linkedin"></i></a>
                        </div>
                    </div>

                    <div class="col-lg-2 col-6 footer-links">
                        <h4>Liens utiles</h4>
                        <ul>
                            <li><i class="bi bi-chevron-right"></i> <a href="#">Accueil</a></li>
                            <li><i class="bi bi-chevron-right"></i> <a href="#">About us</a></li>
                            <li><i class="bi bi-chevron-right"></i> <a href="#">Services</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <div class="container">
            <div class="copyright">
                &copy; Copyright <strong><span>SALE</span></strong>. All Rights Reserved
            </div>
        </div>
    </footer>
</body>

</html>