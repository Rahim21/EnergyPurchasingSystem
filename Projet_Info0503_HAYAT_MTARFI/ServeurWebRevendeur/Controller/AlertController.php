<?php

namespace App;

class AlertController
{
    public function __construct()
    {
        if (isset($_GET['success'])) {
            $this->success($_GET['success']);
        } elseif (isset($_GET['invalid'])) {
            $this->invalid($_GET['invalid']);
        }
    }

    public function success($type)
    {
        echo "<div class='alert alert-success' role='alert' style='width: 300px; position: absolute; top: 10px; right: 10px;'>";
        switch ($type) {
            case 'login':
                echo "Vous êtes connecté !";
                break;
            case 'send':
                echo "Votre commande a bien été enregistrée !";
                break;
            case 'certificat':
                echo "Le certificat de cette énergie est VALIDE, vérification effectué par l'AMI !";
                break;
            default:
                echo "Opération effectuée avec succès !";
                break;
        }
        echo "</div>";

        echo "<script>setTimeout(() => {document.querySelector('.alert').remove();}, 5000);</script>";
    }

    public function invalid($type)
    {
        echo "<div class='alert alert-danger' style='width: 300px; position: absolute; top: 10px; right: 10px; color: white;'>";
        switch ($type) {
            case 'alreadyexist':
                echo "Cette adresse mail est déjà utilisée !";
                break;
            case 'forbid':
                echo "L'accès à cette page est interdit.";
                break;
            case 'send':
                echo "Votre commande n'a pas pu être enregistrée !";
                break;
            case 'certificat':
                echo "Le certificat de cette énergie est INVALIDE, vérification effectué par l'AMI !";
                break;
            case 'certif-none':
                echo "Aucun certificat n'a été trouvé pour cette énergie !";
                break;
            default:
                echo "Opération echouée...";
                break;
        }
        echo "</div>";

        echo "<script>setTimeout(() => {document.querySelector('.alert').remove();}, 5000);</script>";
    }
}
