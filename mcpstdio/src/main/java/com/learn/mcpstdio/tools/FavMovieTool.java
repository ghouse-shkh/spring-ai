package com.learn.mcpstdio.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class FavMovieTool {

    private record Movie(String name, String genre, String language) {
    };

    private final List<Movie> movies = new ArrayList<>();

    @Tool(name = "addMovie", description = "Add a movie to the list of favorite movies")
    public void addMovie(@ToolParam(description = "Name of the movie") String name,
            @ToolParam(description = "Genre of the movie") String genre,
            @ToolParam(description = "Language of the movie") String language) {
        movies.add(new Movie(name, genre, language));
    }

    @Tool(name = "getAllMovies", description = "Get all favorite movies")
    public List<Movie> getAllMovies() {
        return new ArrayList<>(movies);
    }

    @Tool(name = "getMoviesByGenre", description = "Get favorite movies by genre")
    public List<Movie> getMoviesByGenre(@ToolParam(description = "Genre of the movie") String genre) {
        return movies.stream()
                .filter(movie -> movie.genre().equalsIgnoreCase(genre))
                .collect(Collectors.toUnmodifiableList());
    }

    @Tool(name = "getMoviesByLanguage", description = "Get favorite movies by language")
    public List<Movie> getMoviesByLanguage(@ToolParam(description = "Language of the movie") String language) {
        return movies.stream()
                .filter(movie -> movie.language().equalsIgnoreCase(language))
                .collect(Collectors.toUnmodifiableList());
    }

}
