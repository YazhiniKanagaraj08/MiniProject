<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Allotment Details</title>
    <%@ include file="navigationBar.jsp" %>
</head>
<body onload="loadAllotmentDetails()">
   <div class="main">
      <div class="table_header">
        <p>Allotment Details</p>
        <div class="search_container">
        <input type="text" id="searchInput" placeholder="Search...">
        <button onclick="searchAllotments()">Search</button>
        </div>
        <div>
           <button class="add_new" id="addNewButton">+ Add New</button>
           <button class="view" id="viewButton">View</button>
        </div>
      </div>
      <table id="allotmentTable">
          <thead>
              <tr>
                  <th>Allotment ID</th>
                  <th>Academic Year</th>
                  <th>Course Code</th>
                  <th>Staff Alloted</th>
                  <th>Actions</th>
              </tr>
          </thead>
          <tbody>
            <tr>
              <td>${allotment.allotmentID}</td>
              <td>${allotment.academicYear}</td>
              <td>${allotment.courseCode}</td>
              <td>${allotment.staffAllotted}</td>
              <td><button onclick="allotStaff('${allotment.allotmentID}', '${allotment.academicYear}', '${allotment.courseCode}')">Allot</button></td>
           </tr>
            
              <!-- Table rows will be populated dynamically from the servlet -->
          </tbody>
      </table>
   </div>
<!-- Popup form -->
   <div id="popupForm" class="popup" style="display: none;">
       <div class="popup-content">
           <span class="close" onclick="closePopup()">&times;</span>
           <h2 id="popupTitle"></h2>
           <form id="allotmentForm">
            <input type="hidden" id="allotmentIDHidden" />
            <input type="text" id="allotmentID" placeholder="Allotment ID"/><br>
            <input id="academicYear" placeholder="Academic Year"/><br>
            <select id="courseCode" onchange="populateCourseCodes()">
            <option value="">Select Course Code</option>
        <!-- This will be populated dynamically based on the selected Academic Year -->
            </select><br>
            <button type="button" id="popupSaveButton" onclick="addAllotment()">Save</button>
           </form>
       </div>     
       
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
  xhr.open("GET", "AllotmentDetailsServlet", true);
  xhr.send();
}

//Add event listener for the "Add New" button
document.getElementById("addNewButton").addEventListener("click", function() {
    openPopup('add');
});

//Add event listener for the "View" button


function openPopup(action, allotmentID, academicYear, courseCode, staffAllotted) {
    var popupTitle = document.getElementById("popupTitle");
    var saveButton = document.getElementById("popupSaveButton");

    if (action === 'add') {
        popupTitle.textContent = "Add New Allotment";
        saveButton.textContent = "Add";
        populateCourseCodes(); // Call the function to populate course codes
        saveButton.setAttribute("onclick", "addAllotment()");
    } 

    document.getElementById("popupForm").style.display = "block";
}

function populateCourseCodes() {
    var courseCodeSelect = document.getElementById("courseCode");
    var selectedValue = courseCodeSelect.value; // Store the selected value

    // Get the academicYear value
    var academicYear = document.getElementById("academicYear").value;

    var xhr = new XMLHttpRequest();
    xhr.open("GET", "CourseCodeServletAllotment?academicYear=" + encodeURIComponent(academicYear), true); // Include academicYear in the URL
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                var courseCodesHTML = xhr.responseText;

                // Clear existing options before adding new ones
                courseCodeSelect.innerHTML = "";

                // Append new options to the dropdown
                courseCodeSelect.insertAdjacentHTML("beforeend", courseCodesHTML);

                // Set the selected value back after population
                courseCodeSelect.value = selectedValue;

                console.log("After population - Selected value: " + courseCodeSelect.value);
            } else {
                console.error("Failed to fetch course codes. Status: " + xhr.status);
            }
        }
    };
    xhr.send();
}

function closePopup() {
    document.getElementById("popupForm").style.display = "none";
}

function addAllotment() {
    var allotmentID = document.getElementById("allotmentID").value;
    var academicYear = document.getElementById("academicYear").value;
    var courseCode = document.getElementById("courseCode").value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Append new allotment row to the table body
            var newRowHtml = xhr.responseText;

            var tbody = document.querySelector("#allotmentTable tbody");
            tbody.insertAdjacentHTML("beforeend", newRowHtml);

            // Clear input fields after adding the new enrollment
            document.getElementById("allotmentForm").reset();

            // Close the popup after adding the enrollment
            closePopup();
            location.reload();
        }
    };

    xhr.open("POST", "AddAllotmentServlet", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send("allotmentID=" + encodeURIComponent(allotmentID) + "&academicYear=" + encodeURIComponent(academicYear) + "&courseCode=" + encodeURIComponent(courseCode));
}

function allotStaff(allotmentID, academicYear, courseCode) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                // Check the response for success or error message
                var responseText = xhr.responseText;
                if (responseText.includes("Staff alloted successfully!")) {
                    // Reload the allotment details after successful assignment
                    location.reload();
                } else {
                    // Display error message if assignment fails
                    alert(responseText);
                }
            } else {
                console.error("Failed to allot staff. Status: " + xhr.status);
            }
        }
    };

    xhr.open("POST", "StaffAllotServlet", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send("allotmentID=" + encodeURIComponent(allotmentID) + "&academicYear=" + encodeURIComponent(academicYear) + "&courseCode=" + encodeURIComponent(courseCode));
}
function deleteAllotment(allotmentID) {
    // Send an AJAX request to delete the allotment from the backend
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
            console.log("Response status:", xhr.status); // Log the response status code
            if (xhr.status == 200) {
                // If deletion is successful, reload the page
                location.reload();
            } else {
                console.error('Failed to delete Allotment.');
            }
        }
    };
    xhr.open("GET", "http://localhost:8083/MiniProjectStaffAllocation/DeleteAllotmentServlet?allotmentID=" + allotmentID, true);
    xhr.send();
}
function searchAllotments() {
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
    var url = "SearchAllotmentServlet?allotmentID=" + encodeURIComponent(searchInput) +
              "&academicYear=" + encodeURIComponent(searchInput) +
              "&courseCode=" + encodeURIComponent(searchInput) +
              "&staffID=" + encodeURIComponent(searchInput);

    // Open the request
    xhr.open("GET", url, true);

    // Send the request
    xhr.send();
}
//Add event listener for the "View" button
document.getElementById("viewButton").addEventListener("click", function() {
    // Create a new XMLHttpRequest object
    var xhr = new XMLHttpRequest();

    // Configure the request
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Replace the table body content with the response from the servlet
            document.getElementById("allotmentTable").innerHTML = xhr.responseText;
        }
    };

    // Open the request
    xhr.open("GET", "ViewAllotmentDetailsServlet", true);

    // Send the request
    xhr.send();
});
function mailStaff() {
    var xhr = new XMLHttpRequest();
    var url = "SendMailServlet"; // Adjust the URL as per your application context

    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.responseText); // Log the response from the servlet
            alert("Email sent successfully!"); // Show an alert to indicate successful email sending
        }
    };

    // You can add parameters if needed
    var params = ""; // Add parameters if required
    xhr.send(params);
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
