package com.weather.weatherforecast.weather;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/favorites")
public class FavoriteLocationServlet extends HttpServlet {

    private final String jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
    private final String jdbcUsername = "postgres";
    private final String jdbcPassword = "Gokul123@";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<FavoriteLocation> favoriteLocations = getAllFavoriteLocations();

        resp.setContentType("application/json");
        resp.getWriter().write(objectMapper.writeValueAsString(favoriteLocations));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            // Read JSON payload from the request body
            FavoriteLocationRequest requestBody = objectMapper.readValue(req.getReader(), FavoriteLocationRequest.class);

            String placeName = requestBody.getPlaceName();
            String placeDescription = requestBody.getPlaceDescription();

            System.out.println("Received placeName: " + placeName + ", placeDescription: " + placeDescription);

            // Check for null and provide default values if needed
            if (placeName == null) {
                placeName = "Chennai"; // Replace with an appropriate default value
            }
            placeDescription = (placeDescription != null) ? placeDescription : "Hometown";

            FavoriteLocation addedLocation = addFavoriteLocation(placeName, placeDescription);

            resp.setContentType("application/json");
            resp.getWriter().write(objectMapper.writeValueAsString(addedLocation));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"success\": false, \"error\": \"Invalid JSON payload\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");

        if (idParam != null) {
            try {
                long id = Long.parseLong(idParam);
                boolean deleted = deleteFavoriteLocation(id);

                resp.setContentType("application/json");

                if (deleted) {
                    resp.getWriter().write("{\"success\": true}");
                } else {
                    resp.getWriter().write("{\"success\": false, \"error\": \"Favorite not found\"}");
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }

            } catch (NumberFormatException e) {
                resp.setContentType("application/json");
                resp.getWriter().write("{\"success\": false, \"error\": \"Invalid ID format\"}");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            resp.setContentType("application/json");
            resp.getWriter().write("{\"success\": false, \"error\": \"ID parameter is missing\"}");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private List<FavoriteLocation> getAllFavoriteLocations() {
        List<FavoriteLocation> favoriteLocations = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword); Statement statement = connection.createStatement()) {

            String sql = "SELECT * FROM favorites";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                FavoriteLocation location = new FavoriteLocation();
                location.setId(resultSet.getLong("id"));
                location.setPlaceName(resultSet.getString("name"));
                location.setPlaceDescription(resultSet.getString("description"));
                favoriteLocations.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return favoriteLocations;
    }

    private FavoriteLocation addFavoriteLocation(String placeName, String placeDescription) {
        FavoriteLocation addedLocation = null;

        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword); PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO favorites (name, description) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, placeName);
            preparedStatement.setString(2, placeDescription);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    addedLocation = new FavoriteLocation();
                    addedLocation.setId(generatedKeys.getLong(1));
                    addedLocation.setPlaceName(placeName);
                    addedLocation.setPlaceDescription(placeDescription);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return addedLocation;
    }

    private boolean deleteFavoriteLocation(long id) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword); PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM favorites WHERE id = ?")) {

            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static class FavoriteLocation {

        private long id;
        private String placeName;
        private String placeDescription;

        public FavoriteLocation() {
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getPlaceName() {
            return placeName;
        }

        public void setPlaceName(String placeName) {
            this.placeName = placeName;
        }

        public String getPlaceDescription() {
            return placeDescription;
        }

        public void setPlaceDescription(String placeDescription) {
            this.placeDescription = placeDescription;
        }
    }

    private static class FavoriteLocationRequest {

        private String placeName;
        private String placeDescription;

        public String getPlaceName() {
            return placeName;
        }

        public void setPlaceName(String placeName) {
            this.placeName = placeName;
        }

        public String getPlaceDescription() {
            return placeDescription;
        }

        public void setPlaceDescription(String placeDescription) {
            this.placeDescription = placeDescription;
        }
    }
}
