package com.conway.graphqlexample.config.datafetchers;

import com.conway.graphqlexample.dao.BookRepository;
import com.conway.graphqlexample.model.BookModel;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookDataFetcher implements DataFetcher<BookModel> {

  private final BookRepository bookRepository;

  @Autowired
  public BookDataFetcher(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public BookModel get(DataFetchingEnvironment dataFetchingEnvironment) {

    String isn = dataFetchingEnvironment.getArgument("id");

    return bookRepository.findById(isn).get();
  }
}
