@extends('layouts.baseMap')

@section('webTitle', "User Zone")

@section('bodyId', "userZone")

@section('webZone', "User Zone")

@php
    $classBtn = "btn btn-primary bg-newblue w-100";
    $inputClass = "w-100 h-100";
@endphp

@section('mainContent')
    <h1>Hello World</h1>
    <div id="map" class="map"></div>
    @if (count($municipiList) > 0)
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">Ordre</th>
                <th scope="col">Municipi</th>
                <th scope="col">Completat</th>
            </tr>
            </thead>
            <tbody>
                @foreach ($municipiList as $item)
                    <tr>
                        <th scope="row">{{ $item['id'] }}</th>
                        <td>{{ $item['municipi'] }}</td>
                        @if ($item['complet']== 1)
                            <td>Si</td>
                        @else
                            <td>No</td>
                        @endif
                    </tr>
                @endforeach
            </tbody>
        </table>
    @else
        <p>Voste no te treball el dia de avui</p>
    @endif
    <div id="mouse-position" class="d-none"></div>
@endsection