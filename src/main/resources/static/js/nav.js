const aside = document.getElementById('aside_bar');

aside.addEventListener('click',()=>{
    const aside_bar = document.getElementById('aside');
    aside_bar.classList.toggle('active');
})
errorToast();
function errorToast()
{
    debugger;
var a = '[[${errormessage}]]';
              if (a != "" && a != null) {
                $("#toast").toast("show");
              } else {
                $("#toast").toast("hide");
              }
            }