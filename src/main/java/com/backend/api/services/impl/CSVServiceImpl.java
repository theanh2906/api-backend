package com.backend.api.services.impl;

import com.backend.api.services.CSVService;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CSVServiceImpl implements CSVService {
    @Override
    public List<String> toJson(String filePath) {
        String json;
        List<String> headers = new ArrayList<>();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(filePath));
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            headers = parser.getHeaderNames();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return headers;
    }

    public static void main(String[] args) {
//        CSVServiceImpl test = new CSVServiceImpl();
//        String filePath = "uploads/SonarQube-assigned.csv";
////        test.toJson(file).stream().forEach(System.out::println);
//        test.toJson(filePath).stream().forEach(System.out::println);
        File input = new File("uploads/SonarQube-assigned.csv");
        try {
            CsvSchema csv = CsvSchema.emptySchema().withHeader();
            CsvMapper csvMapper = new CsvMapper();
            MappingIterator<Map<?, ?>> mappingIterator =  csvMapper.reader().forType(Map.class).with(csv).readValues(input);
            List<Map<?, ?>> list = mappingIterator.readAll();
            System.out.println(list);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
