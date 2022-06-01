<?php

namespace App\Http\Controllers;

use App\Models\Users;
use Firebase\JWT\JWT;
use Firebase\JWT\Key;
use App\Models\Activitat;
use Illuminate\Http\Request;
use UnexpectedValueException;

class ApiController extends Controller
{
    private String $key = "gerard";
    
    public function get_token(Request $request) {
        $user = Users::where('email',$request->email)->where('password', $request->password)->where('validat',true)->get()->first();
        if($user != null) {
            $payload = array(
                'user_id' => $user->id,
                'email' => $user->email,
                'time' => time()*150
            );
            $jwt = JWT::encode($payload, $this->key, 'HS256');
            return response()->json(
                [
                    'status'=>200,
                    'message'=>"Login Valid",
                    'data'=>$jwt
                ]
            );
        }
        else {
            return response()->json(
                [
                    'status'=>400,
                    'message'=>"Login Invalid",
                    'data'=>null
                ]
            );
        }
    }

    public function exportarActivitats(Request $request) {
        if (strlen($request->header('Authorization')) > 0) {
            $token = $request->header('Authorization');
            $payLoad = $this->obteinToken($token);
            if($payLoad != null && $this->checkToken($payLoad)) {
                $dataInici = $request->dataInici;
                $dtInici = "";
                if(strlen($dataInici) > 0) {
                    $pos = strpos($dataInici, "T");
                    $dtInici = substr($dataInici, 0, $pos);
                    $dtInici .= " " . substr($dataInici, $pos+1);
                }
                $dataFi = $request->dataFi;
                $dtFi = "";
                if(strlen($dataFi) > 0) {
                    $pos = strpos($dataFi, "T");
                    $dtFi = substr($dataFi, 0, $pos);
                    $dtFi .= " " . substr($dataFi, $pos+1);
                }
                $activitats = null;
                if(strlen($dtInici) > 0  && strlen($dtFi) > 0) {
                    $activitats = Activitat::where('calendari',$request->calendari)->where('data_inici',">=",$dtInici)->where('data_fi',"<=",$dtFi)->get();
                }
                else if (strlen($dtInici) > 0) {
                    $activitats = Activitat::where('calendari',$request->calendari)->where('data_inici',">=",$dtInici)->get();
                }
                else if (strlen($dtFi) > 0) {
                    $activitats = Activitat::where('calendari',$request->calendari)->where('data_fi',"<=",$dtFi)->get();
                }
                else {
                    $activitats = Activitat::where('calendari',$request->calendari)->get();
                }
                
                $txt = "";
                foreach($activitats as $act) {
                    $txt .= $act->id . ",";
                    $txt .=  $act->nom . ",";
                    $txt .= $act->data_inici . ",";
                    $txt .= $act->data_fi . ",";
                    $txt .=  $act->descripcio . ",";
                    $txt .= $act->Tipus()->get()->first()->nom . ",";
                    $txt .= $act->Calendari()->get()->first()->nom . ",";
                    $txt .= $act->Propietari()->get()->first()->email . ",";
                    $txt .= $act->publicada . "\n";
                }
                return response()->json(
     [
                        'status'=>200,
                        'message'=>"Exportacio Feta",
                        'data'=>$txt
                    ]
                );

            }
            else {
                return response()->json(
                    [
                        'status'=>400,
                        'message'=>"Token Invalid",
                        'data'=>null
                    ]
                );
            }
        }
        else {
            return response()->json(
                [
                    'status'=>400,
                    'message'=>"Token Invalid",
                    'data'=>null
                ]
            );
        }
    }

    private function checkToken($payLoad)
    {
        $actualTime =  time();
        $duration = $payLoad['time'];
        if($duration > $actualTime) {
            return true;
        }
        else {
            $token = JWT::encode($payLoad, $this->key, 'HS256');
            return false;
        }
    }
    private function obteinToken($token)
    {
        try {
            $decoded = JWT::decode($token, new Key($this->key, 'HS256'));
            $decoded_array = (array) $decoded;
            return $decoded_array;
        } catch(UnexpectedValueException $ex) {
            return null;
        }
    }
}
