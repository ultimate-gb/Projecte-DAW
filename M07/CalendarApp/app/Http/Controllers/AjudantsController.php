<?php

namespace App\Http\Controllers;

use App\Models\Users;
use Illuminate\Http\Request;

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
        $usuaris = Users::all();
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

    public function afegir(Request $request) {

    }
}
