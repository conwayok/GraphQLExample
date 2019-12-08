package com.conway.graphqlexample.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Movies")
@AllArgsConstructor
@NoArgsConstructor
public class MovieModel {

  @Id String movieId;
  String movieName;
  String releaseDate;
  Double rating;
  Long lengthMinutes;

  @Column(name = "description", length = 1000)
  String description;
}
