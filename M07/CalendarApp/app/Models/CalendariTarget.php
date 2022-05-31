<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class CalendariTarget extends Model
{
    use HasFactory;
    public $table = "calendari_target";
    public $fillable = array('calendar', "email");
    public $timestamps = false;

    public function Calendari(): \Illuminate\Database\Eloquent\RelatioNS\BelongsTo
    {
        return $this->belongsTo(Calendar::class, "calendar", "id");
    }
}
