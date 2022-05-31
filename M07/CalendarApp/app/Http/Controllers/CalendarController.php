<?php

namespace App\Http\Controllers;

use PDOException;
use App\Models\Ajuda;
use App\Models\Users;
use App\Models\Calendar;
use App\Models\Activitat;
use Illuminate\Http\Request;
use App\Models\CalendariTarget;
use Illuminate\Pagination\Paginator;
use Illuminate\Support\Collection;
use Illuminate\Pagination\LengthAwarePaginator;

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
        $calendari = Calendar::find($id);
        $activitats = Activitat::where('calendari', $id)->paginate(10)->withQueryString();
        $activitats->append(['id'=>$id]);
        $email = session("email");
        $user = Users::where('email',$email)->get()->first();
        $esPropietari = false;
        if($user->id == $calendari->user) {
            $esPropietari =true;
        }
        $ajudants = array();
        $calendariTarget = array();
        $data = array();
        if($esPropietari) {
            foreach($calendari->Ajuda()->get() as $ajuda) {
                array_push($ajudants, $ajuda->Users()->first());
            }
            $myCollectionObj = collect($ajudants);
            $data = $this->paginate($myCollectionObj);
            $calendarisTarget = CalendariTarget::where('calendar', $id)->get();
            foreach($calendarisTarget as $target) {
                array_push($calendariTarget, $target);
            }
        }
        return view("calendarView", array(
            'calendari'=>$calendari, 
            "activitats"=>$activitats, 
            "esPropietari"=>$esPropietari, 
            "ajudants"=> $data,
            "targets"=>$calendariTarget, 
            "message"=>"",
            "tipus"=>array()
        ));
    }

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    public function paginate($items, $perPage = 5, $page = null, $options = [])
    {
        $page = $page ?: (Paginator::resolveCurrentPage() ?: 1);
        $items = $items instanceof Collection ? $items : Collection::make($items);
        return new LengthAwarePaginator($items->forPage($page, $perPage), $items->count(), $perPage, $page, $options);
    }
}
