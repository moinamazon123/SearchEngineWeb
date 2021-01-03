/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function(){
    
    
     var myOptions = {
      5 : 5,
      10 : 10,
      15 : 15,
      20 : 20,
    
    
};

$.each(myOptions, function(val, text) {
    $("#noResult").append(
        $('<option></option>').val(val).html(text)
    );
});
    
    
   $("#docLi").addClass('index__active'); 
      $("#relLi").addClass('index__active'); 
    $("#scLi").addClass('index__active'); 
      $("[id^='path']").show();
      
      $("[id^='scoreResult']" ).hide();
  
  $(".index__button").click(function() {
      
        $("#docLi").removeClass('index__active');
        $("#relLi").addClass('index__active'); 
         $("#scLi").addClass('index__active'); 
    
      
        $("[id^='relWord']" ).hide();
         $("[id^='searchResult']" ).show();
            $("[id^='scoreResult']" ).hide();
            $("[id^='path']").show();
   });
  
    $("#relKeyWord").click(function() {
        $("#relLi").removeClass('index__active');
        $("#docLi").addClass('index__active'); 
         $("#scLi").addClass('index__active'); 
    
       
        $("[id^='relWord']" ).show();
         $("[id^='searchResult']" ).hide();
            $("[id^='scoreResult']" ).hide();
               $("[id^='path']").show();
        
   });
    $("#docKeyWord").click(function() {
      
       $("#docLi").removeClass('index__active');
        $("#relLi").addClass('index__active'); 
         $("#scLi").addClass('index__active'); 
    
      
        $("[id^='relWord']" ).hide();
         $("[id^='searchResult']" ).show();
            $("[id^='scoreResult']" ).hide();
              
                 $("[id^='path']").show();
});

$("#scoreKeyWord").click(function() {
      
       $("#docLi").addClass('index__active');
        $("#relLi").addClass('index__active'); 
         $("#scLi").removeClass('index__active'); 
    
      
        $("[id^='relWord']" ).hide();
         $("[id^='searchResult']" ).hide();
           $("[id^='scoreResult']" ).show();
             $("[id^='path']").hide();
});
     
     
    
    
})