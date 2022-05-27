<?php

namespace App\Http\Controllers;

use App\Models\Users;
use App\Models\Nacionalitat;
use Illuminate\Http\Request;

class AppController extends Controller
{
    public function index() {
        echo "Hola Mon";
    }

    public function register() {
        $nacionalities = Nacionalitat::all();
        return view("registerForm", array("nacionalities" => $nacionalities, "message"=>""));
    }

    public function registerSave(Request $request) {
        $email = $request->email;
        $nom = $request->nom;
        $cognom1 = $request->cognom1;
        $cognom2 = $request->cognom2;
        $password = $request->pass;
        $dataNaix = $request->dataNaix;
        $telefon = $request->telefon;
        $genere = $request->genere;
        $nacionalitat = $request->nacionalitat;
        $users = Users::create(array("email"=>$email, "nom"=>$nom, "cognoms"=>$cognom1+" "+$cognom2, "password"=>md5($password), "dataNaix"=>$dataNaix, "telefon"=>$telefon,"genere"=>$genere, "nacionalitat"=>$nacionalitat));
        return response()->json([
            'status'=> 200,
            'message'=>json_encode($users)
        ]);
    }
}
