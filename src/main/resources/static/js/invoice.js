$(document).ready(()=>{


$("#btn_addProduct").click(()=>{
    debugger;
    
    if(validPanel("pnl_invoiceDetails"))    
    {
        var data ={
            id:$("#html_productID").val(),
            qty:$("#html_quantity").val()
        };
        $.ajax({
            type:"POST",
            contentType:"application/json",
            url:"/searchProduct",
            data:JSON.stringify(data),
            success:function(res){
console.log(res);
showToasty("<i class='fa-solid fa-square-check fa-shake fa-xl text-white'>&nbsp;&nbsp</i>", "bg-success", "Product added...", "Success")
           

            },
            error:(res)=>{
                showToasty("<i class='fa-solid fa-triangle-exclamation fa-shake fa-xl text-white'>&nbsp;&nbsp;</i>", "bg-danger", "Error...", "Error")
           
console.log(res);
            }



        });

    }
})

})