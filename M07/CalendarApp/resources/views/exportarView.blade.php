@extends('layouts.baseToReturnBack')

@section('webTitle', "Calendari -> Exportacio")

@section('returnBack', route("calendar.see", ["id"=>$calendar]))

@section('webZone', "Calendari -> Exportarcio")

@section('bodyId', "calendariExport")

@section('scriptHeaderZone')
    <script src="{{ asset('js/inputsScript.js') }}"></script>
@endsection

@section('mainContent')
    @if (strlen($message))
        <div class="alert alert-{{ $tipus }} top-message" role="alert">
            {{ $message }}
        </div>
    @endif
    <h2>Exportar Activitats: </h2>
    <form class="container mt-3" method="POST" action="{{ route('calendar.export.on') }}"> 
        <input type="hidden" name="calendar" value="{{ $calendar }}">
        <div class="d-flex justify-content-end">
            <input type="submit" name="add" value="Descarregar" class="btn btn-secondary btn-manager">
        </div>
    </form>
@endsection