$(document).ready(() => {

  
   // document.getElementById("btn").addEventListener("click", () => {
     $("#btn_generatebarcode") .click(()=>{
        debugger;

// $.ajax({
// type:"POST",
// content:"application/json",
// url:"/printBarcode",
// data:JSON.stringify({ challanID:$("#html_chal``````````````````````````````````````lanno").val()}),
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
debugger;
if(response!=null && response.length>0)
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
     $("#barcode" + counter).wrap("<tr><td><div style='' class='innerdiv'></div></td></tr>")

    JsBarcode("#barcode" + counter, product.product_pid)
    
    //$("#barcode" + counter).find('text').text(product.style);
    $("#barcode" + counter).find('text').text(product.product_pid);
    counter++;
  });


  $("#btn_print").show();
  hideToasty();

}
else{
  showToasty("<i class='fa-solid fa-triangle-exclamation fa-shake fa-xl text-white'>&nbsp;&nbsp;</i>", "bg-danger", "Something Went Wrong! Please Try Again Sometime...", "Error")
}



// Append the button to the container



  },
  error: (response) => {
      console.log(response);
      showToasty("<i class='fa-solid fa-triangle-exclamation fa-shake fa-xl text-white'>&nbsp;&nbsp;</i>", "bg-danger", "Something Went Wrong! Please Try Again Sometime...", "Error")
  }
});




      // var a = ['123df', '456fdf', '789dfdsf', '321fdfdfd', "google"];


      // var barcodeContainer = document.getElementById('barcodetable');
      // barcodeContainer.innerHTML='';
      // //barcodeContainer.innerHTML="";
      // for (var i = 0; i < a.length; i++) {

      //    var svgElement = document.createElementNS("http://www.w3.org/2000/svg", "svg");
      //   svgElement.classList.add("barcode");
      //   svgElement.setAttribute("id", "barcode" + i);
      //   svgElement.setAttribute("jsbarcode-format", "CODE128");
      //   //svgElement.setAttribute("jsbarcode-value", a[i]);
      //   svgElement.setAttribute("jsbarcode-textmargin", "1");
      //   svgElement.setAttribute("jsbarcode-fontoptions", "bold");

      //   barcodeContainer.appendChild(svgElement);
      //    $("#barcode" + i).wrap("<tr><td><div style=''></div></td></tr>")

      //   JsBarcode("#barcode" + i, a[i])
      //   //$("#barcode" + i).find('text').text("121321564564456sdfffffffffffffffffffffffffffffff12343553");
      // }


    });


      // Add a click event listener to the Print button
    

      // Function to trigger printing of an element
      $("#btn_print").click(()=> {
        var printWindow = window.open('', '_blank');
        printWindow.document.open();
        printWindow.document.write(`<html><head><title>Print</title>
        <style>
        body div:nth-child(1){
        display: flex;
    flex-wrap: wrap;
  }
    body div div {
    position: relative;
    top: 2px;
    bottom: 2px;
    /* padding: 20px 60px 24px 60px; */
    border: 1px solid black;
    min-width: 225px;
    margin: 3px;
}
        </style>
        
        
        </head><body><div>`);
        printWindow.document.write($("#barcodetable").html());
        printWindow.document.write('</div></body></html>');
        printWindow.document.close();
        printWindow.print();
      });
  

  });