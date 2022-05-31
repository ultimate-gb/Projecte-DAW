<?php

namespace App\Models;

use App\Models\Users;
use App\Models\Calendar;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Factories\HasFactory;

class Ajuda extends Model
{
    use HasFactory;
    public $table = "ajuda";
    public $fillable = array('user', "calendari");
    public $timestamps = false;

    public function Users(): \Illuminate\Database\Eloquent\RelatioNS\BelongsTo
    { 
        return $this->belongsTo(Users::class, "user", "id");
    }

    public function CalendariAjudant(): \Illuminate\Database\Eloquent\RelatioNS\BelongsTo
    {
        return $this->belongsTo(Calendar::class, "calendari");
    }
}
