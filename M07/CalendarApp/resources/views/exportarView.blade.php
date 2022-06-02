@extends('layouts.baseToReturnBack')

@section('webTitle', "Calendari -> Exportacio")

@section('returnBack', route("calendar.see", ["id"=>$calendar]))

@section('webZone', "Calendari -> Export")

@section('bodyId', "calendariExport")

@section('scriptHeaderZone')
    <script src="{{ asset('js/inputsScript.js') }}"></script>
@endsection

@php
    $labelClassCols = "col-2";
    $inputClassCols = "col-10";
@endphp

@section('mainContent')
    @if (strlen($message))
        <div class="alert alert-{{ $tipus }} top-message" role="alert">
            {{ $message }}
        </div>
    @endif
    <h2>Exportar Activitats: </h2>
    <p>En aquesta pagina pots exportar les activitats del calendari {{ $calendari->nom }}</p>
    <ul>
        <li>Si vols exportar totes les activitats deixa les dates en blanc</li>
        <li>Si vols exportar un rang de activitats indica les dates</li>
    </ul>
    <form class="container mt-3" method="POST" action="{{ route('calendar.export.on') }}"> 
        @csrf
        <div class="input-group mb-3">
            <label class="input-group-text {{ $labelClassCols }}" for="dtIniciInput">Data Inici</label>
            <input type="datetime-local" class="form-control {{ $inputClassCols }}" id="dtIniciInput" name="dataInici" placeholder=""  aria-label="DataInici" aria-describedby="dtIniciInputInput">
        </div>
        <div class="input-group mb-3">
            <label class="input-group-text {{ $labelClassCols }}" for="dtIniciInput">Data Fi</label>
            <input type="datetime-local" class="form-control {{ $inputClassCols }}" id="dtFiInput" name="dataFi" placeholder="" aria-label="DataFi" aria-describedby="dtFiInput">
        </div>
        <input type="hidden" name="calendar" value="{{ $calendar }}">
        <div class="d-flex justify-content-end">
            <input type="submit" name="add" value="Descarregar" class="btn btn-secondary btn-manager">
        </div>
    </form>
@endsection