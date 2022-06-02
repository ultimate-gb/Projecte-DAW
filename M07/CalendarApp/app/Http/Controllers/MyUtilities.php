<?php

namespace App\Http\Controllers;

use Exception;
use Google\Client;
use Google\Service\Calendar as GoogleCalendar;
use Google\Service\Calendar\Event as GoogleCalendarEvent;
use Google\Service\Calendar\Calendar as GoogleCalendarCalendar;
use Illuminate\Http\Request;

class MyUtilities extends Controller
{
    public static function uniqueFileName(string $fileName): string
    {
        $today = getdate(); // Obte data actual
        $ext = MyUtilities::extensionDicover($fileName); // Obte extensio fitxer
        $newFileName = str_replace($ext, "-" . $today['mday'] . $today['mon'] . $today['year']  . $today['hours'] . $today['minutes'] . $today['seconds'] . $ext, $fileName, $fileName); // Afegiex al nom del fitxer una data per fer-lo unic

        return $newFileName; // Retorna el nom unic
    }

    public static function extensionDicover(string $fileName = null): string
    {
        $dotPos = strrpos($fileName, "."); //  Busca el punt en el nom del fitxer
        $ext = substr($fileName, $dotPos); // Obte l'extensio

        return $ext; // Retorna la extensio
    }

    public static function convertDateTimeSqlToDateTimeCalendar(String $data) {
        $pos = strpos($data, " ");
        $resultat = substr($data, 0, $pos);
        $resultat .= "T" . substr($data, $pos+1);
        return $resultat;
    }

    public static function getGoogleClient(Request $request)
    {
        $client = new Client();
        $client->setApplicationName('Google Calendar API PHP Quickstart');
        $client->setScopes(GoogleCalendar::CALENDAR);
        $client->setAuthConfig('credentials.json');
        $client->setAccessType('offline');
        $client->setPrompt('select_account consent');
    
        // Load previously authorized token from a file, if it exists.
        // The file token.json stores the user's access and refresh tokens, and is
        // created automatically when the authorization flow completes for the first
        // time.
        $tokenPath = 'token.json';
        if (file_exists($tokenPath)) {
            $accessToken = json_decode(file_get_contents($tokenPath), true);
            $client->setAccessToken($accessToken);
        }
        $code = "";
        // If there is no previous token or it's expired.
        if ($client->isAccessTokenExpired()) {
            // Refresh the token if possible, else fetch a new one.
            if ($client->getRefreshToken()) {
                $client->fetchAccessTokenWithRefreshToken($client->getRefreshToken());
            } else {
                // Request authorization from the user.
                $authUrl = $client->createAuthUrl();
                if(isset($request->code)) {
                    $code = $request->code;
                    $authCode = trim($code);
                } 
                else {
                    header('location: '.$authUrl);
                    exit;
                }
    
                //printf("Open the following link in your browser:\n%s\n", $authUrl);
                //print 'Enter verification code: ';
                // Exchange authorization code for an access token.
                $accessToken = $client->fetchAccessTokenWithAuthCode($authCode);
                $client->setAccessToken($accessToken);
    
                // Check to see if there was an error.
                if (array_key_exists('error', $accessToken)) {
                    throw new Exception(join(', ', $accessToken));
                }
            }
            // Save the token to a file.
            if (!file_exists(dirname($tokenPath))) {
                mkdir(dirname($tokenPath), 0700, true);
            }
            file_put_contents($tokenPath, json_encode($client->getAccessToken()));
        }
        return $client;
    }

    public static function createGoogleCalendar(Client $client, String $nomCalendari, String $timeZone, GoogleCalendar $service) {
        $calendar = new GoogleCalendarCalendar();
        $calendar->setSummary($nomCalendari);
        $calendar->setTimeZone($timeZone);

        $createdCalendar = $service->calendars->insert($calendar);

        return $createdCalendar->getId();
    }

    public static function createGoogleCalendarEvent(GoogleCalendar $service, String $calendarId, String $dateStart, String $dateFinish, String $summary, String $descripcio, String $timeZone) {
        $event = new GoogleCalendarEvent(array(
            'summary' => $summary,
            'description' => $descripcio,
            'start' => array(
              'dateTime' => $dateStart,
              'timeZone' => $timeZone,
            ),
            'end' => array(
                'dateTime' => $dateFinish,
                'timeZone' => $timeZone,
            ),
            'reminders' => array(
              'useDefault' => FALSE,
              'overrides' => array(
                array('method' => 'email', 'minutes' => 24 * 60),
                array('method' => 'popup', 'minutes' => 10),
              ),
            ),
          ));
          
          $event = $service->events->insert($calendarId, $event);
          return $event;
    }
}
