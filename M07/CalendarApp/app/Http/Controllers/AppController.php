<?php

namespace App\Http\Controllers;

use PDOException;
use App\Models\Ajuda;
use App\Models\Users;
use App\Models\Calendar;
use App\Mail\VerifyMail;
use App\Models\Nacionalitat;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Mail;

class AppController extends Controller
{
    public function index()
    {
        $message = session('message');
        $type = session("tipus");
        if($message == null) {
            $message = "";
        }
        if($type == null) {
            $type == "";
        }
        $user = Users::where('email',session("email"))->get()->first();
        $calendariList = $user->CalendariPropietari()->get();
        $helperCalendarId = Ajuda::where('user', $user['id'])->get();
        $helperCalendarList = array();
        foreach($helperCalendarId as $item) {
            array_push($helperCalendarList, $item->CalendariAjudant()->first());
        }
        $userNameList = array();
       foreach($helperCalendarList as $item) {
            $u = $item->Users()->first();
            array_push($userNameList, $u->nom . " " . $u->cognoms);
        }
        return view("index", array('calendariPropis'=>$calendariList, 'calendariAjudant'=>$helperCalendarList, 'userNameList'=>$userNameList, "message"=>$message, "tipus"=>$type));
    }

    public function register()
    {
        $nacionalities = Nacionalitat::all();
        return view("registerForm", array("nacionalities" => $nacionalities, "email"=>"", 'token'=>""));
    }

    public function registerSave(Request $request)
    {
        $email = $request->email;
        $nom = $request->nom;
        $cognom1 = $request->cognom1;
        $cognom2 = $request->cognom2;
        $password = $request->pass;
        $dataNaix = $request->dataNaix;
        $telefon = $request->telefon;
        $genere = $request->genere;
        $nacionalitat = $request->nacionalitat;
        $users = null;
        try {
            if(strlen($request->token) == 0) {
                if (strlen($cognom2) == 0) {
                    $users = Users::create(array("email" => $email, "nom" => $nom, "cognoms" => $cognom1, "password" => md5($password), "data_naix" => $dataNaix, "telefon" => $telefon, "genere" => $genere, "nacionalitat" => $nacionalitat, "token"=>md5($email)));
                }
                else {
                    $users = Users::create(array("email"=>$email, "nom"=>$nom, "cognoms"=>$cognom1." ".$cognom2, "password"=>md5($password), "data_naix"=>$dataNaix, "telefon"=>$telefon,"genere"=>$genere, "nacionalitat"=>$nacionalitat, "token"=>md5($email)));
                }
            }
            else {
                $users = Users::where('email',$email)->get()->first();
                $users->nom = $nom;
                if (strlen($cognom2) == 0) {
                    $users->cognoms = $cognom1;
                }
                else {
                    $users->cognoms = $cognom1 . " ". $cognom2;
                }
                $users->password = md5($password);
                $users->data_naix = $dataNaix;
                $users->telefon = $telefon;
                $users->genere = $genere;
                $users->nacionalitat = $nacionalitat;
                $users->token = md5($email);
                $users->save();
            }

            $emailName = $nom." ".$cognom1;
            if(strlen($cognom2) > 0) {
                $emailName .= " " . $cognom2;
            }
            Mail::to($email)->send(new VerifyMail($emailName,md5($email)));
 
            if (Mail::failures()) {
                return response()->json([
                    'status' => 200,
                    'message' => json_encode($users),
                    'emailState' => 0
                ], 200);
            }
            return response()->json([
                'status' => 200,
                'message' => json_encode($users) ,
                'emailState'=>1
            ], 200);
        } catch (PDOException $ex) {
            if($ex->getCode() == 23000) {
                return response()->json([
                    'status' => 400,
                    'message' => "Error: Correu Electronic Introduit ja existeix a la base de dades"
                ],400);
            }
            else {
                return response()->json(
                    [
                        'status'=> 400,
                        'message'=> "Error: Ha ocurregut un error no controlat",
                        'Informacio Error'=>$ex->getMessage()
                    ],400);
            }

        }

    }

    public function registerCompletar($token) {
        $user = Users::where("token", $token)->where('validat', false)->get()->first();
        if($user == null) {
            return redirect("/login")->with("message", "Token invalid")->with("tipus", "danger");
        }
        else {
            try {
                $user->validat = true;
                $user->save();
                return redirect("/login")->with("message", "La validacio s'ha pogut completar ja pot iniciar sessio")->with("tipus", "success");
            }
            catch(PDOException $ex) {
                return redirect("/login")->with("message", "La validacio no s'ha pogut completar")->with("tipus", "danger");
            }
        }
    }
    
    public function registerAcceptarInvitacio($token) {
        $user = Users::where('token',$token)->where('validat',false)->get()->first();
        if($user == null) {
            return redirect("/login")->with("message", "Token Invalid")->with("tipus", "danger"); 
        }
        $nacionalities = Nacionalitat::all();
        return view("registerForm", array("nacionalities" => $nacionalities, "email"=>$user->email, "token"=>$token));
    }
}
