<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Allotment Details</title>
    <link rel="stylesheet" type="text/css" href="allotmentStyles.css">
    <%@ include file="staffNavigationBar.jsp" %>
</head>
<body onload="loadAllotmentDetails()">
   <div class="main">
      <div class="table_header">
        <p>Allotment Details</p>
        <div class="search_container">
        <input type="text" id="searchInput" placeholder="Search...">
        <button onclick="staffSearchAllotments()">Search</button>
        </div>
      </div>
      <table id="allotmentTable">
          <thead>
              <tr>
                  <th>Allotment ID</th>
                  <th>Academic Year</th>
                  <th>Course Code</th>
                  <th>Staff Alloted</th>
              </tr>
          </thead>
          <tbody>
            <tr>
              <td>${allotment.allotmentID}</td>
              <td>${allotment.academicYear}</td>
              <td>${allotment.courseCode}</td>
              <td>${allotment.staffAllotted}</td>
           </tr>   
              <!-- Table rows will be populated dynamically from the servlet -->
          </tbody>
      </table>
   </div>
<script>
//Function to populate Course Codes dropdown 
function loadAllotmentDetails() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("allotmentTable").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "StaffAllotmentDetailsServlet", true);
    xhr.send();
}
function staffSearchAllotments() {
    // Get the search input value
    var searchInput = document.getElementById("searchInput").value.trim();

    // Create a new XMLHttpRequest object
    var xhr = new XMLHttpRequest();

    // Configure the request
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Replace the table body content with the response from the servlet
            document.getElementById("allotmentTable").innerHTML = xhr.responseText;
        }
    };
    // Construct the URL with the search query parameters
    var url = "StaffSearchAllotmentServlet?allotmentID=" + encodeURIComponent(searchInput) +
              "&academicYear=" + encodeURIComponent(searchInput) +
              "&courseCode=" + encodeURIComponent(searchInput);

    // Open the request
    xhr.open("GET", url, true);

    // Send the request
    xhr.send();
}
</script>
<style>
@charset "ISO-8859-1";@charset "ISO-8859-1";
@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');
/* Center the table */
.main {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column; 
}
table {
    max-width: 800px;
     /* Adjust as per your layout */
}
.table_header {
    position: fixed; 
    top: 0;
    right: 0; 
    display: flex;
    justify-content: space-between;
    align-items: right;
    padding: 20px;
    background-color: rgb(240, 240, 240);
    width: 80%;
    z-index: 1000;
}
.table_header p {
    color: #000000;
}
.table_header input {
    padding: 10px 20px;
    margin: 0 10px;
    outline: none;
    border: 1px solid #0f4eb9;
    border-radius: 6px;
    color: #0f4eb9;
}
.add_new {
    padding: 10px 20px;
    color: #ffffff;
    background-color: #0f4eb9;
    border-radius: 6px;
    cursor: pointer;
}
table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px; 
}
th, td {
    padding: 12px;
    text-align: left;
    border-bottom: 1px solid #ddd;
}
thead th {
    background-color: rgb(240, 240, 240);
    color: #000000;
}
tbody tr:nth-child(even) {
    background-color: #f2f2f2;
}
button {
    outline: none;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    padding: 5px 10px;
    margin-right: 5px;
}
input {
    padding: 10px 20px;
    margin: 0 10px;
    outline: none;
    border: 1px solid #0f4eb9;
    border-radius: 6px;
    color: #0f4eb9;
}
.popup {
    display: none;
    position: fixed;
    z-index: 1;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    overflow: auto;
    background-color: rgba(0,0,0,0.4);
    display: flex;
    justify-content: center;
    align-items: center;
}
.popup-content {
    background-color: #fefefe;
    padding: 20px;
    border: 1px solid #888;
    width: 30%; /* Adjust the width as needed */
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}
.close {
    color: #aaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
}
.close:hover,
.close:focus {
    color: black;
    text-decoration: none;
    cursor: pointer;
}
.add_new {
    padding: 10px 20px;
    color: #ffffff;
    background-color: #0f4eb9;
    border-radius: 6px;
    cursor: pointer;
}
input {
    padding: 10px;
    margin: 5px 0;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}
button {
    background-color: #0f4eb9;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}
button:hover {
    background-color: #023f94;
}
#type {
    padding: 10px;
    margin: 5px 0;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    color: #023f94;
}
.search_container {
    display: flex;
    align-items: center;
    width: 60%; /* Ensure the container stretches */
}

.search_container input[type="text"] {
    flex: 1; /* Let the input grow to fill available space */
    padding: 8px;
    margin-right: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.search_container button {
    padding: 8px 12px;
    border: none;
    background-color: #0f4eb9;
    color: #ffffff;
    border-radius: 4px;
    cursor: pointer;
}
</style>
</body>
</html>

</body>
</html>
