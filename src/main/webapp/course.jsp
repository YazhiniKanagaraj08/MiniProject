<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Course Details</title>
    <link rel="stylesheet" type="text/css" href="courseStyles.css">
    <%@ include file="navigationBar.jsp" %>
</head>
<body onload="loadCourseDetails()">
   <div class="main">
      <div class="table_header">
        <p>Course Details</p>
        <div class="search_container">
        <input type="text" id="searchInput" placeholder="Search...">
        <button onclick="searchCourses()">Search</button>
        </div>
        <div>
           <button class="add_new" onclick="openPopup('add')">+ Add New</button>
           <button class="view" id="viewButton">View</button>
        </div>
      </div>
      <table id="courseTable">
          <thead>
              <tr>
                  <th>Course Code</th>
                  <th>Course Title</th>
                  <th>Type</th>
                  <th>Credits</th>
                  <th>Semester</th>
                  <th>Actions</th>
              </tr>
          </thead>
          <tbody>
              <!-- Table rows will be populated dynamically from the servlet -->
          </tbody>
      </table>
   </div>
<!-- Popup form -->
   <div id="popupForm" class="popup" style="display: none;">
       <div class="popup-content">
           <span class="close" onclick="closePopup()">&times;</span>
           <h2 id="popupTitle"></h2>
           <form id="courseForm">
               <input type="hidden" id="courseCodeHidden" />
               <input type="text" id="courseCode" placeholder="Course Code"/><br>
               <input id="courseTitle" placeholder="Course Title"/><br>
               <select id="type" name="type">
                   <option value="" disabled selected hidden>Type</option>
                   <option value="Theory">Theory</option>
                   <option value="Lab">Lab</option>
                   <option value="Embedded">Embedded</option>
               </select><br>
               <input id="credits" placeholder="Credits"/><br>
               <input id="syllabus" placeholder="Semester"/><br>
               <button type="button" id="popupSaveButton" onclick="addCourse()">Save</button>
           </form>
       </div>
   </div>
<script>
function loadCourseDetails() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("courseTable").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "CourseDetailsServlet", true);
    xhr.send();
}

function openPopup(action, courseCode, courseTitle, type, credits, syllabus) {
    var popupTitle = document.getElementById("popupTitle");
    var saveButton = document.getElementById("popupSaveButton");

    if (action === 'add') {
        popupTitle.textContent = "Add New Course";
        saveButton.textContent = "Add";
        saveButton.setAttribute("onclick", "addCourse()");
    } else if (action === 'update') {
        popupTitle.textContent = "Update Course";
        saveButton.textContent = "Save";
        saveButton.setAttribute("onclick", "updateCourse()");
        
        // Populate the update form with existing values
        document.getElementById("courseCode").value = courseCode;
        document.getElementById("courseTitle").value = courseTitle;
        document.getElementById("type").value = type;
        document.getElementById("credits").value = credits;
        document.getElementById("syllabus").value = syllabus;
        document.getElementById("courseCodeHidden").value = courseCode;
    }

    document.getElementById("popupForm").style.display = "block";
}

function closePopup() {
    document.getElementById("popupForm").style.display = "none";
}

function addCourse() {
    var courseCode = document.getElementById("courseCode").value;
    var courseTitle = document.getElementById("courseTitle").value;
    var type = document.getElementById("type").value;
    var credits = document.getElementById("credits").value;
    var syllabus = document.getElementById("syllabus").value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Append new course row to the table body
            var newRowHtml = xhr.responseText;

            var tbody = document.querySelector("#courseTable tbody");
            tbody.insertAdjacentHTML("beforeend", newRowHtml);

            // Clear input fields after adding the new course
            document.getElementById("courseForm").reset();

            // Close the popup after adding the course
            closePopup();
        }
    };
    xhr.open("GET", "AddCourseServlet?courseCode=" + courseCode + "&courseTitle=" + courseTitle + "&type=" + type + "&credits=" + credits + "&syllabus=" + syllabus, true);
    xhr.send();
}

function updateCourse() {
    var courseCode = document.getElementById("courseCodeHidden").value;
    var courseTitle = document.getElementById("courseTitle").value;
    var type = document.getElementById("type").value;
    var credits = document.getElementById("credits").value;
    var syllabus = document.getElementById("syllabus").value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                // Close the popup after updating the course
                closePopup();
                // Reload the table with updated data
                loadCourseDetails();
            } else {
                console.error('Failed to update course.');
            }
        }
    };
    xhr.open("POST", "UpdateCourseServlet", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    var params = "courseCode=" + encodeURIComponent(courseCode) +
                 "&courseTitle=" + encodeURIComponent(courseTitle) +
                 "&type=" + encodeURIComponent(type) +
                 "&credits=" + encodeURIComponent(credits) +
                 "&syllabus=" + encodeURIComponent(syllabus);
    xhr.send(params);
}

function deleteCourse(courseCode) {
    // Send an AJAX request to delete the course from the backend
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
            console.log("Response status:", xhr.status); // Log the response status code
            if (xhr.status == 200) {
                // If deletion is successful, reload the page
                location.reload();
            } else {
                console.error('Failed to delete course.');
            }
        }
    };
    xhr.open("GET", "http://localhost:8083/MiniProjectStaffAllocation/DeleteCourseServlet?courseCode=" + courseCode, true);
    xhr.send();
}

function searchCourses() {
    // Get the search input value
    var searchInput = document.getElementById("searchInput").value.trim();

    // Create a new XMLHttpRequest object
    var xhr = new XMLHttpRequest();

    // Configure the request
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Replace the table body content with the response from the servlet
            document.getElementById("courseTable").innerHTML = xhr.responseText;
        }
    };

    // Construct the URL with the search query parameters
    var url = "SearchCourseServlet?courseCode=" + encodeURIComponent(searchInput) +
              "&courseTitle=" + encodeURIComponent(searchInput) +
              "&type=" + encodeURIComponent(searchInput) +
              "&credits=" + encodeURIComponent(searchInput);

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
            document.getElementById("courseTable").innerHTML = xhr.responseText;
        }
    };

    // Open the request
    xhr.open("GET", "ViewStaffAvailableDetailsServlet", true);

    // Send the request
    xhr.send();
});
</script>
</body>
</html>