<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Nacionalitat extends Model
{
    use HasFactory;
    public $table = "nacionalitat";
    public $fillable = array('codi', "nom");
    public $timestamps = false;
}
