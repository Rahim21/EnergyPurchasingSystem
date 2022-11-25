<?php
$auth->requireRole('CLIENT');
?>

<body class="g-sidenav-show   bg-gray-100">
    <div class="min-height-300 position-absolute w-100 home-background"></div>
    <!-- Menu -->
    <aside class="sidenav bg-white navbar navbar-vertical navbar-expand-xs border-0 border-radius-xl my-3 fixed-start ms-4 shadow-lg" id="sidenav-main">
        <div class="sidenav-header">
            <i class="fas fa-times p-3 cursor-pointer text-secondary opacity-5 position-absolute end-0 top-0 d-none d-xl-none" aria-hidden="true" id="iconSidenav"></i>
            <a class="navbar-brand m-0" href="/">
                <!-- href chemin home -->
                <img src="image/logo.png" class="navbar-brand-img h-100" alt="main_logo">
                <span class="ms-1 font-weight-bold">SALE</span>
            </a>
        </div>
        <hr class="horizontal dark mt-0">
        <div class="collapse navbar-collapse  w-auto " id="sidenav-collapse-main">
            <ul class="navbar-nav">

                <li class="nav-item mt-3">
                    <h6 class="ps-4 ms-2 text-uppercase text-xs font-weight-bolder opacity-6">Navigation</h6>
                </li>

                <li class="nav-item">
                    <a class="nav-link " href="/">
                        <div class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#4154f1" class="bi bi-house-fill" viewBox="0 0 16 16">
                                <path d="M8.707 1.5a1 1 0 0 0-1.414 0L.646 8.146a.5.5 0 0 0 .708.708L8 2.207l6.646 6.647a.5.5 0 0 0 .708-.708L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.707 1.5Z" />
                                <path d="m8 3.293 6 6V13.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5V9.293l6-6Z" />
                            </svg>
                        </div>
                        <span class="nav-link-text ms-1">Accueil</span>
                    </a>
                </li>

                <li class="nav-item">
                    <a class="nav-link " href="/home">
                        <div class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-buildings-fill" viewBox="0 0 16 16">
                                <path d="M15 .5a.5.5 0 0 0-.724-.447l-8 4A.5.5 0 0 0 6 4.5v3.14L.342 9.526A.5.5 0 0 0 0 10v5.5a.5.5 0 0 0 .5.5h9a.5.5 0 0 0 .5-.5V14h1v1.5a.5.5 0 0 0 .5.5h3a.5.5 0 0 0 .5-.5V.5ZM2 11h1v1H2v-1Zm2 0h1v1H4v-1Zm-1 2v1H2v-1h1Zm1 0h1v1H4v-1Zm9-10v1h-1V3h1ZM8 5h1v1H8V5Zm1 2v1H8V7h1ZM8 9h1v1H8V9Zm2 0h1v1h-1V9Zm-1 2v1H8v-1h1Zm1 0h1v1h-1v-1Zm3-2v1h-1V9h1Zm-1 2h1v1h-1v-1Zm-2-4h1v1h-1V7Zm3 0v1h-1V7h1Zm-2-2v1h-1V5h1Zm1 0h1v1h-1V5Z" />
                            </svg>
                        </div>
                        <span class="nav-link-text ms-1">Marche de Gros</span>
                    </a>
                </li>

                <!-- client : -->
                <?php if ($user->role === 'CLIENT') : ?>
                    <li class="nav-item">
                        <a class="nav-link " href="<?= $router->generate('commande') ?>">
                            <div class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#37b8f0" class="bi bi-box-fill" viewBox="0 0 16 16">
                                    <path fill-rule="evenodd" d="M15.528 2.973a.75.75 0 0 1 .472.696v8.662a.75.75 0 0 1-.472.696l-7.25 2.9a.75.75 0 0 1-.557 0l-7.25-2.9A.75.75 0 0 1 0 12.331V3.669a.75.75 0 0 1 .471-.696L7.443.184l.004-.001.274-.11a.75.75 0 0 1 .558 0l.274.11.004.001 6.971 2.789Zm-1.374.527L8 5.962 1.846 3.5 1 3.839v.4l6.5 2.6v7.922l.5.2.5-.2V6.84l6.5-2.6v-.4l-.846-.339Z" />
                                </svg>
                            </div>
                            <span class="nav-link-text ms-1">Mes commandes</span>
                        </a>
                    </li>
                <?php endif; ?>
                <!-- producteur : -->
                <?php if ($user->role === 'PRODUCTEUR') : ?>
                    <li class="nav-item">
                        <a class="nav-link " href="<?= $router->generate('production') ?>">
                            <div class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#02a1a3" class="bi bi-plugin" viewBox="0 0 16 16">
                                    <path fill-rule="evenodd" d="M1 8a7 7 0 1 1 2.898 5.673c-.167-.121-.216-.406-.002-.62l1.8-1.8a3.5 3.5 0 0 0 4.572-.328l1.414-1.415a.5.5 0 0 0 0-.707l-.707-.707 1.559-1.563a.5.5 0 1 0-.708-.706l-1.559 1.562-1.414-1.414 1.56-1.562a.5.5 0 1 0-.707-.706l-1.56 1.56-.707-.706a.5.5 0 0 0-.707 0L5.318 5.975a3.5 3.5 0 0 0-.328 4.571l-1.8 1.8c-.58.58-.62 1.6.121 2.137A8 8 0 1 0 0 8a.5.5 0 0 0 1 0Z" />
                                </svg>
                            </div>
                            <span class="nav-link-text ms-1">Mes énergies</span>
                        </a>
                    </li>
                <?php endif; ?>

                <li class="nav-item mt-3">
                    <h6 class="ps-4 ms-2 text-uppercase text-xs font-weight-bolder opacity-6">Information Utilisateur</h6>
                </li>

                <li class="nav-item">
                    <a class="nav-link " href="#">
                        <div class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#4154f1" class="bi bi-person-circle" viewBox="0 0 16 16">
                                <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z" />
                                <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z" />
                            </svg>
                        </div>
                        <span class="nav-link-text ms-1">Profile</span>
                    </a>
                </li>

                <li class="nav-item">
                    <a class="nav-link " href="<?= $router->generate('logout') ?>">
                        <div class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#bd0000" class="bi bi-door-open-fill" viewBox="0 0 16 16">
                                <path d="M1.5 15a.5.5 0 0 0 0 1h13a.5.5 0 0 0 0-1H13V2.5A1.5 1.5 0 0 0 11.5 1H11V.5a.5.5 0 0 0-.57-.495l-7 1A.5.5 0 0 0 3 1.5V15H1.5zM11 2h.5a.5.5 0 0 1 .5.5V15h-1V2zm-2.5 8c-.276 0-.5-.448-.5-1s.224-1 .5-1 .5.448.5 1-.224 1-.5 1z" />
                            </svg>
                        </div>
                        <span class="nav-link-text ms-1">Déconnexion</span>
                    </a>
                </li>
            </ul>
        </div>

        <div class="sidenav-footer mx-3 ">
            <div class="card card-plain shadow-none" id="sidenavCard">
                <?php if ($user->role === 'CLIENT') : ?>
                    <img class="w-80 mx-auto" src="image/commande.svg" alt="illustration">
                <?php endif; ?>
                <?php if ($user->role === 'PRODUCTEUR') : ?>
                    <img class="w-80 mx-auto" src="image/wind_turbine.svg" alt="illustration">
                <?php endif; ?>
                <div class="card-body text-center p-3 w-100 pt-0">
                    <div class="docs-info">
                        <h6 class="mb-0">Chez SALE</h6>
                        <p class="text-xs font-weight-bold mb-0">Votre satisfaction est notre priorité</p>
                    </div>
                </div>
            </div>
        </div>
    </aside>
    <!-- End Menu -->


    <main class="main-content position-relative border-radius-lg ">
        <!-- Navbar -->
        <nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-lg border-radius-xl position-sticky card left-auto top-2 z-index-sticky" id="navbarBlur" data-scroll="false">
            <div class="container-fluid py-1 px-3">
                <nav aria-label="breadcrumb">
                    <ol class="breadcrumb bg-transparent mb-0 pb-0 pt-1 px-0 me-sm-6 me-5">
                        <li class="breadcrumb-item text-sm"><a class="opacity-5" href="javascript:;">Pages</a></li>
                        <li class="breadcrumb-item text-sm active" aria-current="page">Commande</li>
                    </ol>
                    <h6 class="font-weight-bolder mb-0">Commande</h6>
                </nav>
                <div class="collapse navbar-collapse mt-sm-0 mt-2 me-md-0 me-sm-4" id="navbar">
                    <div class="ms-md-auto pe-md-3 d-flex align-items-center">
                        <!-- autre info? -->
                    </div>
                    <li class="navbar-nav  justify-content-end">

                        <?php if ($user) : ?>
                    <li class="nav-item d-flex align-items-center">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
                            <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3Zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6Z" />
                        </svg>
                        <span class="d-sm-inline d-none"><?= $user->lastname ?> <?= $user->firstname ?></span>
                    </li>
                    <li class="px-3 nav-item d-flex align-items-center">
                        <span class="badge bg-info text-dark"><?= $user->role ?></span>
                    </li>
                <?php endif; ?>

                <li class="nav-item d-flex align-items-center"></li>
                <div class="form-check form-switch">
                    <input class="form-check-input mt-1 ms-auto" type="checkbox" id="dark-version" onclick="darkMode(this)">
                </div>
                </li>
                <li class="nav-item dropdown px-3 d-flex align-items-center">
                    <a href="javascript:;" class="nav-link p-0" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="fa fa-bell cursor-pointer"></i>
                    </a>
                    <ul class="dropdown-menu  dropdown-menu-end  px-2 py-3 me-sm-n4" aria-labelledby="dropdownMenuButton">
                        <li class="mb-2">
                            <a class="dropdown-item border-radius-md" href="javascript:;">
                                <div class="d-flex py-1">
                                    <div class="my-auto">
                                        <img src="image/order.png" class="avatar avatar-sm  me-3 ">
                                    </div>
                                    <div class="d-flex flex-column justify-content-center">
                                        <h6 class="text-sm font-weight-normal mb-1">
                                            <span class="font-weight-bold">Commande 2</span> a été validé
                                        </h6>
                                        <p class="text-xs mb-0">
                                            <i class="fa fa-clock me-1"></i>
                                            il y a 13 minutes
                                        </p>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="mb-2">
                            <a class="dropdown-item border-radius-md" href="javascript:;">
                                <div class="d-flex py-1">
                                    <div class="my-auto">
                                        <img src="image/order.png" class="avatar avatar-sm  me-3 ">
                                    </div>
                                    <div class="d-flex flex-column justify-content-center">
                                        <h6 class="text-sm font-weight-normal mb-1">
                                            <span class="font-weight-bold">Commande 1</span> a été validé
                                        </h6>
                                        <p class="text-xs mb-0">
                                            <i class="fa fa-clock me-1"></i>
                                            il y a 2 jours
                                        </p>
                                    </div>
                                </div>
                            </a>
                        </li>
                    </ul>
                </li>
                </ul>
                </div>
            </div>
        </nav>
        <!-- End Navbar -->

        <div class="container-fluid py-4">

            <div class="row">
                <div class="col-md-7 mt-4 cardFlex">
                    <div class="card shadow-lg">


                        <div class="card-header pb-0 px-3 card_button">
                            <h6 class="mb-0">Vos commandes</h6>
                            <a href="<?= $router->generate('commande_create') ?>" class="card_button_right text-dark px-3 mb-0">
                            <i class="fas fa-plus-circle text-dark me-2" aria-hidden="true"></i> Nouvelle commande</a>
                        </div>
                        <div class="card-body pt-4 p-3 card-scroll">
                        <?php
                        $tab_type = ["ELECTRIQUE", "THERMIQUE", "CHIMIQUE", "MECANIQUE", "LUMINEUSE"];
                        $tab_status = ["ENVOYER", "EN COURS", "LIVRER"];
                        $tab_pays = ["FRANCE","ALLEMAGNE","ESPAGNE","ITALIE","PORTUGAL","ROUMANIE","GRECE","BELGIQUE","SUISSE","AUTRICHE","PAYS_BAS","DANEMARK","SUEDDE","NORVEGE","FINLANDE","POLOGNE","TCHECOSLOVAQUIE","HONGRIE","BULGARIE","REPUBLIQUE_TCHEQUE","SLOVAQUIE","ESTONIE","LETTONIE","LITUANIE","UKRAINE","MOLDAVIE","BOSNIE","CROATIE","MACEDOINE","SERBIE","SLOVENIE","ALBANIE","MONTENEGRO","MACEDOINE_DU_NORD","BULGARIE_DU_NORD","GRECE_DU_NORD","TURQUIE","ARMENIE","AZERBAIDJAN","GEORGIE","MAROC","TUNISIE","ALGERIE","LIBYE","MAURITANIE","SENEGAL","GAMBIE","GUINEE","GUINEE_EQUATORIALE","COTE_D_IVOIRE","GHANA","TOGO","BENIN","NIGER","NIGERIA","CHAD","CAMEROUN","CENTRAFRIQUE","CONGO","ANGOLA","GABON","REPUBLIQUE_DEMOGRAPHIQUE_DU_CONGO","REPUBLIQUE_CENTRAFRICAINE","SUDAN","ERYTHREE","ETIOPIE","SOMALIE","DJIBOUTI","KENYA","TANZANIE","OUGANDA","RWANDA","BURUNDI","ZAMBIE","ZIMBABWE","MOZAMBIQUE","CHINE","JAPON","COREE_DU_SUD","INDE","BANGLADESH","NEPAL","BIRMANIE","LAOS","VIETNAM","THAILANDE","MALAISIE","SINGAPOUR","PHILIPPINES","INDONESIE","AUSTRALIE","NOUVELLE_ZELANDE","CANADA","ETATS_UNIS","MEXIQUE","CUBA","DOMINICAINE","HAITI","GUATEMALA","SALVADOR","HONDURAS","NICARAGUA","COSTA_RICA","PANAMA"];
                        ?>

                            <form method="POST" action="">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label for="type" class="form-label">Type d'énergie</label>
                                            <select class="form-select" name="type" id="type">
                                                <?php foreach ($tab_type as $type) : ?>
                                                    <option value="<?= $type ?>"><?= $type ?></option>
                                                <?php endforeach; ?>
                                            </select>
                                        </div>
                                        <div class="mb-3">
                                                <label for="origine" class="form-label">Pays d'origne</label>
                                                <select class="form-select" name="type" id="type">
                                                <?php foreach ($tab_pays as $pays) : ?>
                                                    <option value="<?= $pays ?>"><?= $pays ?></option>
                                                <?php endforeach; ?>
                                            </select>
                                        </div>
                                        <div class="mb-3">
                                                <label for="quantite" class="form-label">Quantité souhaitée par MWh </label>
                                                <input type="number" class="form-control" id="quantite" name="quantite" min="1" max="9999" step="1"
                  onkeypress="return event.charCode >= 48 && event.charCode <= 57"
                  title="Numbers only">
                                        </div>
                                        <div class="mb-3">
                                                <label for="prix" class="form-label">Budget en Euro (€)</label>
                                                <input type="number" class="form-control" id="prix" name="prix" step=".01">
                                        </div>
                                        <input type="submit" class="btn btn-primary" value="Envoyer la commande">
                                    </div>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>