<?php

namespace App\Rules;

use Illuminate\Contracts\Validation\Rule;

class CsvExtensionValidator implements Rule
{
    /**
     * Create a new rule instance.
     *
     * @return void
     */
    public function __construct()
    {
        //
    }

    /**
     * Determine if the validation rule passes.
     *
     * @param  string  $attribute
     * @param  mixed  $value
     * @return bool
     */
    public function passes($attribute, $value)
    {
        $dotPos = strrpos($value->getClientOriginalName(), "."); //  Busca el punt en el nom del fitxer
        $ext = substr($value->getClientOriginalName(), $dotPos); // Obte l'extensio
        if($ext == '.csv') {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Get the validation error message.
     *
     * @return string
     */
    public function message()
    {
        return 'El fitxer donat ha de ser un fitxer csv.';
    }
}
