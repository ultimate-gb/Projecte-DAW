<?php

namespace App\Models;

use App\Models\Ajuda;
use App\Models\Users;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Factories\HasFactory;

class Calendar extends Model
{
    use HasFactory;
    public $table = "calendari";
    public $fillable = array('id', "nom", "data_creacio", "user");
    public $timestamps = false;

    public function Users(): \Illuminate\Database\Eloquent\RelatioNS\BelongsTo
    { 
        return $this->belongsTo(Users::class, 'user', "id");
    }
    public function Ajuda(): \Illuminate\Database\Eloquent\RelatioNS\HasMany
    { 
        return $this->hasMany(Ajuda::class, "calendari", "id");
    }
}
