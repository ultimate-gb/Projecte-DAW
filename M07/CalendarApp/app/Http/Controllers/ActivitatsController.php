<?php

namespace App\Http\Controllers;

use PDOException;
use App\Models\Users;
use App\Models\Calendar;
use App\Models\Activitat;
use App\Models\TipusActivitat;
use Illuminate\Http\Request;

class ActivitatsController extends Controller
{
    public function index($op,$id, $calendariId) {
        $message = session('message');
        $type = session("tipus");
        if($message == null) {
            $message = "";
        }
        if($type == null) {
            $type == "";
        }
        $activitat = (object) array(
            "nom" => "",
            "data_inici"=>"",
            "data_fi"=>"",
            "descripcio"=>"",
            "tipus"=>-1,

        );
        $email = session('email');
        $calendari = Calendar::find($calendariId);
        $usuari = Users::where("email", $email)->get()->first();
        $tipusActivitats = TipusActivitat::where('user', $calendari->Users()->get()->first()->id)->get();
        $opType = "";
        $dtInici = "";
        $dtFi = "";
        if($op == "add") {
            if($id != -1) {
                return redirect("/index");
            }
            $opType = "Afegir";
        }
        else if($op == "edit") {
            try {
                $activitat = Activitat::find($id);
                $dataInici = $activitat->data_inici;
                $pos = strpos($dataInici, " ");
                $dtInici = substr($dataInici, 0, $pos);
                $dtInici .= "T" . substr($dataInici, $pos+1);
                $dataFi = $activitat->data_fi;
                $pos = strpos($dataFi, " ");
                $dtFi = substr($dataFi, 0, $pos);
                $dtFi .= "T" . substr($dataFi, $pos+1);
            }
            catch (PDOException $ex) {
                return redirect("/index");
            }
            $opType = "Editar";
        }
        else if($op == "del") {
            $opType = "Esborrar";
        }
        return view("activitatManager", array('op'=>$op,'calendar'=>$calendariId, "opType"=>$opType, "user" =>$usuari->id, 'id'=>$id, "message"=>$message, "tipus"=>$type, "activitat"=>$activitat, "tipusActivitats"=>$tipusActivitats, "actDataInici"=>$dtInici, "actDataFi"=>$dtFi));
    }

}
