package com.movie.mvc.springmoviemvc.controller;

import com.movie.mvc.springmoviemvc.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MovieViewController {

    @Autowired
    private IMovieService movieService;

    @GetMapping("/")   // метод GET на url просто з  адресом "/". Однак, тут не rest, тут mvc
    public String index(Model model){   // повертається назва веб-сторінки. Параметром передаємо Model (це наша Map)

        model.addAttribute("movies", movieService.getAllMovies());  // в model передаємо значення по "ключ"-"значення".  Об'єкти з movieService.getAllMovies() по ключу "movies" передати на веб-сторінку
        return "movies";  // назва веб-сторінки - movies
    }


}
