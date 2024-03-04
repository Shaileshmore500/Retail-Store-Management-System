

document.getElementById("searchbtn").addEventListener("click", function() {
    // Your vanilla JavaScript code here
    $(".customloader").show();

debugger;
let id=$("#html_id").val();
var category=$("#html_cat").val();
var brand=$("#html_brand").val();

var data={


id:id,category:category,brand:brand
}

$.ajax({
    url: '/getProductByParameter', // Change this to your actual API endpoint
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(data),
    success: function(response) {
        
        if(response==null || response.length<=0)
{
  //$(".page__body .container section")[1].append($("#empty-state"))
  $("#EmptyStateModel").append($("#empty-state"))
  $("#empty-state").show();
  $(".customloader").hide();
  return;
}else
$(".customloader").hide();
$("#empty-state").hide();
        JSONToHTMLTable(response, "tblEmployee");
    },
    error: function(xhr, status, error) {
        $("#EmptyStateModel").append($("#empty-state"))
        $("#empty-state").show();
        $(".customloader").hide();
    }
});



});
function JSONToHTMLTable(jsonData, elementToBind) {
          
    //This Code gets all columns for header   and stored in array col
    var col = [];
    for (var i = 0; i < jsonData.length; i++) {
        for (var key in jsonData[i]) {
            if (col.indexOf(key) === -1) {
                col.push(key);
            }
        }
    }

    //This Code creates HTML table
    var table = document.createElement("table");
    table.setAttribute("id", "jsonTable");
    var classList = ["table", "table-striped", "table-bordered","table-sm"];
    classList.forEach((classname)=>{
        table.classList.add(classname);
    })

    // id="jsonTable" class="table table-striped table-bordered table-sm" 
    // style="width:99%;margin: 0 auto;"


    //This Code getsrows for header creader above.
    var tr = table.insertRow(-1);

    for (var i = 0; i < col.length; i++) {
        var th = document.createElement("th");
        th.innerHTML = col[i];
        tr.appendChild(th);
    }

    //This Code adds data to table as rows
    for (var i = 0; i < jsonData.length; i++) {

        tr = table.insertRow(-1);

        for (var j = 0; j < col.length; j++) {
            var tabCell = tr.insertCell(-1);
            tabCell.innerHTML = jsonData[i][col[j]];
        }
    }

    //This Code gets the all columns for header
    var divContainer = document.getElementById(elementToBind);
    divContainer.innerHTML = "";
    divContainer.appendChild(table);
}
   


   


function jsonToDataTable2(jsonData) {
if(jsonData==null || jsonData.length<=0)
{
//$(".page__body .container section")[1].append($("#empty-state"))
$("#EmptyStateModel").append($("#empty-state"))
$("#empty-state").show();
return;
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

    

