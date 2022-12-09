<div class="col-md-5 mt-4 cardFlex">
    <div class="card h-100 mb-4 shadow-lg">
        <div class="card-header pb-0 px-3">
            <div class="row">
                <div class="col-md-6">
                    <h6 class="mb-0">Marche de Gros :</h6>
                    <h8 class="mb-0">Liste d'énergie disponible</h8>
                </div>
            </div>
        </div>
        <div class="card-body pt-4 p-3 card-scroll" id="marche">
            <ul class="list-group">
                <?php
                $energies = file_get_contents('../../Serveurs/src/ClassServeurMarcheGros/energie.json');
                $mes_enrgies = json_decode($energies, true);
                if ($mes_enrgies != null) {
                    $taille = count($mes_enrgies);
                    foreach ($mes_enrgies as $key => $value) :

                ?>
                        <li class="list-group-item border-0 d-flex p-4 mb-2 bg-gray-100 border-radius-lg">
                            <div class="d-flex flex-column">
                                <span class="mb-2 text-xs">Energie n°<span class="text-dark ms-sm-2 font-weight-bold"><?= $taille-- ?></span></span>
                                <span class="mb-2 text-xs">Type d'énergie: <span class="text-dark font-weight-bold ms-sm-2"><?= $value['type'] ?></span></span>
                                <span class="mb-2 text-xs">Origine: <span class="text-dark font-weight-bold ms-sm-2"><?= $value['origine'] ?></span></span>
                                <span class="mb-2 text-xs">Quantité disponible: <span class="text-dark font-weight-bold ms-sm-2"><?= $value['quantite'] ?></span> kWh</span>
                                <span class="mb-2 text-xs">Prix: <span class="text-dark font-weight-bold ms-sm-2"><?= $value['prix'] ?></span> €/MWh</span> <!-- entre 30 et 300 €/MWh -->
                                <span class="mb-2 text-xs">Id Producteur: <span class="text-dark font-weight-bold ms-sm-2"><?= $value['idProprietaire'] ?></span></span>
                            </div>
                        </li>
                <?php endforeach;
                } else {
                    echo '<li class="list-group-item border-0 d-flex p-4 mb-2 bg-gray-100 border-radius-lg">
              <div class="d-flex flex-column">
                <h6 class="mb-3 text-sm">Le marché de gros est vide</h6>
                </div>
            </li>';
                } ?>
            </ul>
        </div>
    </div>
</div>
</div>
<footer class="footer pt-3  ">
    <div class="container-fluid">
        <div class="row align-items-center justify-content-lg-between">
            <div class="col-lg-6 mb-lg-0 mb-4">
                <div class="copyright text-center text-sm text-muted text-lg-start">
                    © <script>
                        document.write(new Date().getFullYear())
                    </script>,
                    made with <i class="fa fa-heart"></i> by
                    <a href="/#team" id="dark" class="font-weight-bold" target="_blank">HAYAT Rahim & MTARFI Souhail</a> - Energy Purchasing System
                </div>
            </div>
        </div>
    </div>
</footer>
</div>
</main>
<!--   Core JS Files   -->
<script src="JS/popper.min.js"></script>
<script src="JS/bootstrap.min.js"></script>
<script>
    var win = navigator.platform.indexOf('Win') > -1;
    if (win && document.querySelector('#sidenav-scrollbar')) {
        var options = {
            damping: '0.5'
        }
        Scrollbar.init(document.querySelector('#sidenav-scrollbar'), options);
    }
</script>
<!-- Control Center for Soft Dashboard: parallax effects, scripts for the example pages etc -->
<script src="JS/dashboard.min.js?v=2.0.4"></script>
</body>

</html>