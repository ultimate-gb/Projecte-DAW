@extends('layouts.baseToReturnBack')

@section('webTitle', "Mostrant Calendari")

@section('webZone', "Mostrant Calendari")

@section('bodyId', "calendariViewer")

@section('returnBack', route('index'))

@section('scriptHeaderZone')
@endsection

@section('mainContent')
    @if (strlen($message))
        <div class="alert alert-{{ $tipus }}" role="alert">
            {{ $message }}
        </div>
    @endif
    <div class="titolZone">
        <h2>Calendari: {{ $calendari->nom }}</h2>
        <a href="{{ route("activitat", ['op'=>"add", "id"=>"-1", "calendariId"=>$calendari->id]) }}" class="btn btn-primary bg-newblue"><i class="fas fa-plus"></i></a>
    </div>
    <main class="container">
        <section class="table-responsive">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th scope="row">#</th>
                        <th>Nom</th>
                        <th>Data Inici:</th>
                        <th>Data Fi</th>
                        <th>Descripcio</th>
                        @if ($esPropietari)
                            <th>Propietari</th>
                        @endif
                        <th>Tipus</th>
                        <th>Publicada</th>
                        <th>Opcions</th>
                    </tr>
                </thead>
                <tbody>
                @foreach($activitats as $item)
                    <tr>
                        <td scope="row">{{$item->id}}</td>
                        <td>{{$item->nom}}</td>
                        <td>{{$item->data_inici}}</td>
                        <td>{{$item->data_fi}}</td>
                        <td>{{$item->descripcio}}</td>
                        @if ($esPropietari)
                            @if ($user->id == $item->user)
                                <td>Jo</td>
                            @else
                                <td>{{ $item->Propietari()->get()->first()->nom . " " . $item->Propietari()->get()->first()->cognoms }}</td>
                            @endif
                            
                        @endif
                        <td>{{ $item->Tipus()->get()->first()->nom }}</td>
                        <td>
                            @if($item->publicada == 1)
                                <i class="fa fa-check"></i>
                            @else
                                <i class="fa fa-times"></i>
                            @endif
                        </td>
                        <td>
                            <div class="d-flex gap-2 mb-2">
                                <a href="{{ route("activitat", ['op'=>"edit", "id"=>$item->id, "calendariId"=>$calendari->id]) }}" class="btn btn-secondary w-100"><i class="fas fa-edit"></i></a>
                                <a href="{{ route("activitat", ['op'=>"del", "id"=>$item->id, "calendariId"=>$calendari->id]) }}" class="btn btn-danger w-100"><i class="fas fa-trash-alt"></i></a>
                            </div>
                        </td>
                    </tr>
                @endforeach
                </tbody>
            </table>
            {{$activitats->links()}}
        </section>
        @if ($esPropietari)
            <section>
                <header class="titolZone">
                    <h2>Ajudants: </h2>
                    <a href="{{ route("ajudants", ['op'=>"add", "id"=>"-1", 'calendariId'=>$calendari->id]) }}" class="btn btn-primary bg-newblue"><i class="fas fa-plus"></i></a>
                </header>
                <main class="table-responsive w-100">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th scope="rol">#</th>
                                <th>Nom</th>
                                <th>Cognoms</th>
                                <th>Opcions</th>
                            </tr>
                        </thead>
                        <tbody>
                        @foreach($ajudants as $item)
                            <tr>
                                <td scope="row">{{$item->id}}</td>
                                <td>{{$item->nom}}</td>
                                <td>{{$item->cognoms}}</td>
                                <td>
                                    <div class="d-flex gap-2 mb-2">
                                        <a href="{{ route("ajudants", ['op' =>"del","id"=>$item->id, 'calendariId'=>$calendari->id]) }}" class="btn btn-danger w-100"><i class="fas fa-trash-alt"></i></a>
                                    </div>
                                </td>
                            </tr>
                        @endforeach
                        </tbody>
                    </table>
                    {{$ajudants->links()}}
                </main>
            </section>
        @endif
    </main>
    @if ($esPropietari)
        <section class="container">
            <header class="titolZone">
                <h2>Destinataris: </h2>
                <a href="{{ route("calendar.destinataris",['id'=>$calendari->id]) }}" class="btn btn-primary bg-newblue"><i class="fas fa-plus"></i></a>
            </header>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Email</th>
                    </tr>
                </thead>
                <tbody>
                @foreach($targets as $item)
                    <tr>
                        <td>{{$item->email}}</td>
                    </tr>
                @endforeach
                </tbody>
            </table>
        </section>
        <section class="container">
            <header class="titolZone">
                <h4>Vols Publicar El Calendari?</h4>
            </header>
            <main>
                <p>Si es aixi pressiona el buto de publica</p>
                <a href="{{ route("calendar.publicar", ["id"=>$calendari->id]) }}" class="btn btn-primary bg-newblue">Publicar</a>
            </main>
            
        </section>
    @endif
    <section class="container">
        <header class="titolZone">
            <h4>Vols Exportar Les Activitats del Calendari?</h4>
        </header>
        <main>
            <p>Si es aixi pressiona el buto d'exporta</p>
            <a href="{{ route("calendar.export", ["id"=>$calendari->id]) }}" class="btn btn-primary bg-newblue">Exportar</a>
        </main>    
    </section>  
@endsection