package com.conway.graphqlexample.controller;

import com.conway.graphqlexample.config.GraphQLConfig;
import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/graphql")
@RestController
public class GraphQLController {

  private final GraphQLConfig graphQLConfig;

  @Autowired
  public GraphQLController(GraphQLConfig graphQLConfig) {
    this.graphQLConfig = graphQLConfig;
  }

  @PostMapping("/endpoint")
  @ResponseBody
  public ResponseEntity<Object> get(@RequestBody String query) {
    ExecutionResult execute = graphQLConfig.getGraphQL().execute(query);
    return new ResponseEntity<>(execute, HttpStatus.OK);
  }
}
