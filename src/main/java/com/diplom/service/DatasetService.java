package com.diplom.service;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stole on 4/27/2016.
 */
@Service
public class DatasetService {

    public List<String> getDepts(String startWith) throws MalformedURLException, FileNotFoundException {

        String QueryString =
                        "prefix dbo: <http://dbpedia.org/ontology/>" +
                        "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                "select distinct ?label ?Concept where {[] dbo:academicDiscipline ?Concept." +
                        "?Concept rdfs:label ?label." +
                        "FILTER regex(?Concept,'^http://dbpedia.org/resource/" + startWith +"','i')" +
                        "FILTER (lang(?label) = 'en')" +
                        "}";
        Query query = QueryFactory.create(QueryString);
        QueryExecution qe = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
        ResultSet result = qe.execSelect();
        List<String> values = new ArrayList<>();
        while(result.hasNext())
        {
            QuerySolution solution = result.next();
            values.add(solution.get("label").asLiteral().getString().replaceFirst("@en",""));
        }
        return values;
    }
}
