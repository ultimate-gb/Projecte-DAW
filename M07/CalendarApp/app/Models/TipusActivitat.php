<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class TipusActivitat extends Model
{
    use HasFactory;
    public $table = "tipus_activitat";
    public $fillable = array('codi', "nom", "user");
    public $timestamps = false;

    public function Propietari(): \Illuminate\Database\Eloquent\RelatioNS\BelongsTo
    {
        return $this->belongsTo(Users::class, "user", "id");
    }
}
