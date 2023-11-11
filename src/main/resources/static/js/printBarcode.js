$(document).ready(() => {

   // document.getElementById("btn").addEventListener("click", () => {
     $("#btn_generatebarcode") .click(()=>{
        debugger;

// $.ajax({
// type:"POST",
// content:"application/json",
// url:"/printBarcode",
// data:JSON.stringify({ challanID:$("#html_challanno").val()}),
// success:(response)=>{
// console.log(response)
// },
// error:(response)=>{
// console.log(response);
// }

// });
$.ajax({
  type: "POST",
  contentType: "application/json",
  url: "/printBarcode",
  data: JSON.stringify({ challanID: $("#html_challanno").val() }),
  success: (response) => {
      console.log(response);

if(response!=null)
{
  
  var productList=response;


  //var a = ['123df', '456fdf', '789dfdsf', '321fdfdfd', "google"];


  var barcodeContainer = document.getElementById('barcodetable');
  barcodeContainer.innerHTML='';
  //barcodeContainer.innerHTML="";
  //for (var i = 0; i < a.length; i++) {
    var counter=0;
    productList.forEach(product => {       

     var svgElement = document.createElementNS("http://www.w3.org/2000/svg", "svg");
    svgElement.classList.add("barcode");
    svgElement.setAttribute("id", "barcode" + counter);
    svgElement.setAttribute("jsbarcode-format", "CODE128");
    //svgElement.setAttribute("jsbarcode-value", a[i]);
    svgElement.setAttribute("jsbarcode-textmargin", "1");
    svgElement.setAttribute("jsbarcode-fontoptions", "bold");

    barcodeContainer.appendChild(svgElement);
     $("#barcode" + i).wrap("<tr><td><div style=''></div></td></tr>")

    JsBarcode("#barcode" + counter, product.product_pid
    )
    //$("#barcode" + i).find('text').text("121321564564456sdfffffffffffffffffffffffffffffff12343553");
    counter++;
  });




}




  },
  error: (response) => {
      console.log(response);
  }
});




      var a = ['123df', '456fdf', '789dfdsf', '321fdfdfd', "google"];


      var barcodeContainer = document.getElementById('barcodetable');
      barcodeContainer.innerHTML='';
      //barcodeContainer.innerHTML="";
      for (var i = 0; i < a.length; i++) {

         var svgElement = document.createElementNS("http://www.w3.org/2000/svg", "svg");
        svgElement.classList.add("barcode");
        svgElement.setAttribute("id", "barcode" + i);
        svgElement.setAttribute("jsbarcode-format", "CODE128");
        //svgElement.setAttribute("jsbarcode-value", a[i]);
        svgElement.setAttribute("jsbarcode-textmargin", "1");
        svgElement.setAttribute("jsbarcode-fontoptions", "bold");

        barcodeContainer.appendChild(svgElement);
         $("#barcode" + i).wrap("<tr><td><div style=''></div></td></tr>")

        JsBarcode("#barcode" + i, a[i])
        //$("#barcode" + i).find('text').text("121321564564456sdfffffffffffffffffffffffffffffff12343553");
      }


    });
  });