<?php

namespace App\Mail;

use Illuminate\Bus\Queueable;
use Illuminate\Contracts\Queue\ShouldQueue;
use Illuminate\Mail\Mailable;
use Illuminate\Queue\SerializesModels;

class VerifyMail extends Mailable
{
    use Queueable, SerializesModels;

    private String $nom;
    private String $token;

    /**
     * Create a new message instance.
     *
     * @return void
     */
    public function __construct(String $nom, String $token)
    {
        $this->nom = $nom;
        $this->token = $token;
    }

    /**
     * Build the message.
     *
     * @return $this
     */
    public function build()
    {
        return $this->subject("Validacio Compte Calendar App")->view('emails.verifyMail', array('nom'=>$this->nom,"token"=>$this->token));
    }
}
