<?php

use App\Http\Controllers\ActivitatsController;
use App\Mail\VerifyMail;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\AppController;
use App\Http\Controllers\CalendarController;
use App\Http\Controllers\LoginController;
use App\Http\Controllers\AjudantsController;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});

Route::get('/login', [LoginController::class, 'login'])->withoutMiddleware(\App\Http\Middleware\CheckLogin::class);
Route::post('/login/check', [LoginController::class, 'check'])->name("login.check")->withoutMiddleware(\App\Http\Middleware\CheckLogin::class);
Route::get('/login/logout', [LoginController::class, 'logout'])->name('login.logout');

Route::get('/index', [AppController::class, 'index'])->name("index");
Route::get('/register', [AppController::class, 'register'])->name("register")->withoutMiddleware(\App\Http\Middleware\CheckLogin::class);
Route::post("/register/save", [AppController::class, 'registerSave'])->name("register.save")->withoutMiddleware(\App\Http\Middleware\CheckLogin::class);
Route::get("/register/completarUsuari/{token}", [AppController::class, 'registerCompletar'])->withoutMiddleware(\App\Http\Middleware\CheckLogin::class);
Route::get("/register/acceptarInvitacio/{token}", [AppController::class, 'registerAcceptarInvitacio'])->name("register.invitation.save")->withoutMiddleware(\App\Http\Middleware\CheckLogin::class);
Route::post("/register/update", [AppController::class, 'registerUpdate'])->name("register.update")->withoutMiddleware(\App\Http\Middleware\CheckLogin::class);

Route::get("/calendar/{op}/{id}", [CalendarController::class, 'index'])->name('calendar');
Route::get("/calendar/see", [CalendarController::class, 'see'])->name('calendar.see');
Route::post("/calendar/create", [CalendarController::class, 'add'])->name('calendar.add');
Route::post("/calendar/mod", [CalendarController::class, 'edit'])->name('calendar.edit');
Route::delete("/calendar/destroy", [CalendarController::class, 'destroy'])->name('calendar.del');

Route::get("/ajudants/{op}/{id}/{calendariId}", [AjudantsController::class, 'index'])->name("ajudants");
Route::post("/ajudants/create", [AjudantsController::class, 'add'])->name("ajudants.add");
Route::delete("/ajudants/destroy", [AjudantsController::class, 'destroy'])->name("ajudants.del");

Route::get("/activitat/{op}/{id}/{calendariId}", [ActivitatsController::class, "index"])->name("activitat");
Route::post("/activitat/create", [ActivitatsController::class, "add"])->name("activitat.add");
Route::post("/activitat/mod", [ActivitatsController::class, "edit"])->name("activitat.edit");
Route::delete("/activitat/destroy", [ActivitatsController::class, "destroy"])->name("activitat.del");

Route::get('/help', function() {
    return view('help');
})->withoutMiddleware(\App\Http\Middleware\CheckLogin::class)->name("help");

Route::get("/calendar/destinataris", [CalendarController::class, "vistaAfegirDestinataris"])->name("calendar.destinataris");
Route::post("/calendar/destinataris/add", [CalendarController::class, "afegirDestinataris"])->name("calendar.destinataris.add");

Route::get("/calendar/publicar", [CalendarController::class, "publicar"])->name("calendar.publicar");
Route::get("/calendar/publicar/on", [CalendarController::class, "publicarActivitats"])->name("calendar.publicar.on");

Route::get("/calendar/export", [CalendarController::class, "export"])->name("calendar.export");
Route::post("/calendar/export/on", [CalendarController::class, "exportarActivitats"])->name("calendar.export.on");