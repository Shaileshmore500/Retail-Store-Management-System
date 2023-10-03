const aside = document.getElementById("aside_bar");
aside.addEventListener("click", () => {
  
  const aside_bar = document.getElementById("aside");
  aside_bar.classList.toggle("active");
});
//errorToast();
function errorToast(msg) {
  debugger;
  var a = msg;
  if (a != "" && a != null) {
    $("#toast").toast("show");
  } else {
    $("#toast").toast("hide");
  }
}




$(document).ready(function() {

//$('.clild-menu').hide();
debugger;
  $(".toggleChild").click(function() {
 var a=$(this).find(".fa-caret-down") 
 $(a).toggleClass("tarnsform");
   $(".clild-menu_"+$(this)[0].id).slideToggle()
      
  });
});
$("form").submit(()=>{

  $(".loader").show();
});




// $(".toggleChild").click(()=>{

// alert(1)
// // Find the closest parent li element
// var parentLi = $(this).closest("li");

// // Find the sibling div with the class child-menu
// var childMenuDiv = parentLi.next(".child-menu");

// // Toggle the visibility of the child-menu div
// childMenuDiv.toggle();






// //$(".clild-menu").slideToggle();
// });
