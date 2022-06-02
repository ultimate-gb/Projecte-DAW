@extends('layouts.base')

@section('webTitle', "Index")

@section('webZone', "Pagina Principal")

@section('bodyId', "index")

@section('scriptHeaderZone')
@endsection

@section('mainContent')
    @if (strlen($message))
        <div class="alert alert-{{ $tipus }}" role="alert">
            {{ $message }}
        </div>
    @endif
    <div class="titolZone">
        <h2>Els Meus Calendaris</h2>
        <a href="{{ route("calendar", ['op'=>"add", "id"=>"-1"]) }}" class="btn btn-primary bg-newblue"><i class="fas fa-plus"></i></a>
    </div>
    <main class="container">
        <section>
            @foreach ($calendariPropis as $item)
                <article class="calendariArt">
                    <header class="d-flex justify-content-center">
                        <h3>{{ $item['nom'] }}</h3>
                    </header>
                    <main class="container">
                        <div class="row mb-2 line">
                            <i class="col-2 fas fa-calendar-plus"></i>
                            <p class="col-10">{{ $item['data_creacio'] }}</p>
                        </div>
                        <div class="d-flex gap-2 mb-2">
                            <a href="{{ route("calendar.see", ['id'=>$item->id]) }}" class="btn btn-primary bg-newblue w-100"><i class="fas fa-eye"></i></a>
                            <a href="{{ route("calendar", ['op'=>"edit", "id"=>$item->id]) }}" class="btn btn-secondary w-100"><i class="fas fa-edit"></i></a>
                            <a href="{{ route("calendar", ['op' =>"del","id"=>$item->id]) }}" class="btn btn-danger w-100"><i class="fas fa-trash-alt"></i></a>
                        </div>
                    </main>
                </article>
            @endforeach
        </section>
    </main>
    <h2>Els Calendaris on ajudo</h2>
    <section class="container">
        <div>
            @php
                $i = 0;
            @endphp
            @foreach ($calendariAjudant as $item)
                <article class="calendariArt">
                    <header class="d-flex justify-content-center">
                        <h3>{{ $item['nom'] }}</h3>
                    </header>
                    <main class="container">
                        <div class="row mb-2 line">
                            <i class="col-2 fas fa-calendar-plus"></i>
                            <p class="col-10">{{ $item['data_creacio'] }}</p>
                        </div>
                        <div class="row mb-2 line">
                            <i class="col-2 fas fa-user"></i>
                            <p class="col-10">{{ $userNameList[$i] }}</p>
                        </div>
                        <div class="d-flex gap-2 mb-2">
                            <a href="{{ route("calendar.see", ['id'=>$item->id]) }}" class="btn btn-primary bg-newblue w-100"><i class="fas fa-eye"></i></a>
                        </div>
                    </main>
                </article>
                @php
                    $i++;
                @endphp
            @endforeach
        </div>
    </section>
    <section class="container mb-3" id="informe">
        <header class="titolZone">
            <h4>Vols Un Informe de les Activitats del Calendari?</h4>
        </header>
        <main>
            <p>Si es aixi pressiona el buto d'informe</p>
            <a href="{{ route("calendar.inform") }}" class="btn btn-primary bg-newblue">Inform</a>
        </main>    
    </section>  
@endsection