//Add event listener for the "Add New" button
document.getElementById("addNewButton").addEventListener("click", function() {
    openPopup('add');
});

//Function to populate Course Codes dropdown 
function loadEnrollmentDetails() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("enrollmentTable").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "EnrollmentDetailsServlet", true);
    xhr.send();
}

function openPopup(action, enrollmentID, academicYear, courseCode, staffAllotted) {
    var popupTitle = document.getElementById("popupTitle");
    var saveButton = document.getElementById("popupSaveButton");

    if (action === 'add') {
        popupTitle.textContent = "Add New Enrollment";
        saveButton.textContent = "Add";
        populateCourseCodes(); // Call the function to populate course codes
        saveButton.setAttribute("onclick", "addEnrollment()");
    } 

    document.getElementById("popupForm").style.display = "block";
}

function populateCourseCodes() {
    var courseCodeSelect = document.getElementById("courseCode");
    var selectedValue = courseCodeSelect.value; // Store the selected value

    // Get the academicYear value
    var academicYear = document.getElementById("academicYear").value;

    var xhr = new XMLHttpRequest();
    xhr.open("GET", "CourseCodeServlet?academicYear=" + encodeURIComponent(academicYear), true); // Include academicYear in the URL
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

function addEnrollment() {
    var enrollmentID = document.getElementById("enrollmentID").value;
    var academicYear = document.getElementById("academicYear").value;
    var courseCode = document.getElementById("courseCode").value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Append new enrollment row to the table body
            var newRowHtml = xhr.responseText;

            var tbody = document.querySelector("#enrollmentTable tbody");
            tbody.insertAdjacentHTML("beforeend", newRowHtml);

            // Clear input fields after adding the new enrollment
            document.getElementById("enrollmentForm").reset();

            // Close the popup after adding the enrollment
            closePopup();
            location.reload();
        }
    };

    xhr.open("POST", "AddEnrollmentServlet", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send("enrollmentID=" + encodeURIComponent(enrollmentID) + "&academicYear=" + encodeURIComponent(academicYear) + "&courseCode=" + encodeURIComponent(courseCode));
}

function assignStaff(enrollmentID, academicYear, courseCode) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                // Check the response for success or error message
                var responseText = xhr.responseText;
                if (responseText.includes("Staff assigned successfully!")) {
                    // Reload the enrollment details after successful assignment
                    location.reload();
                } else {
                    // Display error message if assignment fails
                    alert(responseText);
                }
            } else {
                console.error("Failed to assign staff. Status: " + xhr.status);
            }
        }
    };

    xhr.open("POST", "StaffAssignServlet", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send("enrollmentID=" + encodeURIComponent(enrollmentID) + "&academicYear=" + encodeURIComponent(academicYear) + "&courseCode=" + encodeURIComponent(courseCode));
}
function deleteEnrollment(enrollmentID) {
    // Send an AJAX request to delete the enrollment from the backend
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
            console.log("Response status:", xhr.status); // Log the response status code
            if (xhr.status == 200) {
                // If deletion is successful, reload the page
                location.reload();
            } else {
                console.error('Failed to delete Enrollment.');
            }
        }
    };
    xhr.open("GET", "http://localhost:8083/MiniProjectStaffAllocation/DeleteEnrollmentServlet?enrollmentID=" + enrollmentID, true);
    xhr.send();
}
function searchEnrollments() {
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
    var url = "SearchEnrollmentServlet?enrollmentID=" + encodeURIComponent(searchInput) +
              "&academicYear=" + encodeURIComponent(searchInput) +
              "&courseCode=" + encodeURIComponent(searchInput) +
              "&staffID=" + encodeURIComponent(searchInput);

    // Open the request
    xhr.open("GET", url, true);

    // Send the request
    xhr.send();
}