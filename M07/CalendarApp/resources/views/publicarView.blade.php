@extends('layouts.baseToReturnBack')

@section('webTitle', "Calendari -> Publicar")

@section('returnBack', route("calendar.see", ["id"=>$calendar]))

@section('webZone', "Calendari -> Publicar")

@section('bodyId', "calendariPublicar")

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
    <form class="container mt-3" method="POST" action="{{ route('calendar.export.on') }}"> 
        <section class="table-responsive">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Seleccionar</th>
                        <th scope="row">#</th>
                        <th>Nom</th>
                        <th>Data Inici:</th>
                        <th>Data Fi</th>
                        <th>Descripcio</th>
                        <th>Tipus</th>
                        <th>Publicada</th>
                        <th>Opcions</th>
                    </tr>
                </thead>
                <tbody>
                @foreach($activitats as $item)
                    <tr>
                        <td><input type="checkbox" name="publicar[]"></td>
                        <td scope="row">{{$item->id}}</td>
                        <td>{{$item->nom}}</td>
                        <td>{{$item->data_inici}}</td>
                        <td>{{$item->data_fi}}</td>
                        <td>{{$item->descripcio}}</td>
                        <td>{{ $item->tipus }}</td>
                        <td>
                            @if($item->publicada == 1)
                                <i class="fa fa-check"></i>
                            @else
                                <i class="fa fa-times"></i>
                            @endif
                        </td>
                        <td>
                            <div class="d-flex gap-2 mb-2">
                                <a href="{{ route("activitat", ['op'=>"edit", "id"=>$item->id, "calendariId"=>$calendar]) }}" class="btn btn-secondary w-100"><i class="fas fa-edit"></i></a>
                                <a href="{{ route("activitat", ['op'=>"del", "id"=>$item->id, "calendariId"=>$calendar]) }}" class="btn btn-danger w-100"><i class="fas fa-trash-alt"></i></a>
                            </div>
                        </td>
                    </tr>
                @endforeach
                </tbody>
            </table>
            {{$activitats->links()}}
        </section>
        <input type="hidden" name="calendar" value="{{ $calendar }}">
        <div class="d-flex justify-content-end">
            <input type="submit" name="publicar" value="Publicar" class="btn btn-secondary btn-manager">
        </div>
    </form>
@endsection