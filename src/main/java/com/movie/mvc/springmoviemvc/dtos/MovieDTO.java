package com.movie.mvc.springmoviemvc.dtos;

import com.movie.mvc.springmoviemvc.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MovieDTO {
    private List<Movie> movies;
    private int totalPages;
    private int pagesCount;
    private boolean isLast;
    private boolean isEmpty;
}
