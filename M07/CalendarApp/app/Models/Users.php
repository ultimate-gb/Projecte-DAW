<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Users extends Model
{
    use HasFactory;
    public $table = "users";
    public $fillable = array('id', "email", "nom", "cognoms", "password", "data_naix", "genere", "telefon", "bloquejat", "nacionalitat", "role");
    public $timestamps = false;
}
