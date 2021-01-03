<%-- 
    Document   : search
    Created on : 4 Jun, 2020, 4:51:56 PM
    
--%>
  
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Search Engine - Web</title>
  <meta name="description" value="The search engine that doesn't track you. Learn More.">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <script src="static/jQuery.js"></script>
   <link rel="stylesheet" type="text/css" href="static/colors.css">
  <link rel="stylesheet" type="text/css" href="static/modal.css">
  <link rel="stylesheet" type="text/css" href="static/index.css">
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
   <script src="static/search.js"></script>
   <script src="static/suggest.js"></script>
  
 
  
  
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

    
  <div class="index">
     
     <!--  <a style="margin-left: 80%" href="admin_1.jsp?docFlag=y">Add Fields</a> -->
    <section class="index__header">
      <div class="index__logo"></div>
        
      <ul class="index__nav">
       
        
      
        <li id="relLi"><a id="relKeyWord" href="#">Web Search</a></li>
        
      
        
      </ul>
      

      <div class="index__search">
        <form class="index__form" action="WebSearch" method="POST">
          <div>
              <div class="ui-widget">
                    <label for="tags">
            <input id="tags" name="webKeyword" type="search" placeholder="Search Keyword"
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
           
            </div>
                
               
            <button class="index__button" onclick="javascript:document.getElementById('pBar').style.display='block'"  aria-label="Search" type="submit">
            <div class="index__ico"></div>
            <div id="pBar" style="display:none">
          <img src="images/spinner.gif">
      </div>
          </button>
      
           
       
      </div>
    </section><br>
     <div>  No. of Search Result per Page <select name="noResult" id="noResult">
         
         </select>    </div>
     </form>
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

