jQuery.noConflict();


jQuery(document).ready(function(jQuery){
	
	jQuery(".Dislike").click(function () {
	  jQuery(".Like").removeClass("LikeActive");
	  jQuery(this).toggleClass("DislikeActive");
	 });
	 
	 jQuery(".Like").click(function () {
	  jQuery(".Dislike").removeClass("DislikeActive");
	  jQuery(this).toggleClass("LikeActive");
	 });




jQuery(".Toggle").click(function () {
	  jQuery(this).toggleClass("Minus");
    jQuery(this).parent().next(".ExpandCollapse").slideToggle("slow")
	 

  });


  jQuery('.TopMenuSelect').bind('change', function () {
        var url = jQuery(this).val(); // get selected value
        if (url) { // require a URL
        	 window.location=document.getElementById(url).href // redirect
        }
        return false;
  }); 	


});

function popitup(url) {
	newwindow=window.open(url,'name','height=600,width=400');
	newwindow.moveTo((screen.width/2)-200,(screen.height/2)-300); 

	if (window.focus) {newwindow.focus()}
	return false;
}