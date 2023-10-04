

function showToasty(icon,bgclass,message)
{
    debugger;
    $("#toast div strong").prepend(icon);
    $("#toast .toast-header").addClass(bgclass);
    $(".toast-body").text(message)
    $("#toast").show();
}