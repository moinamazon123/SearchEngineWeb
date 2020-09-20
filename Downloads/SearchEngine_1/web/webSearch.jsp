<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
 <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
   <script src="static/search.js"></script>
   <script src="static/suggest.js"></script>
     <link rel="stylesheet" href="static/style.css">
 
     <script>
         
        sessionStorage.removeItem("SelectedItem"); 
         </script>

<div class="container">
 
    <form id="searchFrm" action="WebSearch" method="POST">
	<div class="row">
	    <div class="col-12"><h2>Web Search Here</h2></div>
	    <div class="col-12">
    	    <div id="custom-search-input">
                <div class="input-group">
                    <input type="text" name="webKeyword" class="search-query form-control" placeholder="Search" />
                    <span class="input-group-btn">
                        <button onclick="javascript:document.getElementById('pBar').style.display='block'"  type="submit" >
                            <span class="fa fa-search"></span>
                        </button>
                    </span>
                </div>
                <br>
                <div>  No. of Search Result per Page <select name="noResult" id="noResult"></select>
            </div>
        </div>
                 <div id="pBar" style="display:none">
          <img  src="images/spinner.gif">
      </div>
            
	</div>
    </form>
    
    
</div>
