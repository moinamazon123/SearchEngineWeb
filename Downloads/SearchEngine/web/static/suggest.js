/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$( function() {
    var availableTags = [
      "ActionScript",
      "AppleScript",
      "Asp",
      "British",
      "Tom Cruise",
      "Jack Ryan",
      "Ana de Armas",
      "Most Popular Movies",
      "Traylor Howard",
      "corpora",
      "Corona",
      "Corpus del Español",
      "Covid",
      "MySql",
      "Sql",
      "Database",
      "Global Web-Based English (GloWbE)",
      "New",
      "NEWSPAPER",
      "Time",
      "Magazine",
      "Tony Manero",
      "New York",
      "Haskell",
      "Scheme",
      "world"
    ];
    $( "#tags" ).autocomplete({
      source: availableTags
    });
  } );