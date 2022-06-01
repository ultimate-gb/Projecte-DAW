<?php

namespace App\Rules;

use Illuminate\Contracts\Validation\Rule;

class ValidarDataFi implements Rule
{
    private $dataInici; 
    /**
     * Create a new rule instance.
     *
     * @return void
     */
    public function __construct($dataInici)
    {
        $this->dataInici = $dataInici;
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
        if($value == null) {
            return true;
        }
        $data =strtotime($value);
        if($this->dataInici > $data) {
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
        return "Data de Fi ha de ser posterior a la d'inici.";
    }
}
