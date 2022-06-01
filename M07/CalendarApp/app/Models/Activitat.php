<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Activitat extends Model
{
    use HasFactory;
    public $table = "activitat";
    public $fillable = array('id', "calendari", "nom", "data_inici", "data_fi", "descripcio", "tipus", "user", "publicada");
    public $timestamps = false;

    public function Calendari(): \Illuminate\Database\Eloquent\RelatioNS\BelongsTo
    {
        return $this->belongsTo(Calendar::class, "calendari", "id");
    }

    public function Propietari(): \Illuminate\Database\Eloquent\RelatioNS\BelongsTo
    {
        return $this->belongsTo(Users::class, "user", "id");
    }

    public function Tipus(): \Illuminate\Database\Eloquent\RelatioNS\BelongsTo
    {
        return $this->belongsTo(TipusActivitat::class, "tipus", "codi");
    }
}
