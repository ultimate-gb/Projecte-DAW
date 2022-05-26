@extends('layouts.baseToReturnBack')

@section('webTitle', "qt")

@section('bodyId', "qtForm")

@section('mainContent')
    <form action="{{route('index.qt.save')}}" method="post" class="mt-5">
        @csrf
        <div class="input-group mb-3">
            <label class="input-group-text d-flex justify-content-center" for="qtInput">Quantitat</label>
            <input type="number" name='qt' class="form-control" id="qtInput" placeholder="Quantitat Recollida" aria-label="qt" aria-describedby="qt">
        </div>
        <div class="col-12 d-flex justify-content-end">
            <button type="submit" name='completarBtn' class="btn btnLogin btn-primary bg-newblue">Completar</button>
        </div>
        <input type="hidden" name="latitud" value="{{ $lat }}">
        <input type="hidden" name="longitud" value="{{ $long }}">
    </form>
@endsection