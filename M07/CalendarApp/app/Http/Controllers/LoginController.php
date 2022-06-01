<?php

namespace App\Http\Controllers;

use App\Models\Users;
use Illuminate\Http\Request;

class LoginController extends Controller
{
    public function login() {
        $message = session('message');
        if($message == null) {
            $message = "";
        }
        $type = session("tipus");
        if($type == null) {
            $type == "";
        }
        return view('loginForm', array("message"=>$message, "tipus"=>$type));
    }

    public function check(Request $request) {
        $email = $request->input("email");
        $pass = $request->input('pass');
        $usuari = Users::where('email',$email)->where('password',md5($pass))->get()->first();
        if($usuari == null) {
            session()->flash("message","Credencials Introduides Invalides");
            session()->flash("tipus","danger");
            return redirect("/login");
        }
        if($usuari->bloquejat) {
            session()->flash("message","Usuari bloquejat");
            session()->flash("tipus","danger");
            return redirect("/login");
        }
        session()->flash("message","Login Correcte");
        session()->flash("tipus","success");
        session()->put("email",$email);
        return redirect('/index');
    }

    public function logout() {
        session()->forget("email");
        session()->flash("message","Logout efectuat correctament");
        session()->flash("tipus","success");
        return redirect("/login");
    }
}
