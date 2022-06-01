@extends('layouts.baseToReturnBack')

@section('webTitle', "Calendari -> Destinataris")

@section('returnBack', route("calendar.see", ["id"=>$calendar]))

@section('webZone', "Calendari -> Destinataris Manager")

@section('bodyId', "calendariDestinatarisManager")

@section('scriptHeaderZone')
    <script src="{{ asset('js/inputsScript.js') }}"></script>
@endsection

@section('mainContent')
    @if (strlen($message))
        <div class="alert alert-{{ $tipus }}" role="alert">
            {{ $message }}
        </div>
    @endif
    @if ($errors->any())
    <div class="alert alert-danger">
        <ul>
            @foreach ($errors->all() as $error)
                <li>{{ $error }}</li>
            @endforeach
        </ul>
    </div>
    @endif
    <h2>Afegir Destinataris: </h2>
    <form class="container mt-3" method="POST" action="{{ route('calendar.destinataris.add') }}" enctype="multipart/form-data">
        @csrf
        <div class="input-group mb-3">
            <label class="input-group-text" for="csvFile">Fitxer CSV</label>
            <input type="file" class="form-control" name="csvFile" id="csvFile">
        </div>
         
        <input type="hidden" name="calendar" value="{{ $calendar }}">
        <div class="d-flex justify-content-end">
            <input type="submit" name="add" value="Afegir" class="btn btn-secondary btn-manager">
        </div>
    </form>
@endsection