package com.conway.graphqlexample.config.datafetchers;

import com.conway.graphqlexample.dao.MovieRepository;
import com.conway.graphqlexample.model.MovieModel;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieDataFetcher implements DataFetcher<MovieModel> {

  private final MovieRepository movieRepository;

  @Autowired
  public MovieDataFetcher(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  @Override
  public MovieModel get(DataFetchingEnvironment environment) {

    String title = environment.getArgument("movieName");

    return movieRepository.getMovieModelByMovieName(title);
  }
}
