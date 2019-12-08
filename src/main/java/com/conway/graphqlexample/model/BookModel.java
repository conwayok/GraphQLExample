package com.conway.graphqlexample.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Books")
@Entity
public class BookModel {

  @Id private String isn;
  private String title;
  private String publisher;
  private String[] authors;
  private String publishedDate;
}
