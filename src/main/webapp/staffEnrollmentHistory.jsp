<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Enrollment Details</title>
    <link rel="stylesheet" type="text/css" href="enrollmentStyles.css">
    <%@ include file="staffNavigationBar.jsp" %>
</head>
<body onload="loadEnrollmentDetails()">
   <div class="main">
      <div class="table_header">
        <p>Enrollment Details</p>
        <div class="search_container">
        <input type="text" id="searchInput" placeholder="Search...">
        <button onclick="staffSearchEnrollments()">Search</button>
        </div>
      </div>
      <table id="enrollmentTable">
          <thead>
              <tr>
                  <th>Enrollment ID</th>
                  <th>Academic Year</th>
                  <th>Course Code</th>
                  <th>Staff Alloted</th>
              </tr>
          </thead>
          <tbody>
            <tr>
              <td>${enrollment.enrollmentID}</td>
              <td>${enrollment.academicYear}</td>
              <td>${enrollment.courseCode}</td>
              <td>${enrollment.staffAllotted}</td>
           </tr>   
              <!-- Table rows will be populated dynamically from the servlet -->
          </tbody>
      </table>
   </div>
 </div>
<script>
//Function to populate Course Codes dropdown 
function loadEnrollmentDetails() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("enrollmentTable").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "StaffEnrollmentDetailsServlet", true);
    xhr.send();
}

function staffSearchEnrollments() {
    // Get the search input value
    var searchInput = document.getElementById("searchInput").value.trim();

    // Create a new XMLHttpRequest object
    var xhr = new XMLHttpRequest();

    // Configure the request
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Replace the table body content with the response from the servlet
            document.getElementById("enrollmentTable").innerHTML = xhr.responseText;
        }
    };

    // Construct the URL with the search query parameters
    var url = "StaffSearchEnrollmentServlet?enrollmentID=" + encodeURIComponent(searchInput) +
              "&academicYear=" + encodeURIComponent(searchInput) +
              "&courseCode=" + encodeURIComponent(searchInput);

    // Open the request
    xhr.open("GET", url, true);

    // Send the request
    xhr.send();
}


</script>
</body>
</html>
