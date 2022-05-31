<?php

namespace App\Models;

use App\Models\Calendar;
use App\Models\TipusActivitat;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Factories\HasFactory;

class Users extends Model
{
    use HasFactory;
    public $table = "users";
    public $fillable = array('id', "email", "nom", "cognoms", "password", "data_naix", "genere", "telefon", "bloquejat", "nacionalitat", "role", "token", "validat");
    public $timestamps = false;

    public function CalendariPropietari(): \Illuminate\Database\Eloquent\RelatioNS\HasMany
    {
        return $this->hasMany(Calendar::class, 'user', "id");
    }

    public function TipusActivitat(): \Illuminate\Database\Eloquent\RelatioNS\HasMany
    {
        return $this->hasMany(TipusActivitat::class, 'user', "id");
    }

}
