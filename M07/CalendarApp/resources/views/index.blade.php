@extends('layouts.base')

@section('webTitle', "Index")

@section('bodyId', "index")

@section('scriptHeaderZone')
    <script src="{{ asset('js/inputsScript.js') }}"></script>
@endsection

@section('mainContent')
    <h2>Els Meus Calendaris</h2>
    <table>
        
    </table>
    <h2>Els Calendaris on ajudo</h2>
@endsection