

function showToasty(icon,bgclass,message,stat)
{
    debugger;
    $("#toast div strong").prepend(icon);
    $("#toast div strong").text(stat);
    $("#toast .toast-header").addClass(bgclass);
    $(".toast-body").text(message)
    $("#toast").show();
}