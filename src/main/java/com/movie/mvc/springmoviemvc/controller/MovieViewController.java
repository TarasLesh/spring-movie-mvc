package com.movie.mvc.springmoviemvc.controller;

import com.movie.mvc.springmoviemvc.model.Movie;
import com.movie.mvc.springmoviemvc.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MovieViewController {

    @Autowired
    private IMovieService movieService;

    @GetMapping("/")   // метод GET на url просто з  адресом "/". Однак, тут не rest, тут mvc
    public String index(Model model){   // повертається назва веб-сторінки. Параметром передаємо Model (це наша Map)

        model.addAttribute("movies", movieService.getAllMovies());  // в model передаємо значення по "ключ"-"значення".  Об'єкти з movieService.getAllMovies() по ключу "movies" передати на веб-сторінку
        model.addAttribute("newMovie", new Movie()); // на форму потрібно відправляти пустий об’єкт (newMovie).
        return "movies";  // назва веб-сторінки - movies
    }

    @PostMapping("/movie")  // метод POST на url /movie
    public String createMovie(@ModelAttribute Movie movie){  //
    movieService.createMovie(movie);
    return "redirect:/";  // редіректимо на /. ТОбто відпрацює метод index з  GET
    }

}
