<?php

namespace App\Rules;

use App\Http\Controllers\MyUtilities;
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
        $ext = MyUtilities::extensionDicover($value->getClientOriginalName());
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
