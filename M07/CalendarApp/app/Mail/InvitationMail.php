<?php

namespace App\Mail;

use Illuminate\Bus\Queueable;
use Illuminate\Contracts\Queue\ShouldQueue;
use Illuminate\Mail\Mailable;
use Illuminate\Queue\SerializesModels;

class InvitationMail extends Mailable
{
    use Queueable, SerializesModels;

    private String $token;

    /**
     * Create a new message instance.
     *
     * @return void
     */
    public function __construct(String $token)
    {
        $this->token = $token;
    }

    /**
     * Build the message.
     *
     * @return $this
     */
    public function build()
    {
        return $this->subject("Invitacio Calendar App")->view('emails.invitateMail', array("token"=>$this->token));
    }
}
