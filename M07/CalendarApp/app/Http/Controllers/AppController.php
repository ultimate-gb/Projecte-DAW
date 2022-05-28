<?php

namespace App\Http\Controllers;

use PDOException;
use App\Models\Users;
use App\Mail\VerifyMail;
use App\Models\Nacionalitat;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Mail;

class AppController extends Controller
{
    public function index()
    {
        echo "Hola Mon";
    }

    public function register()
    {
        $nacionalities = Nacionalitat::all();
        return view("registerForm", array("nacionalities" => $nacionalities));
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
            if (strlen($cognom2) == 0) {
                $users = Users::create(array("email" => $email, "nom" => $nom, "cognoms" => $cognom1, "password" => md5($password), "data_naix" => $dataNaix, "telefon" => $telefon, "genere" => $genere, "nacionalitat" => $nacionalitat, "token"=>md5($email)));
            }
            else {
                $users = Users::create(array("email"=>$email, "nom"=>$nom, "cognoms"=>$cognom1." ".$cognom2, "password"=>md5($password), "data_naix"=>$dataNaix, "telefon"=>$telefon,"genere"=>$genere, "nacionalitat"=>$nacionalitat, "token"=>md5($email)));
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
}
