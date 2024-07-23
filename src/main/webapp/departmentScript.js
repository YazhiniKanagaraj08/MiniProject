function loadDepartmentDetails() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("departmentTable").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "DepartmentDetailsServlet", true);
    xhr.send();
}

function openPopup(action, departmentID, departmentName, hod) {
    var popupTitle = document.getElementById("popupTitle");
    var saveButton = document.getElementById("popupSaveButton");

    if (action === 'add') {
        popupTitle.textContent = "Add New Department";
        saveButton.textContent = "Add";
        saveButton.setAttribute("onclick", "addDepartment()");
    } else if (action === 'update') {
        popupTitle.textContent = "Update Department";
        saveButton.textContent = "Save";
        saveButton.setAttribute("onclick", "updateDepartment()");
        
        // Populate the update form with existing values
        document.getElementById("departmentID").value = departmentID;
        document.getElementById("departmentName").value = departmentName;
        document.getElementById("hod").value = hod;
        
    }

    document.getElementById("popupForm").style.display = "block";
}

function closePopup() {
    document.getElementById("popupForm").style.display = "none";
}

function addDepartment() {
    var departmentID = document.getElementById("departmentID").value;
    var departmentName = document.getElementById("departmentName").value;
    var hod = document.getElementById("hod").value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Append new course row to the table body
            var newRowHtml = xhr.responseText;

            var tbody = document.querySelector("#departmentTable tbody");
            tbody.insertAdjacentHTML("beforeend", newRowHtml);

            // Clear input fields after adding the new course
            document.getElementById("departmentForm").reset();

            // Close the popup after adding the course
            closePopup();
        }
    };
    xhr.open("GET", "AddDepartmentServlet?departmentID=" + departmentID + "&departmentName=" + departmentName + "&hod=" + hod, true);
    xhr.send();
}

function updateDepartment() {
    var departmentID = document.getElementById("departmentIDHidden").value;
    var departmentName = document.getElementById("departmentName").value;
    var hod = document.getElementById("hod").value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                // Close the popup after updating the course
                closePopup();
                // Reload the table with updated data
                loadDepartmentDetails();
            } else {
                console.error('Failed to update department.');
            }
        }
    };
    xhr.open("POST", "UpdateDepartmentServlet", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    var params = "departmentID=" + encodeURIComponent(departmentID) +
                 "&departmentName=" + encodeURIComponent(departmentName) +
                 "&hod=" + encodeURIComponent(hod);
    xhr.send(params);
}

function deleteDepartment(departmentID) {
    // Send an AJAX request to delete the course from the backend
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
            console.log("Response status:", xhr.status); // Log the response status code
            if (xhr.status == 200) {
                // If deletion is successful, reload the page
                location.reload();
            } else {
                console.error('Failed to delete department.');
            }
        }
    };
    xhr.open("GET", "http://localhost:8083/MiniProjectStaffAllocation/DeleteDepartmentServlet?departmentID=" + departmentID, true);
    xhr.send();
}

function searchDepartments() {
    // Get the search input value
    var searchInput = document.getElementById("searchInput").value.trim();

    // Create a new XMLHttpRequest object
    var xhr = new XMLHttpRequest();

    // Configure the request
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Replace the table body content with the response from the servlet
            document.getElementById("departmentTable").innerHTML = xhr.responseText;
        }
    };

    // Construct the URL with the search query parameters
    var url = "SearchDepartmentServlet?courseCode=" + encodeURIComponent(searchInput) +
              "&departmentName=" + encodeURIComponent(searchInput) +
              "&departmentID=" + encodeURIComponent(searchInput) +
              "&hod=" + encodeURIComponent(searchInput);

    // Open the request
    xhr.open("GET", url, true);

    // Send the request
    xhr.send();
}
