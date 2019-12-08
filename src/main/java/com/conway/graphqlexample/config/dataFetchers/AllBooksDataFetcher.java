package com.conway.graphqlexample.config.dataFetchers;

import com.conway.graphqlexample.dao.BookRepository;
import com.conway.graphqlexample.model.BookModel;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllBooksDataFetcher implements DataFetcher<List<BookModel>> {

  private final BookRepository bookRepository;

  @Autowired
  public AllBooksDataFetcher(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public List<BookModel> get(DataFetchingEnvironment dataFetchingEnvironment) {
    return bookRepository.findAll();
  }
}
