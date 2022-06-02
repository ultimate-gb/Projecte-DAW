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
        <section class="table-responsive mb-3">
            <table class="table table-striped">
                @if (!$isMobile)
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
                @endif
                <tbody>
                @foreach($activitats as $item)
                    @if (!$isMobile)
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
                    @else
                        <tr>
                            <td scope="row">#</td>
                            <td>{{$item->id}}</td>
                        </tr>
                        <tr>
                            <td scope="row">Nom</td>
                            <td>{{$item->nom}}</td>
                        </tr>
                        <tr>
                            <td>Data Inici</td>
                            <td>{{$item->data_inici}}</td>
                        </tr>
                        <tr>
                            <td>Data Fi</td>
                            <td>{{$item->data_fi}}</td>
                        </tr>
                        <tr>
                            <td>Descripcio</td>
                            <td>{{$item->descripcio}}</td>
                        </tr>
                        <tr>
                            <td>Propietari</td>
                            @if ($esPropietari)
                                @if ($user->id == $item->user)
                                        <td>Jo</td>
                                @else
                                        <td>{{ $item->Propietari()->get()->first()->nom . " " . $item->Propietari()->get()->first()->cognoms }}</td>
                                @endif    
                            @endif
                        </tr>
                        <tr>
                            <td>Tipus</td>
                            <td>{{ $item->Tipus()->get()->first()->nom }}</td>
                        </tr>
                        <tr>
                            <td>Publicada</td>
                            <td>
                                @if($item->publicada == 1)
                                    <i class="fa fa-check"></i>
                                @else
                                    <i class="fa fa-times"></i>
                                @endif
                            </td>
                        </tr>
                        <tr>
                            <td>Opcions</td>
                            <td>
                                <div class="d-flex gap-2">
                                    <a href="{{ route("activitat", ['op'=>"edit", "id"=>$item->id, "calendariId"=>$calendari->id]) }}" class="btn btn-secondary w-100"><i class="fas fa-edit"></i></a>
                                    <a href="{{ route("activitat", ['op'=>"del", "id"=>$item->id, "calendariId"=>$calendari->id]) }}" class="btn btn-danger w-100"><i class="fas fa-trash-alt"></i></a>
                                </div>
                            </td>
                        </tr>
                    @endif
                @endforeach
                </tbody>
            </table>
            <table class="table table-striped">
                <tbody>
                @foreach($activitats as $item)

                @endforeach
                </tbody>
            </table>
            {{$activitats->links()}}
        </section>
        @if ($esPropietari)
            <section class="mb-3">
                <header class="titolZone">
                    <h2>Ajudants: </h2>
                    <a href="{{ route("ajudants", ['op'=>"add", "id"=>"-1", 'calendariId'=>$calendari->id]) }}" class="btn btn-primary bg-newblue"><i class="fas fa-plus"></i></a>
                </header>
                <main class="table-responsive w-100">
                    <table class="table table-striped">
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
                                    <a href="{{ route("ajudants", ['op' =>"del","id"=>$item->id, 'calendariId'=>$calendari->id]) }}" class="btn btn-danger w-100"><i class="fas fa-trash-alt"></i></a>
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
        <section class="container mb-3">
            <header class="titolZone">
                <h2>Destinataris: </h2>
                <a href="{{ route("calendar.destinataris",['id'=>$calendari->id]) }}" class="btn btn-primary bg-newblue"><i class="fas fa-plus"></i></a>
            </header>
            <table class="table table-striped">
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
            {{$targets->links()}}
        </section>
        <section class="container mb-3" id="publicacio">
            <header class="titolZone">
                <h4>Vols Publicar El Calendari?</h4>
            </header>
            <main>
                <p>Si es aixi pressiona el buto de publica</p>
                <a href="{{ route("calendar.publicar", ["id"=>$calendari->id]) }}" class="btn btn-primary bg-newblue">Publicar</a>
            </main>
            
        </section>
    @endif
    <section class="container mb-3" id="exportacio">
        <header class="titolZone">
            <h4>Vols Exportar Les Activitats del Calendari?</h4>
        </header>
        <main>
            <p>Si es aixi pressiona el buto d'exporta</p>
            <a href="{{ route("calendar.export", ["id"=>$calendari->id]) }}" class="btn btn-primary bg-newblue">Exportar</a>
        </main>    
    </section>
@endsection