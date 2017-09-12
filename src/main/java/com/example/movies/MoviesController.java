package com.example.movies;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.input.DataFormat.URL;

@Controller
public class MoviesController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/now-playing")
    public String nowPlaying(Model model) {
        model.addAttribute("movies", getMovies("now-playing"));
        System.out.println(getMovies("now-playing"));
        return "now-playing";
    }

    @GetMapping("/medium-popular-long-name")
    public String mediumPopularLongName(Model model){
        List<Movie> nowPlaying = getMovies("medium-popular-long-name");
        List<Movie> mediumPopularLongName = new ArrayList<>();
        for (Movie movie : nowPlaying){
            if (movie.getPopularity() >= 30 && movie.getPopularity() <= 80 && movie.getTitle().length() >= 10){
                mediumPopularLongName.add(movie);
            }
        }
        model.addAttribute("mediumPopularLongName", mediumPopularLongName);
        return "medium-popular-long-name";


    }

    public static List<Movie> getMovies(String route){
        RestTemplate restTemplate = new RestTemplate();
        ResultsPage nowPlaying = restTemplate.getForObject("https://api.themoviedb.org/3/movie/now_playing?api_key=8cd89e75b05310c27b3e0cd1c784842f", ResultsPage.class);
        //System.out.println(nowPlaying);
        return nowPlaying.results;


    }

}
