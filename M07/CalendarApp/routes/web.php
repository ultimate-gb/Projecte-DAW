<?php

use App\Mail\VerifyMail;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\AppController;
use App\Http\Controllers\CalendarController;
use App\Http\Controllers\LoginController;

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
Route::get("/calendar/{op}/{id}", [CalendarController::class, 'index'])->name('calendar');
Route::get("/calendar/see", [CalendarController::class, 'see'])->name('calendar.see');
Route::post("/calendar/create", [CalendarController::class, 'add'])->name('calendar.add');
Route::post("/calendar/mod", [CalendarController::class, 'edit'])->name('calendar.edit');
Route::delete("/calendar/destroy", [CalendarController::class, 'destroy'])->name('calendar.del');

Route::get('/help', function() {
    return view('help');
})->withoutMiddleware(\App\Http\Middleware\CheckLogin::class)->name("help");