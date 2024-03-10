
window.onload = function () {

  //----- Search bar styling -----//
  let searchIcon = document.createElement('i');
  searchIcon.className = "icon-search";
  if (document.getElementById('jsonTable_filter')) {
    let filterLabel = document.getElementById('jsonTable_filter').children[0]
    filterLabel.prepend(searchIcon);
  }
}

function jsonToDataTable(jsonData) {

  if (jsonData == null || jsonData.length <= 0) {
    $("#EmptyStateModel").append($("#empty-state"))
    $("#empty-state").show();
    return;
  }

  var data = [];
  for (var i = 0; i < jsonData.length; i++) {
    var obj = jsonData[i]
    data.push({
      "ID": obj["partyChallan_pid"],
      "Challan No": obj["challan_no"],
      "Challan Date": obj["challan_date"] != null ? new Date(obj["challan_date"]).getDate() + "-" + new Date(obj["challan_date"]).getMonth() + 1 + "-" + new Date(obj["challan_date"]).getFullYear() : "",
      "Amount": obj["amount"],
      "Quantity": obj["quantity"],
      "Purchase Date":obj["purchase_date"],
      "Supplier": obj["supplier_fid"] != null ? obj["supplier_fid"].name : "",
        





    })

  }
  jsonData = data;



  let newKey = 'Action';
  let newValue = '<a  class="btn btn-primary m-1 edit"><i class="btn-edit "></i></a>';

  // Loop through the array and add the new key-value pair to each object
  for (let i = 0; i < jsonData.length; i++) {
    jsonData[i] = { [newKey]: newValue, ...jsonData[i] };
  }
  // Extract column headers from the first object in the array
  var columns = Object.keys(jsonData[0]);

  // Initialize DataTable
  var table = $('#jsonTable').DataTable({
    data: jsonData,
    columns: columns.map(function (column) {
      return { data: column };
    }),
    paging: true, // Enable pagination
    searching: true, // Enable search box
    order: [] // Disable initial sorting
  });

  // Add column headers to the table
  $('#jsonTable thead').append('<tr>' +
    columns.map(function (column) {
      return '<th>' + column + '</th>';
    }).join('') +
    '</tr>');
}

function displayStatus(status, mode) {

  if (status != null && status != '') {

    if (status == "success") {
      if (mode === "add")
        var msg = "Data added successfully..."
      else if (mode === "edit")
        msg = "Data edited successfully..."
      else if (mode == "delete")
        msg = "Data deleted successfully..."
      else
        msg = "Success..."
      //  showToasty("<i class='fa-solid fa-square-check fa-shake fa-xl text-white'>&nbsp;&nbsp</i>", "bg-success", "Success...", "Success")       
      showToasty(
        "<i class='fa-solid fa-square-check fa-shake fa-xl text-white'>&nbsp;&nbsp</i>",
        "bg-success",
        msg,
        "Success"
      );

    } else if (status == "error") {
      //  showToasty("<i class='fa-solid fa-triangle-exclamation fa-shake fa-xl text-white'>&nbsp;&nbsp;</i>", "bg-danger", "Something Went Wrong! Pleae Try Again Later...", "Error")

      showToasty(
        "<i class='fa-solid fa-triangle-exclamation fa-shake fa-xl text-white'>&nbsp;&nbsp;</i>",
        "bg-danger",
        "Something Went Wrong! Pleae Try Again Later...",
        "Error"
      );
    }
  }
  $("#customloader").hide();
}

$(document).ready(() => {


  $(".edit,.view,.add,.delete").on('click', function () {
    //    $("#customloader").show();
    debugger
    let currentelement = this;
    $("#save").show();

    if (currentelement.classList.contains("add")) {

      window.location.href = "/product?pid="


    }
    else if (currentelement.classList.contains("delete")) {
      // $("#customloader").show();
      // $.ajax({
      //   type: 'GET',
      //   url: `/deleteItem?form=${new URLSearchParams(window.location.search).get("form")}&id=${$(currentelement).closest('tr').find('td:nth-child(2)').text()}`        ,
      //   success : function(){
      //     displayStatus("success", "delete")

      //    setTimeout(()=>window.location.reload(),3000)

      //   },
      //   error : function(){
      //     displayStatus("error", "delete")
      //   }

      // });


    }
    else if (currentelement.classList.contains("edit")) {
      debugger;
      var pid = $(currentelement).closest('tr').find('td:nth-child(2)').text();
      window.location.href = `/product?pid=${pid}`;



    }



  })


  $(".btn-form-cancle").click(() => {
    $("input , textarea").val("");
    $("#myModal").css("display", "none")

  });



})