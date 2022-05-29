<?php

namespace App\Http\Controllers;

use PDOException;
use App\Models\Calendar;
use App\Models\Users;
use Illuminate\Http\Request;

class CalendarController extends Controller
{
    public function index($op, $id) {
        $message = session('message');
        $type = session("tipus");
        if($message == null) {
            $message = "";
        }
        if($type == null) {
            $type == "";
        }
        $email = session('email');
        $user = Users::where('email',$email)->get()->first();
        $calendar = (object) array(
            'id'=>"",
            'nom'=>"",
            'data_creacio'=>""
        );
        $opType = "";
        if($op == "add") {
            if($id != -1) {
                return redirect("/index");
            }
            $opType = "Afegir";
        }
        else if($op == "edit") {
            try {
                $calendar = Calendar::where('id', $id)->get()->first();
            }
            catch (PDOException $ex) {
                return redirect("/index");
            }
            $opType = "Editar";
        }
        else if($op == "del") {
            $opType = "Esborrar";
        }
        return view("calendarManager", array('op'=>$op,'calendar'=>$calendar, "opType"=>$opType, 'user'=>$user->id, 'id'=>$id, "message"=>$message, "tipus"=>$type));
    }

    public function add(Request $request) {
        $nom = $request->nom;
        $user = $request->user;
        if(strlen($nom) > 250) {
            return redirect("/calendar/create/add/-1")->with("message", "Longitud el nom no pot superar els 250 caracters.")->with("tipus", "danger");
        }
        try {
            Calendar::create(array('nom'=>$nom,'data_creacio'=>date('Y-m-d h:i:s', time()), 'user'=>$user));
            return redirect("/index")->with("message", "Calendari Nou Inserit")->with("tipus", "success");
        } catch (PDOException $ex) {
            return redirect("/calendar/add/-1")->with("message", "Ha ocurregut un error no controlat")->with("tipus", "danger");
        }
    }

    public function edit(Request $request) {
        $id = $request->id;
        $nom = $request->nom;
        if(strlen($nom) > 250) {
            return redirect("/calendar/create/edit/".$id)->with("message", "Longitud el nom no pot superar els 250 caracters.")->with("tipus", "danger");
        }
        try {
            $calendar = Calendar::find($id);
            $calendar->nom = $nom;
            $calendar->save();
            return redirect("/index")->with("message", "Actualitzat el Calendari amb nom " . $nom)->with("tipus", "success");
        } catch (PDOException $ex) {
            return redirect("/calendar/edit/".$id)->with("message", "Ha ocurregut un error no controlat")->with("tipus", "danger");
        }
    }

    public function destroy(Request $request) {
        $id = $request->id;
        try {
            $calendar = Calendar::find($id);
            Calendar::destroy($id);
            return redirect("/index")->with("message", "Eliminat el Calendari amb nom " . $calendar->nom)->with("tipus", "success");
        } catch (PDOException $ex) {
            return redirect("/index")->with("message", "Ha ocurregut un error no controlat")->with("tipus", "danger");
        }
    }

    public function see(Request $request) {
        $id = $request->id;
        if($id == null) {
            return redirect("/index")->with("message", "Falta el id")->with("tipus", "danger");
        }
    }
}
