package com.conway.graphqlexample.config.dataFetchers;

import com.conway.graphqlexample.dao.MovieRepository;
import com.conway.graphqlexample.model.MovieModel;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllMoviesDataFetcher implements DataFetcher<List<MovieModel>> {

  private final MovieRepository movieRepository;

  @Autowired
  public AllMoviesDataFetcher(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  @Override
  public List<MovieModel> get(DataFetchingEnvironment environment) {
    return movieRepository.findAll();
  }
}
