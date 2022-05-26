@extends('layouts.baseMap')

@section('webTitle', "Admin Zone")

@section('bodyId', "adminZone")

@php
    $classBtn = "btn btn-primary bg-newblue w-100";
    $inputClass = "w-100 h-100";
@endphp

@section('mainContent')
    <h1>Hello World From Admin</h1>
    <main>
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

          <h3>Canviar Usuari:</h3>
          <div class="mb-5">
            <form action="{{ route('admin.changeUser') }}" class="d-flex gap-2" method="post">
                @csrf
                <select name="user" class="form-select">
                    @foreach ($userList as $u)
                        @php
                            $selected = "";
                            if($userIdSel == $u['id']) {
                                $selected = "selected";
                            }
                        @endphp
                        <option value="{{ $u['id'] }}" {{ $selected }}>{{ $u['nom'] }}</option>
                    @endforeach
                </select>
                <button type="submit" name='changeUser' class="btn btn-primary bg-newblue w-25">Canviar Usuari</button>
            </form>
          </div>
    </main>

    <div id="mouse-position" class="d-none"></div>
@endsection