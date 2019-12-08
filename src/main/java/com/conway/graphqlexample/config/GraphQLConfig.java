package com.conway.graphqlexample.config;

import com.conway.graphqlexample.config.datafetchers.AllBooksDataFetcher;
import com.conway.graphqlexample.config.datafetchers.AllMoviesDataFetcher;
import com.conway.graphqlexample.config.datafetchers.BookDataFetcher;
import com.conway.graphqlexample.config.datafetchers.MovieDataFetcher;
import com.conway.graphqlexample.dao.BookRepository;
import com.conway.graphqlexample.dao.MovieRepository;
import com.conway.graphqlexample.model.BookModel;
import com.conway.graphqlexample.model.MovieModel;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

@Component
public class GraphQLConfig {

  private final BookRepository bookRepository;

  private final MovieRepository movieRepository;

  private GraphQL graphQL;

  private final AllBooksDataFetcher allBooksDataFetcher;
  private final BookDataFetcher bookDataFetcher;
  private final MovieDataFetcher movieDataFetcher;
  private final AllMoviesDataFetcher allMoviesDataFetcher;

  @Value("classpath:schema.graphql")
  Resource resource;

  @Autowired
  public GraphQLConfig(
      BookRepository bookRepository,
      AllBooksDataFetcher allBooksDataFetcher,
      BookDataFetcher bookDataFetcher,
      AllMoviesDataFetcher allMoviesDataFetcher,
      MovieRepository movieRepository,
      MovieDataFetcher movieDataFetcher) {
    this.bookRepository = bookRepository;
    this.allBooksDataFetcher = allBooksDataFetcher;
    this.bookDataFetcher = bookDataFetcher;
    this.allMoviesDataFetcher = allMoviesDataFetcher;
    this.movieRepository = movieRepository;
    this.movieDataFetcher = movieDataFetcher;
  }

  // load schema at application start up
  @PostConstruct
  private void loadSchema() throws IOException {

    // Load data into HSQL
    loadDataIntoHSQL();

    // get the schema
    File schemaFile = resource.getFile();
    // parse schema
    TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
    RuntimeWiring wiring = buildRuntimeWiring();
    GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
    graphQL = GraphQL.newGraphQL(schema).build();
  }

  private void loadDataIntoHSQL() {

    // save books
    Stream.of(
            new BookModel(
                "123",
                "Book of Clouds",
                "Kindle Edition",
                new String[] {"Chloe Aridjis"},
                "Nov 2017"),
            new BookModel(
                "124",
                "Cloud Arch & Engineering",
                "Orielly",
                new String[] {"Peter", "Sam"},
                "Jan 2015"),
            new BookModel(
                "125", "Java 9 Programming", "Orielly", new String[] {"Venkat", "Ram"}, "Dec 2016"))
        .forEach(bookRepository::save);

    // save movies
    MovieModel movieModel1 =
        new MovieModel(
            "1",
            "DARK WATERS",
            "2019-12-06",
            96.0,
            126L,
            "Inspired by a shocking true story, a tenacious attorney (Mark Ruffalo) uncovers a dark secret that connects a growing number of unexplained deaths due to one of the world's largest corporations. In the process, he risks everything -- his future, his family, and his own life -- to expose the truth.");

    MovieModel movieModel2 =
        new MovieModel(
            "2",
            "THE AERONAUTS",
            "2019-12-06",
            92.0,
            101L,
            "In 1862, daredevil balloon pilot Amelia Wren (Felicity Jones) teams up with pioneering meteorologist James Glaisher (Eddie Redmayne) to advance human knowledge of the weather and fly higher than anyone in history. While breaking records and advancing scientific discovery, their voyage to the very edge of existence helps the unlikely pair find their place in the world they have left far below them. But they face physical and emotional challenges in the thin air, as the ascent becomes a fight for survival.");

    MovieModel movieModel3 =
        new MovieModel(
            "3",
            "RAMBO: LAST BLOOD",
            "2019-12-03",
            27.0,
            89L,
            "Almost four decades after he drew first blood, Sylvester Stallone is back as one of the greatest action heroes of all time, John Rambo. Now, Rambo must confront his past and unearth his ruthless combat skills to exact revenge in a final mission. A deadly journey of vengeance, RAMBO: LAST BLOOD marks the last chapter of the legendary series.");

    movieRepository.save(movieModel1);
    movieRepository.save(movieModel2);
    movieRepository.save(movieModel3);
  }

  private RuntimeWiring buildRuntimeWiring() {
    return RuntimeWiring.newRuntimeWiring()
        .type(
            "Query",
            typeWiring ->
                typeWiring
                    .dataFetcher("allBooks", allBooksDataFetcher)
                    .dataFetcher("book", bookDataFetcher)
                    .dataFetcher("allMovies", allMoviesDataFetcher)
                    .dataFetcher("movie", movieDataFetcher))
        .build();
  }

  public GraphQL getGraphQL() {
    return graphQL;
  }
}
