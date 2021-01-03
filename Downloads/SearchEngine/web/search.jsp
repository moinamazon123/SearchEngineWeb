<%-- 
    Document   : search
    Created on : 4 Jun, 2020, 4:51:56 PM
   
--%>
  
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Search Engine</title>
  <meta name="description" value="The search engine that doesn't track you. Learn More.">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <link rel="stylesheet" type="text/css" href="static/colors.css">
  <link rel="stylesheet" type="text/css" href="static/modal.css">
  <link rel="stylesheet" type="text/css" href="static/index.css">
  <script src="static/jQuery.js"/>
  
 
  
  
</head>
<body>
    <script>
function myFunction() {
    var input, filter, ul, li, a, i, txtValue;
    input = document.getElementById("myInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myUL");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("a")[0];
        txtValue = a.textContent || a.innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";
        }
    }
}
</script>
<script>
    
    $(document).ready(function(){
    $("#docLi").addClass('index__active'); 
      $("#relLi").addClass('index__active'); 
    $("#scLi").addClass('index__active'); 
      $("[id^='path']").hide();
    
  
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
    </script>
     <a style="margin-left: 80%" href="admin.jsp">Admin</a>
      &nbsp; &nbsp; &nbsp;<a style="margin-top: 20%" href="webCrawl.jsp">Web Crawl</a>
  <div class="index">
     
     <!--  <a style="margin-left: 80%" href="admin_1.jsp?docFlag=y">Add Fields</a> -->
    <section class="index__header">
      <div class="index__logo"></div>
           <% if (session == null || (String)session.getAttribute("indexPath") == null) { %>
     <h4>Your session is expired </h4>
       
<% }%>
      <ul class="index__nav">
        <li id="docLi"><a id="docKeyWord" href="#">Document</a></li>
        
      
        <li id="relLi"><a id="relKeyWord" href="#">Relevant Keywords</a></li>
        
         <li id="scLi"><a id="scoreKeyWord" href="#">Sort by Score</a></li>
        
      </ul>
      

      <div class="index__search">
        <form class="index__form" action="SearchAction" method="POST">
          <div>
            <input name="q" type="search" placeholder="Search Keyword"
                   class="index__query" 
                   maxlength="512"
                   autocomplete="off"
                   title="Search"
                   aria-label="Search"
                   dir="ltr"
                   id="myInput"
                   spellcheck="false"
                   autofocus="autofocus"
                   
            >
         
          </div>
               
            <button class="index__button" onclick="javascript:document.getElementById('pBar').style.display='block'"  aria-label="Search" type="submit">
            <div class="index__ico"></div>
            <div id="pBar" style="display:none">
          <img src="images/spinner.gif">
      </div>
          </button>
            
           
        </form>
      </div>
    </section>
    <footer class="index__footer">
      <div class="index__bottom">
       
        <p class="index__copyright">&copy; 2020 Search Engine</p>
      </div>
    </footer>
  </div>
  <!-- Modal -->
  <div class="modal" id="about" aria-hidden="true">
    <div class="modal__dialog">
      <div class="modal__header">
        <h2>About</h2>
        <a href="#" class="modal__close" aria-hidden="true">×</a>
      </div>
     
    </div>
  </div>
  <!-- /Modal -->
</body>

</html>

