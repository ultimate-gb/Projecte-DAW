@extends('layouts.baseToReturnBack')

@section('webTitle', "Informe")

@section('returnBack', route("index"))

@section('webZone', "Informe")

@section('bodyId', "informe")

@section('scriptHeaderZone')
    <script src="{{ asset('js/inputsScript.js') }}"></script>
@endsection

@section('mainContent')
    @if (strlen($message))
        <div class="alert alert-{{ $tipus }} top-message" role="alert">
            {{ $message }}
        </div>
    @endif
    <h2>Publicar Activitats: </h2>
    <form class="container mt-3" method="POST" action="{{ route('calendar.inform.on') }}"> 
        @csrf
        <section class="table-responsive">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Seleccionar</th>
                        <th scope="row">#</th>
                        <th>Nom</th>
                    </tr>
                </thead>
                <tbody>
                @foreach($calendaris as $item)
                    <tr>
                        <td><input type="checkbox" name="informe[]" value="{{ $item->id }}"></td>
                        <td scope="row">{{$item->id}}</td>
                        <td>{{ $item->nom }}</td>
                    </tr>
                @endforeach
                </tbody>
            </table>
        </section>
        <div class="d-flex justify-content-end">
            <input type="submit" name="informeBtn" value="Informe" class="btn btn-secondary btn-manager">
        </div>
    </form>
@endsection