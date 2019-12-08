package com.conway.graphqlexample.dao;

import com.conway.graphqlexample.model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookModel, String> {
}
