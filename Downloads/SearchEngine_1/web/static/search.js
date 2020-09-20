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

 $("#siteImage").hide();
  $("#customers").show();
   $("#images").attr("style","color:black");
    $("#all").attr("style","color:black");
  
  
  sessionStorage.getItem('SelectedItem')!=null?
 $("#noResult").append('<option value=0 selected='+sessionStorage.getItem('SelectedItem')+'>'+sessionStorage.getItem('SelectedItem')+'</option>') :
         $("#noResult").append('<option value=5 selected=selected>--select--</option>');

$.each(myOptions, function(val, text) {
    if(sessionStorage.getItem('SelectedItem')!=null) {
        if(sessionStorage.getItem('SelectedItem')!=text) {
    $("#noResult").append(
        $('<option></option>').val(val).html(text)
    );
        }
    }else {
        $("#noResult").append(
        $('<option></option>').val(val).html(text)
    );
    }
    
});

$("#images").click(function(){
    
    $("#siteImage").show();
    $("#customers").hide();
     $(this).attr("style","color:blue");
     $("#all").attr("style","color:black");
})

$("#all").click(function(){
    
    $("#siteImage").hide();
    $("#customers").show();
    $(this).attr("style","color:blue");
     $("#images").attr("style","color:black");
})
 $("#noResult").change(function(){
     $("#pBar").show();
     sessionStorage.setItem('SelectedItem',  $("#noResult").val());
       $( "#searchFrm" ).submit();
       //$("#pBar").hide();
 })   
    
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
     
   	$('.star').on('click', function () {
      $(this).toggleClass('star-checked');
    });

    $('.ckbox label').on('click', function () {
      $(this).parents('tr').toggleClass('selected');
    });

    $('.btn-filter').on('click', function () {
      var $target = $(this).data('target');
      if ($target != 'all') {
        $('.table tr').css('display', 'none');
        $('.table tr[data-status="' + $target + '"]').fadeIn('slow');
      } else {
        $('.table tr').css('display', 'none').fadeIn('slow');
      }
    });  
    
    
})