package com.conway.graphqlexample.dao;

import com.conway.graphqlexample.model.MovieModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<MovieModel, String> {
  MovieModel getMovieModelByMovieName(String movieName);
}
