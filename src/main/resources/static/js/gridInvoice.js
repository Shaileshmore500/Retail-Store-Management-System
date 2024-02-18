window.onload = function () {

  //----- Search bar styling -----//
  let searchIcon = document.createElement('i');
  searchIcon.className = "icon-search";
  let filterLabel = document.getElementById('jsonTable_filter').children[0]
  filterLabel.prepend(searchIcon);
}

function jsonToDataTable(jsonData) {
  // Extract column headers from the first object in the array
  debugger;
  //jsonData.unshift({"Action":'<a  class="btn btn-primary m-1 view"><i class="btn-view"></i></a><a  class="btn btn-primary m-1 edit"><i class="btn-edit "></i></a><a  class="btn btn-danger  m-1 delete"><i class="btn-delete"></i> </a>})

  if (jsonData == null || jsonData.length <= 0) {
    //$(".page__body .container section")[1].append($("#empty-state"))
    $("#EmptyStateModel").append($("#empty-state"))
    $("#empty-state").show();
    return;
  }
  let newValue = `<a  class="btn btn-primary m-1 view"><i class="btn-view"></i></a>`;
  var param = new URLSearchParams(window.location.search)
  if (param.get("mode") == "Cancel") {
    newValue += `<a  class=" trigger-btn btn btn-danger  m-1 delete" ><i class="btn-delete"></i> </a>`;

  }
  if (param.get("mode") == "Return") {
    newValue += `<a  class="btn btn-primary m-1 edit"><i class="btn-edit "></i></a>`;
  }



  let newKey = 'Action';



  // Loop through the array and add the new key-value pair to each object
  for (let i = 0; i < jsonData.length; i++) {
    jsonData[i] = { [newKey]: newValue, ...jsonData[i] };
  }
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

    if (status == "sucess") {
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
}

$(document).ready(() => {

  $(".edit,.view,.add,.delete").on('click', function () {

    debugger
    let currentelement = this;
    

    if(!currentelement.classList.contains("delete"))
    {
      window.location=`/invoice?mode=${currentelement.classList.contains("edit")?'edit':'view'}&id=${$(currentelement).closest('tr').find('td:nth-child(2)').text()}`;

    }
    else if (currentelement.classList.contains("delete")) {

      $("#myModal2").show();
      let a = $(currentelement).closest('tr').find('td:nth-child(2)').text();
      $("#deleteInvoice").click(function () {
        $.ajax({
          type: 'GET',
          url: `/deleteItem?form=${new URLSearchParams(window.location.search).get("form")}&id=${a}`,
          success: function (data) {
            displayStatus(data, "delete")
            window.location.reload();
          },
          error: function () {
            displayStatus("error", "delete")
          }

        });

      })
      $("#cancelModal ,.close").click(function () {
        $("#myModal2").hide();


      });


      //      $.ajax({
      //        type: 'GET',
      //        url: `/deleteItem?form=${new URLSearchParams(window.location.search).get("form")}&id=${$(currentelement).closest('tr').find('td:nth-child(2)').text()}`        ,
      //        success : function(){
      //          displayStatus("success", "delete")
      //          window.location.reload();
      //        },
      //        error : function(){
      //          displayStatus("error", "delete")
      //        }
      //
      //      });

    }


  })


  $(".btn-form-cancle").click(() => {
    $("input , textarea").val("");
    $("#myModal").css("display", "none")

  });



})