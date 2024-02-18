var password = document.getElementById("password");
var confirm_password = document.getElementById("confirmPassword")
var oldpassword=document.getElementById("oldpassword")

//document.getElementById('signupLogo').src = "https://s3-us-west-2.amazonaws.com/shipsy-public-assets/shipsy/SHIPSY_LOGO_BIRD_BLUE.png";
enableSubmitButton();

function validatePassword() {

  if(password.value==oldpassword.value)
{
  oldpassword.setCustomValidity("New Password Should Be Different Than Old Password");
  return false;
}else  if(password.value != confirm_password.value) {
  confirm_password.setCustomValidity("Passwords Don't Match");
  return false;
}

else {
  confirm_password.setCustomValidity('');
  oldpassword.setCustomValidity("");
  
  return true;
}
}

password.onchange = validatePassword;
confirm_password.onkeyup = validatePassword;

function enableSubmitButton() {
document.getElementById('submitButton').disabled = false;
document.getElementById('loader').style.display = 'none';
}

function disableSubmitButton() {
document.getElementById('submitButton').disabled = true;
document.getElementById('loader').style.display = 'unset';
}

function validateSignupForm() {
  debugger;
var form = document.getElementById('signupForm');


for(var i=0; i < form.elements.length; i++){
    if(form.elements[i].value === '' && form.elements[i].hasAttribute('required')){
      console.log('There are some required fields!');
      return false;
    }
  }

if (!validatePassword()) {
  return false;
}

onSignup();
}

function onSignup() {
  $(".customloader").show();
 
  disableSubmitButton();

//enableSubmitButton();
// Create an object to send
var dataToSend = {
old: oldpassword.value,
newpass: password.value,
conf : confirm_password.value
};

// Stringify the object
//var jsonData = JSON.stringify(dataToSend);

// Make a POST request to the server
$.ajax({
url: `/setnewpassword?old=${oldpassword.value}&newpass=${password.value}`,
type: 'POST',
contentType: 'application/json',

success: function(response) {
  // Request was successful, handle the response data here
  showToasty(
    "<i class='fa-solid fa-square-check fa-shake fa-xl text-white'>&nbsp;&nbsp</i>",
    "bg-success",
    "Password Change Successfully",
    "Success"
  );
  $(".customloader").hide();
setTimeout(function(){
  window.location.href="/";
},1000)
  //enableSubmitButton();

},
error: function(xhr, status, error) {
  // Request failed, handle the error here
  $(".customloader").hide();
  if(xhr.responseText=="Old Password not Matchhed")
  {
    oldpassword.setCustomValidity(xhr.responseText);
  }
  showToasty(
    "<i class='fa-solid fa-triangle-exclamation fa-shake fa-xl text-white'>&nbsp;&nbsp;</i>",
    "bg-danger",
    xhr.responseText,
    "Error"
  );
  enableSubmitButton()
}
});



  

 
}