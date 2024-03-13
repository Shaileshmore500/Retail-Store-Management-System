// window.onload = function () {
//   debugger
window.onload = function () {
  addButtonGroup();
  searchBarStyling();
}
//   //----- Insert 'ButtonGroup' between 'Record limit selector' and 'Search Bar' -----//
function addButtonGroup(){
  let buttonList = document.getElementById('tableButtonGroup');
  if(document.getElementById('jsonTable_wrapper')){
  let table_wrapper = document.getElementById('jsonTable_wrapper').children;
  let table_entryLimit = table_wrapper[0].children[0];
  table_entryLimit.after(buttonList);
  }
}

function searchBarStyling(){
  document.querySelector('#jsonTable_filter>label').childNodes[0].nodeValue = "";
  document.querySelector('#jsonTable_filter input').placeholder = 'Search';
}
// window.onload = function () {
//   debugger
//   //----- Insert 'ButtonGroup' between 'Record limit selector' and 'Search Bar' -----//
//   let buttonList = document.getElementById('tableButtonGroup');
//   if(document.getElementById('jsonTable_wrapper')){
//   let table_wrapper = document.getElementById('jsonTable_wrapper').children;
//   let table_entryLimit = table_wrapper[0].children[0];
//   table_entryLimit.after(buttonList);
//   }

//   //----- Adding View and Edit buttons to DataTable -----//
//   if(document.getElementById('jsonTable'))
//   {
//   let tableHead = document.getElementById('jsonTable').children[0];
//   let tableHeadRow = tableHead.children[1];
//   let actionColumn = document.createElement('th');
//   actionColumn.innerText = "Action";
//   if(tableHeadRow)
//   tableHeadRow.prepend(actionColumn);
//   }
//   //--Selecting rows from table body
//   if(document.getElementById('jsonTable')){
//   let tableBody = document.getElementById('jsonTable').children[1];
//   var tableBodyRows = tableBody.children;
//   }
// ;

//   for (let loop = 0; loop < tableBodyRows.length; loop++) {
//     //-----
//     let multiButtonColumn = document.createElement('td');
//     multiButtonColumn.innerHTML = '<a  class="btn btn-primary m-1 view"><i class="btn-view"></i></a><a  class="btn btn-primary m-1 edit"><i class="btn-edit "></i></a><a  class="btn btn-danger  m-1 delete"><i class="btn-delete"></i> </a>';

//     let checkboxBodyColumn = document.createElement('td');
//     let bodyCheckbox = document.createElement('input');
//     bodyCheckbox.type = "checkbox";
//     checkboxBodyColumn.append(bodyCheckbox);

//     tableBodyRows[loop].prepend(multiButtonColumn);
//     // tableBodyRows[loop].prepend(checkboxBodyColumn);
//   }

//   //----- Search bar styling -----//
//   let searchIcon = document.createElement('i');
//   searchIcon.className = "icon-search";
//   if(document.getElementById('jsonTable_filter'))
//   {
//   let filterLabel = document.getElementById('jsonTable_filter').children[0]
//   filterLabel.prepend(searchIcon);
//   }
// }

function jsonToDataTable(jsonData) {
  if (jsonData == null || jsonData.length <= 0) {
    //$(".page__body .container section")[1].append($("#empty-state"))
    $("#EmptyStateModel").append($("#empty-state"))
    $("#empty-state").show();
    return;
  }
  
  var data = [];
const form=new URLSearchParams(window.location.search).get('form');

if(form=="user")
{
  for (var i = 0; i < jsonData.length; i++) {
    var obj = jsonData[i]
    
    data.push({
"ID":obj["id"],
"Name":obj["name"],
"Email":obj["email"],
"Role":obj["role"],
"Mobile":obj["mobile_no"],
"Address":obj["address"]
    })
    
  }
  jsonData=data;
}








  let newKey = 'Action';
  let newValue = '<a  class="btn btn-primary m-1 view"><i class="btn-view"></i></a><a  class="btn btn-primary m-1 edit"><i class="btn-edit "></i></a><a  class="btn btn-danger  m-1 delete"><i class="btn-delete"></i> </a>';

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
0
     if (status == "success" || status == "Success") {
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

    } else if (status == "Error" || status == "error") {
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

  $('form').submit(function (event) {        
    event.preventDefault();

    var form=new URLSearchParams(window.location.search).get("form")

if(form="customer")
{
  debugger
  var mail_regex= /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  var email = document.getElementsByName("email")[0];
  var mobile_no=document.getElementsByName("mobile_no")[0];
  // if(!mail_regex.test(email.value))
  // {
  //   email.setCustomValidity("Invalid Email...");
  //   return false;
  // }
  if(mobile_no.value.length!=10)
  {
    mobile_no.setCustomValidity("Mobile number must contain 10 digit...")
    return false;
  }



  var confirm_password = document.getElementsByName("code")[0];
  confirm_password.setCustomValidity("Passwords Don't Match");
  return false;
}



    $("#customloader").show();   
    // Continue with the form submission
    $(this).unbind('submit').submit(); // Unbind the submit event and trigger the form submission
  });
  $(".edit,.view,.add,.delete").on('click', function () {
    //    $("#customloader").show();
    debugger
    let currentelement = this;
    $("#save").show();

    if (!currentelement.classList.contains("add") && !currentelement.classList.contains("delete")) {
      let urlParams = new URLSearchParams(window.location.search);


      $.ajax({
        type: 'GET',
        url: `/getFormData?FormName=${new URLSearchParams(window.location.search).get("form")}&Pid=${$(currentelement).closest('tr').find('td:nth-child(2)').text()}`,
        success: function (data) {
          console.log(data);
          debugger;
          if (data != null) {
            for (let key in data) {
              if (data.hasOwnProperty(key)) {

                if ($(`[name=${key}]`).attr('type') == "Date") {
                  if (data[key] != null && data[key] != "") {
                    var arr_date = data[key].split('-');
                    $(`[name=${key}]`).val(`${arr_date[0]}-${arr_date[1]}-${arr_date[2].substring(0, 2)}`);

                  }




                } else {
                  $(`[name=${key}]`).val(data[key]);
                }
              }
            }
            if (currentelement.classList.contains("view"))
              $("#save").hide()
            $("#myModal").css("display", "block");



          }




        }, error: function (err) {
          alert(2)
          console.log(err)
        }

      });



    }
    else if (currentelement.classList.contains("delete")) {
      $("#customloader").show();
      $.ajax({
        type: 'GET',
        url: `/deleteItem?form=${new URLSearchParams(window.location.search).get("form")}&id=${$(currentelement).closest('tr').find('td:nth-child(2)').text()}`,
        success: function (data) {
          displayStatus(data, "delete")


          setTimeout(() => window.location.reload(), 1000)

        },
        error: function () {
          displayStatus("error", "delete")
        }

      });

    }
    else {
      $("#myModal").css("display", "block");
    }


  })
  $("#closeModalBtn").click(() => {

    $("#myModal").css("display", "none");
  })
  $(window).click(function (e) {
    //debugger
    // if(e.target==$("#myModal"))
    // $("#myModal").css("display","none");

  })
  $(".btn-form-cancle").click(() => {
    $("input , textarea").val("");
    $("#myModal").css("display", "none")

  });

  // $("#save12").click(() => {
  //   debugger;
  //   var inputval = $("form").find('[name]');
  //   // var details    ={};
  //   var paramurl = "";
  //   inputval.each(function () {
  //     var elem = $(this);
  //     // details[elem.attr("name")] = elem.val();
  //     paramurl += `${elem.attr("name")}=${elem.val()}&`
  //   });






  //   $.ajax({
  //     type: "POST",
  //     url: `/savebrand`,
  //     // ?${paramurl}`,
  //     data: {
  //       brand_pid: '',
  //       name: 'yourNameValue',
  //       code: 'yourCodeValue'
  //       // Add other parameters as needed
  //     },
  //     success: function (data) {
  //       console.log(data);
  //       alert(1)

  //     }, error: function (err) {
  //       alert(2)
  //       console.log(err)
  //     }


  //   });
  // })


  // $("form").submit((e) => {
  //   debugger;
  //   e.preventDefault();
  //   $.ajax({
  //     type: 'GET',
  //     url: "/getFormData?FormName=category&Pid=123",
  //     success: function (data) {
  //       console.log(data);
  //       alert(1)

  //     }, error: function (err) {
  //       alert(2)
  //       console.log(err)
  //     }

  //   });


  // });

})