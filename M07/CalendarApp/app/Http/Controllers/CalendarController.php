<?php

namespace App\Http\Controllers;

use PDOException;
use App\Models\Ajuda;
use App\Models\Users;
use App\Models\Calendar;
use App\Models\Activitat;
use Illuminate\Http\Request;
use App\Models\CalendariTarget;
use Illuminate\Support\Collection;
use Illuminate\Support\Facades\DB;
use App\Rules\CsvExtensionValidator;
use Google\Service\Calendar as GoogleCalendar;
use Google\Service\Calendar\AclRule;
use Google\Service\Calendar\AclRuleScope;
use Illuminate\Pagination\Paginator;
use Illuminate\Support\Facades\Storage;
use Illuminate\Pagination\LengthAwarePaginator;
use Illuminate\Routing\UrlGenerator;

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
                $calendar = Calendar::find($id);
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
        $message = session('message');
        $type = session("tipus");
        if($message == null) {
            $message = "";
        }
        if($type == null) {
            $type == "";
        }
        $calendari = Calendar::find($id);
        $email = session("email");
        $user = Users::where('email',$email)->get()->first();
        $activitats = null;;
        $esPropietari = false;
        if($user->id == $calendari->user) {
            $esPropietari =true;
            $activitats = Activitat::where('calendari', $id)->paginate(10, ['*'], "activitats")->withQueryString();
        }
        else {
            $activitats = Activitat::where('calendari', $id)->where('user',$user->id)->paginate(10,['*'], "activitats")->withQueryString();
        }
        $ajudants = array();
        $calendariTarget = array();
        $data = array();
        if($esPropietari) {
            foreach($calendari->Ajuda()->get() as $ajuda) {
                if($ajuda->Users()->first()->validat == 1) {
                    array_push($ajudants, $ajuda->Users()->first());
                }
            }
            $myCollectionObj = collect($ajudants);
            $data = $this->paginate($myCollectionObj)->withPath(url()->current())->withQueryString();
            $calendariTarget = CalendariTarget::where('calendar', $id)->paginate(5,['*'], "targets")->withQueryString();
        }
        return view("calendarView", array(
            'calendari'=>$calendari, 
            "activitats"=>$activitats, 
            "esPropietari"=>$esPropietari, 
            "ajudants"=> $data,
            "targets"=>$calendariTarget, 
            "message"=>$message,
            "tipus"=>$type,
            "user"=>$user,
            'isMobile'=>MyUtilities::isMobile()
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

    public function vistaAfegirDestinataris(Request $request) {
        $message = session('message');
        $type = session("tipus");
        if($message == null) {
            $message = "";
        }
        if($type == null) {
            $type == "";
        }
        if($request->id == null) {
            return redirect("/index")->with("message", "Falta el id o el id es incorrecte")->with("tipus", "danger");
        }
        $calendari = Calendar::find($request->id);
        if($calendari == null) {
            return redirect("/calendar/see?id=".$request->id)->with("message", "Falta el id o el id es incorrecte")->with("tipus", "danger");
        }
        return view("calendarTargetView", array('calendar'=>$request->id, "message"=>$message, "tipus"=>$type));
    }

    public function afegirDestinataris(Request $request) {
        $validatedData = $request->validate([
            'csvFile' => ['required', new CsvExtensionValidator],
        ]);
        $fileStatus = "";
        if ($request->hasFile('csvFile')) {
            $name = $request->file('csvFile')->getClientOriginalName();
            $fileName = MyUtilities::uniqueFileName($name);
            $path = $request->file('csvFile')->storeAs('public/files', $fileName); // Puja un fitxer
            if($request->file('csvFile')->isValid()) {
                $fileStatus = "Fitxer pujat correctament";
                $filePath = Storage::path("public/files/" . $fileName);
                $longitudDeLinea = 1000;
                $delimitador = ","; # Separador de columnes
                $caracterCircundant = '"'; # A vegades els valores estab tancats entre cometes
                # Orbir el archivo
                $gestor = fopen($filePath, "r");
                if (!$gestor) {
                    return redirect("/calendar/see?id=".$request->id)->with("message", "No s'ha pogut obrir el fitxer")->with("tipus", "danger");
                }

                #  Comença a llegir, $numeroDeFila es per portar un index
                $numeroDeFila = 1;
                $fila = fgetcsv($gestor, $longitudDeLinea, $delimitador, $caracterCircundant);
                DB::beginTransaction();
                while ($fila !== false && count($fila)==1) {
                    try {
                        $target = CalendariTarget::where("email", $fila[0])->where('calendar', $request->calendar)->get()->first();
                        if($target == null) {
                            if(preg_match("/^[\w\-\.]+@([\w\-]+\.)+[\w-]{2,4}$/", $fila[0],$result,PREG_OFFSET_CAPTURE)) {
                                CalendariTarget::create(array('calendar'=>$request->calendar, "email"=>$fila[0]));
                            }
                            else {
                                DB::rollback();
                                return redirect('/calendar/destinataris?id='.$request->calendar)->with('message', "Un dels correus del fitxer es invalid")->with("tipus","danger");
                            }
                        }
                    }
                    catch (PDOException $ex) {
                        DB::rollback();
                        return redirect('/calendar/destinataris?id='.$request->calendar)->with('message', "Ha ocurregut un error no controlat")->with("tipus","danger");
                    }
                    $fila = fgetcsv($gestor, $longitudDeLinea, $delimitador, $caracterCircundant);
                }
                DB::commit();
                // Al final tancar el gestor
                fclose($gestor);
                $fileStatus = "Fitxer pujat correctament i fetes totes les insercions";
            }
            else {
                $fileStatus = "Error en pujar fitxer";
            }
            echo $fileStatus;
        }
        return redirect("/calendar/see?id=".$request->calendar)->with("message", $fileStatus)->with("tipus", "success");
    }

    public function publicar(Request $request) {
        if($request->id == null) {
            return redirect("/index")->with("message", "Falta el id o el id es incorrecte")->with("tipus", "danger");
        }
        $calendari = Calendar::find($request->id);
        $activitats = Activitat::where('calendari', $request->id)->where('publicada', false)->paginate(10)->withQueryString();
        if($calendari == null) {
            return redirect("/index")->with("message", "Falta el id o el id es incorrecte")->with("tipus", "danger");
        }
        return view("publicarView", array('calendar'=>$request->id, "message"=>"", "tipus"=>"", "activitats"=>$activitats));
    }

    public function publicarActivitats(Request $request) {
        $calendari = Calendar::find($request->calendar);
        if($calendari == null) {
            $id = session("calendarId");
            if($id == null) {
                return redirect("/calendar/publicar")->with("message", "Falta el id o el id es incorrecte")->with("tipus", "danger");
            }
            else {
                $calendari = Calendar::find($id);
                session()->forget("calendarId");
            }
            
        }
        else {
            session()->put("calendarId", $request->calendar);
            session()->put("publicar", json_encode($request->publicar));
        }
        $client = MyUtilities::getGoogleClient($request);
        $idGoogleCalendar = null;
        $service = new GoogleCalendar($client);
        if($calendari->googleCalId == null || strlen($calendari->googleCalId) == 0) {
            $idGoogleCalendar = MyUtilities::createGoogleCalendar($client, $calendari->nom, "Europe/Madrid", $service);
            try {
                $calendari->googleCalId = $idGoogleCalendar;
                $calendari->save();
            } catch(PDOException $ex) {
                return redirect("/calendar/publicar")->with("message", "No s'ha pogut inserir guardar el id del calendari creat.")->with("tipus", "danger");
                $service->calendars->delete($idGoogleCalendar);
            }
        }
        else {
            $idGoogleCalendar = $calendari->googleCalId;
        }
        $activitats = Activitat::findMany(json_decode(session("publicar"),true));
        session()->forget("publicar");
        if($activitats == null) {
            return redirect("/calendar/publicar")->with("message", "Ha ocurregut un error")->with("tipus", "danger");
        }
        foreach($activitats as $act) {
            $descripcio = "";
            if($act->descripcio != null) {
                $descripcio = $act->descripcio;
            }
            $event = MyUtilities::createGoogleCalendarEvent($service, $idGoogleCalendar, MyUtilities::convertDateTimeSqlToDateTimeCalendar($act->data_inici), MyUtilities::convertDateTimeSqlToDateTimeCalendar($act->data_fi),$act->nom,$descripcio, "Europe/Madrid" );
            if(isset($event->error)) {
                continue;
            }
            else {
                try {
                    $activitat = Activitat::find($act->id);
                    $activitat->publicada = 1;
                    $activitat->save();
                } catch(PDOException $ex) {
                    $service->events->delete($idGoogleCalendar, $event->id);
                    return redirect("/calendar/publicar")->with("message", "Ha ocurregut un error al passar a actualitzar")->with("tipus", "danger");
                }
            }
        }
        $targets = CalendariTarget::where('calendar',$calendari->id)->get();
        $acl = $service->acl->listAcl('primary');
        foreach($targets as $targ) {
            $i = 0;
            foreach ($acl->getItems() as $rule) {
                $email = $rule->getScope()->getValue();
                if($email != $targ->email) {
                    $i++;
                }   
            }
            if($i == count($acl->getItems())) {
                $rule = new AclRule();
                $scope = new AclRuleScope();

                $scope->setType("user");
                $scope->setValue("$targ->email");
                $rule->setScope($scope);
                $rule->setRole("reader");

                $createdRule = $service->acl->insert('primary', $rule);
                if(isset($createdRule->error)) {
                    continue;
                }
            }
        }
        return redirect("/calendar/see?id=". $calendari->id)->with("message", "Tot publicat correctament")->with("tipus", "success");

    }

    public function export(Request $request) {
        if($request->id == null) {
            return redirect("/index")->with("message", "Falta el id o el id es incorrecte")->with("tipus", "danger");
        }
        $calendari = Calendar::find($request->id);
        if($calendari == null) {
            return redirect("/index")->with("message", "Falta el id o el id es incorrecte")->with("tipus", "danger");
        }
        return view("exportarView", array('calendar'=>$request->id, "message"=>"", "tipus"=>"", "calendari"=>$calendari));
    }

    public function exportarActivitats(Request $request) {
        $user = Users::where("email", session('email'))->get()->first();
        $url = "http://localhost/projecte/CalendarApp/public/api/getToken";
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, array('email'=>$user->email,"password"=>$user->password));
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        $result = curl_exec($ch);
        $output = json_decode($result);
        if($output->status == 200) {
            $token = $output->data;
            $url2 = "http://localhost/projecte/CalendarApp/public/api/exportarActivitats";
            $ch2 = curl_init();
            curl_setopt($ch2, CURLOPT_URL, $url2);
            curl_setopt($ch2, CURLOPT_POST, true);
            curl_setopt($ch2, CURLOPT_HTTPHEADER, ["Authorization: ".$token]);
            curl_setopt($ch2, CURLOPT_POSTFIELDS, array('calendari'=>$request->calendar, "dataInici"=>$request->dataInici,"dataFi"=>$request->dataFi));
            curl_setopt($ch2, CURLOPT_RETURNTRANSFER, true);
            $result2 = curl_exec($ch2);
            $output2 = json_decode($result2);
            Storage::put("activitats.csv", $output2->data);
            return Storage::download("activitats.csv");
        }
        else {
            return redirect("calendar/export?id=".$request->calendar)->with("message", "No s'ha pogut descarregar")->with("tipus","danger");
        }
    }

    public function inform(Request $request) {
        $calendaris = Calendar::all();
        return view("informView", array("message"=>"", "tipus"=>"", "calendaris"=>$calendaris));
    }

    public function generarInforme(Request $request) {
        $url = "http://51.68.224.27:8080/jasperserver/rest_v2/reports/daw2-gbalsells/InformeProjecte_02_06_2022.pdf?";
        $i = 0;
        if(strlen($request->informe) > 0) {
            foreach($request->informe as $id) {
                $url .= "cal=".$id;
                $i++;
                if($i < count($request->informe)) {
                    $url .= "&";
                }
            }
        }

        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_POST, false);
        curl_setopt($ch, CURLOPT_HTTPHEADER, ["Authorization:Basic ".base64_encode("daw2-gbalsells:4192G")]);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        $result = curl_exec($ch);
        Storage::put("informe.pdf", $result);
        return Storage::download("informe.pdf");
    }

}
