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
        return view('loginForm', array("message"=>$message));
    }

    public function check(Request $request) {
        $email = $request->input("email");
        $pass = $request->input('pass');
        $usuari = Users::where('email',$email)->where('password',md5($pass))->get()->first();
        if($usuari == null) {
            session()->flash("message","Credencials Introduides Invalides");
            return redirect("/login");
        }
        session()->flash("message","Login Correcte");
        session()->put("email",$email);
        return redirect('/index');
    }

    public function logout() {
        session()->forget("email");
        return redirect("/login");
    }
}
