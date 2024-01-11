window.onload = function(){
    debugger
    //----- Insert 'ButtonGroup' between 'Record limit selector' and 'Search Bar' -----//
    let buttonList = document.getElementById('tableButtonGroup');
    let table_wrapper = document.getElementById('jsonTable_wrapper').children;
    let table_entryLimit = table_wrapper[0].children[0];
    table_entryLimit.after(buttonList);

    //----- Adding View and Edit buttons to DataTable -----//
    let tableHead = document.getElementById('jsonTable').children[0];
    let tableHeadRow = tableHead.children[1];
    let actionColumn = document.createElement('th');
    actionColumn.innerText = "Action";
    tableHeadRow.prepend(actionColumn);
    //--Selecting rows from table body
    let tableBody = document.getElementById('jsonTable').children[1];
    let tableBodyRows = tableBody.children;

    //----- Adding Checkbox column to DataTable
    let checkboxColumn = document.createElement('th');
    let headerCheckbox = document.createElement('input');
    headerCheckbox.type = "checkbox";
    console.log(headerCheckbox);
    //checkboxColumn.append(headerCheckbox);
   // tableHeadRow.prepend(checkboxColumn);

    for(let loop = 0; loop <= tableBodyRows.length; loop++){
      //-----
      let multiButtonColumn = document.createElement('td');
      multiButtonColumn.innerHTML = '<a href="" class="btn btn-primary m-1"><i class="btn-view"></i></a><a href="" class="btn btn-primary m-1"><i class="btn-edit"></i></a><a href="" class="btn btn-danger  m-1"><i class="btn-delete"></i> </a>';

      let checkboxBodyColumn = document.createElement('td');
      let bodyCheckbox = document.createElement('input');
      bodyCheckbox.type = "checkbox";
      checkboxBodyColumn.append(bodyCheckbox);

      tableBodyRows[loop].prepend(multiButtonColumn);
    // tableBodyRows[loop].prepend(checkboxBodyColumn);
    }

    //----- Search bar styling -----//
    let searchIcon = document.createElement('i');
    searchIcon.className = "icon-search";
    let filterLabel = document.getElementById('jsonTable_filter').children[0]
    filterLabel.prepend(searchIcon);
  }

  function jsonToDataTable(jsonData) {
    // Extract column headers from the first object in the array
    var columns = Object.keys(jsonData[0]);

    // Initialize DataTable
    var table = $('#jsonTable').DataTable({
      data: jsonData,
      columns: columns.map(function(column) {
        return { data: column };
      }),
      paging: true, // Enable pagination
      searching: true, // Enable search box
      order: [] // Disable initial sorting
    });

    // Add column headers to the table
    $('#jsonTable thead').append('<tr>' +
      columns.map(function(column) {
        return '<th>' + column + '</th>';
      }).join('') +
      '</tr>');
  }