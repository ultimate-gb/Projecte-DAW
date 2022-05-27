<?php

namespace App\Http\Controllers;

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
}
