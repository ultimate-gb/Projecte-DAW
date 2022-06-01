<?php

namespace App\Http\Controllers;

use PDOException;
use App\Models\Ajuda;
use App\Models\Users;
use App\Models\Calendar;
use App\Mail\InvitationMail;
use Illuminate\Http\Request;
use App\Rules\ValidarUsuariSel;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Mail;

class AjudantsController extends Controller
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
        $email = session('email');
        $calendar = Calendar::find($calendariId);
        $usuaris = Users::where('id', '!=',$calendar->Users()->get()->first()->id)->get();
        $opType = "";
        if($op == "add") {
            if($id != -1) {
                return redirect("/index");
            }
            $opType = "Afegir";
        }
        else if($op == "del") {
            $opType = "Esborrar";
        }
        return view("helperManager", array('op'=>$op,'calendar'=>$calendariId, "opType"=>$opType, "usuaris" =>$usuaris, 'id'=>$id, "message"=>$message, "tipus"=>$type));
    }

    public function add(Request $request) {
        $validatedData = $request->validate([
            'user' => ['required', new ValidarUsuariSel],
            'email' => ['min:0', 'max:500']
        ]);
        if($request->user > 0) {
            try {
                $user = Users::find($request->user);
                $calendari = Calendar::find($request->calendari);
                Ajuda::create(array('user'=>$request->user, 'calendari'=>$request->calendari));
                return redirect('/calendar/see?id='.$request->calendari)->with("message", "S'ha afegit correctament el ajudant amb email ".$user->email)->with("tipus", "success");
            } catch (PDOException $ex) {
                return redirect('/ajudants/add/-1/'. $request->calendari)->with("message","Aquest Usuari ja es ajudant")->with('tipus',"danger");
            }
        }
        else {
            DB::beginTransaction();
            $users = null;
            try {
                $users = Users::where('email', $request->email)->get()->first();
                if($users == null) {
                    $users = Users::create(array("email"=>$request->email, "nom"=>"Usuari", "cognoms"=>"Temporal", "password"=>md5("prova"), "data_naix"=>"2000-01-01", "telefon"=>"","genere"=>"I", "nacionalitat"=>"ESP", "token"=>md5($request->email)));
                }
            } catch(PDOException $ex) {
                DB::rollBack();
                return redirect('/ajudants/add/-1/'. $request->calendari)->with("message","Ha ocurregut un error no controlat")->with('tipus',"danger");
            }
            try {
                Ajuda::create(array("user"=>$users->id, "calendari"=>$request->calendari));
            } catch(PDOException $ex) {
                DB::rollBack();
                return redirect('/ajudants/add/-1/'. $request->calendari)->with("message","No s'ha pogut inserir el usuari")->with('tipus',"danger");
            }
            DB::commit();
            Mail::to($request->email)->send(new InvitationMail(md5($request->email)));
 
            if (Mail::failures()) {
                return redirect('/ajudants/add/-1/'. $request->calendari)->with("message","El usuari ja ha sigut registrar a la base de dades de forma temporal pero no se li ha pogut enviar el correu")->with('tipus',"danger");
            }
            return redirect('/calendar/see?id='.$request->calendari)->with("message", "S'ha completat correctament el registre temporal del ajudant. A més se l'hi ha enviat un correu de invitacio a la aplicacio en el email ".$users->email)->with("tipus", "success");
        }
    }
    public function destroy(Request $request) {
    }
}
