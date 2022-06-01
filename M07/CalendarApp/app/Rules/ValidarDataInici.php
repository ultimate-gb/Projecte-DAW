<?php

namespace App\Rules;

use DateTime;
use Illuminate\Contracts\Validation\Rule;

class ValidarDataInici implements Rule
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
        $today = time();
        $data = strtotime($value);
        if($today > $data) {
            return false;
        }
        return true;
    }

    /**
     * Get the validation error message.
     *
     * @return string
     */
    public function message()
    {
        return "La data de inici a de ser posterior a l'actual.";
    }
}
