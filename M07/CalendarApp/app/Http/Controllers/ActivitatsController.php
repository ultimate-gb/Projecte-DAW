<?php

namespace App\Http\Controllers;

use DateTime;
use PDOException;
use App\Models\Users;
use App\Models\Calendar;
use App\Models\Activitat;
use App\Rules\ValidarTipus;
use App\Rules\ValidarDataFi;
use Illuminate\Http\Request;
use App\Models\TipusActivitat;
use App\Rules\ValidarDataInici;
use PDO;

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
                if($activitat == null) {
                    throw new PDOException("Activitat no trobada");
                }
                if($usuari->id != $calendari->user && $activitat->user != $usuari->id) {
                    return redirect("/calendar/see?id=".$calendari->id)->with("message","La activitat que has intentant accedir no és teva")->with("tipus","danger");
                }
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
                return redirect("/calendar/see?id=".$calendari->id)->with("message","La activitat no trobada")->with("tipus","danger");
            }
            $opType = "Editar";
        }
        else if($op == "del") {
            $opType = "Esborrar";
        }
        return view("activitatManager", array('op'=>$op,'calendar'=>$calendariId, "opType"=>$opType, "user" =>$usuari->id, 'id'=>$id, "message"=>$message, "tipus"=>$type, "activitat"=>$activitat, "tipusActivitats"=>$tipusActivitats, "actDataInici"=>$dtInici, "actDataFi"=>$dtFi));
    }

    public function add(Request $request) {
        $validatedData = $request->validate([
            'nom' => ['required', 'max:250'],
            'dataInici' => ['required', new ValidarDataInici],
            'dataFi' => [new ValidarDataFi(strtotime($request->dataInici))],
            'descripcio' => ['min:0', 'max:500'],
            'tipus'=> ['required', new ValidarTipus]
        ]);
        $pos = strpos($request->dataInici, "T");
        $dataInici = substr($request->dataInici, 0, $pos);
        $dataInici .= " " . substr($request->dataInici, $pos+1);
        $dataFi = null;
        if(strlen($request->dataFi) > 0) {
            $pos = strpos($request->dataFi, "T");
            $dataFi = substr($request->dataFi, 0, $pos);
            $dataFi .= " " . substr($request->dataFi, $pos+1); 
        }
        try {
            Activitat::create(array('nom'=>$request->nom, "data_inici"=>$dataInici, "data_fi"=>$dataFi, "calendari"=>$request->calendar, 'user'=>$request->user, "descripcio"=>$request->descripcio, "publicada"=>false, 'tipus'=>$request->tipus));
        } catch (PDOException $ex) {
            return redirect('/activitat/add/-1/'.$request->calendar)->with("message","No s'ha inserit la activitat")->with('type',"danger");
        }
        return redirect('/calendar/see?id='.$request->calendar)->with("message", "S'ha inserit correctament la activitat")->with("type", "success");
    }
    public function edit(Request $request) {
        $validatedData = $request->validate([
            'nom' => ['required', 'max:250'],
            'dataInici' => ['required'],
            'dataFi' => [new ValidarDataFi(strtotime($request->dataInici))],
            'descripcio' => ['min:0', 'max:500'],
            'tipus'=> ['required', new ValidarTipus]
        ]);
        $pos = strpos($request->dataInici, "T");
        $dataInici = substr($request->dataInici, 0, $pos);
        $dataInici .= " " . substr($request->dataInici, $pos+1);
        $dataFi = null;
        if(strlen($request->dataFi) > 0) {
            $pos = strpos($request->dataFi, "T");
            $dataFi = substr($request->dataFi, 0, $pos);
            $dataFi .= " " . substr($request->dataFi, $pos+1); 
        }
        try {
            $activitat = Activitat::find($request->id);
            $activitat->nom = $request->nom;
            $activitat->data_inici = $dataInici;
            $activitat->data_fi = $dataFi;
            $activitat->descripcio = $request->descripcio;
            $activitat->tipus = $request->tipus;
            $activitat->save();
            return redirect('/calendar/see?id='.$request->calendar)->with("message", "S'ha actualitzat correctament la activitat ".$activitat->nom)->with("type", "success");
        } catch (PDOException $ex) {
            return redirect('/activitat/edit/'. $request->id .'/'.$request->calendar)->with("message","No s'ha actualitzat la activitat")->with('type',"danger");
        }
    }
    public function destroy(Request $request) {
        $id = $request->id;
        try {
            $activitat = Activitat::find($id);
            Activitat::destroy($id);
            return redirect('/calendar/see?id='.$request->calendar)->with("message", "Eliminada la activitat amb nom " . $activitat->nom)->with("tipus", "success");
        } catch (PDOException $ex) {
            return redirect("/index")->with("message", "Ha ocurregut un error no controlat i no s'ha pogut eliminar la activitat")->with("tipus", "danger");
        }
    }
}
