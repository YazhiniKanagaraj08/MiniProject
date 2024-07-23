package com.servlet.dashboard;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.servlet.course.SearchCourseServlet;
import com.servlet.staff.SearchStaffServlet;
import com.servlet.department.SearchDepartmentServlet;
import com.servlet.allotment.SearchAllotmentServlet;
import com.servlet.enrollment.SearchEnrollmentServlet;

@SuppressWarnings("unused")
@WebServlet("/CentralizedSearchServlet")
public class CentralizedSearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve search query parameters from the request
        String searchQuery = request.getParameter("searchQuery");

        // Perform search operation based on the provided search query
        String allSearchResults = performCentralizedSearch(searchQuery);

        // Prepare the response
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Output search results as HTML
        out.println("<html>");
        out.println("<body>");
        out.println("<h1>Search Results</h1>");
        out.println(allSearchResults);
        out.println("</body>");
        out.println("</html>");

        out.close();
    }

    private String performCentralizedSearch(String searchQuery) {
        StringBuilder allSearchResults = new StringBuilder();
        String searchResultHeader = "<h2>%s Results:</h2>";

        // Check if the search query contains specific keywords to determine the type of search
        if (searchQuery.toLowerCase().contains("course")) {
            allSearchResults.append(String.format(searchResultHeader, "Course"));
            allSearchResults.append(getSearchResultsFromServlet("/SearchCourseServlet", searchQuery));
        }
        else if (searchQuery.toLowerCase().contains("staff")) {
            allSearchResults.append(String.format(searchResultHeader, "Staff"));
            allSearchResults.append(getSearchResultsFromServlet("/SearchStaffServlet", searchQuery));
        }
        else if (searchQuery.toLowerCase().contains("department")) {
            allSearchResults.append(String.format(searchResultHeader, "Department"));
            allSearchResults.append(getSearchResultsFromServlet("/SearchDepartmentServlet", searchQuery));
        }
        else if (searchQuery.toLowerCase().contains("allotment")) {
            allSearchResults.append(String.format(searchResultHeader, "Allotment"));
            allSearchResults.append(getSearchResultsFromServlet("/SearchAllotmentServlet", searchQuery));
        }
        else if (searchQuery.toLowerCase().contains("enrollment")) {
            allSearchResults.append(String.format(searchResultHeader, "Enrollment"));
            allSearchResults.append(getSearchResultsFromServlet("/SearchEnrollmentServlet", searchQuery));
        }

        // If no specific type is identified, add a message indicating the search type
        else if (allSearchResults.length() == 0) {
            allSearchResults.append("<h2>Search Results:</h2>");
            allSearchResults.append("<p>No specific search type identified.</p>");
        }

        return allSearchResults.toString();
    }


    // Helper method to get search results from individual search servlets
    private String getSearchResultsFromServlet(String servletUrl, String searchQuery) {
        // Here, you would make an HTTP request to the specified servlet URL with the search query
        // and receive the search results as a response.
        // For brevity, I'll simulate the result as a placeholder string.

        // Placeholder for simulated search results
        return "<p>This is a placeholder for search results from " + servletUrl + " for query: " + searchQuery + "</p>";
    }
}
