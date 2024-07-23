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
