package com.example.beekeeper;
import com.opencsv.CSVWriter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api")
public class Controller {

    @GetMapping("/getcsv")
    public void getCsv() throws Exception {

        File file = new File("./newFile.csv");
        FileWriter outputfile = new FileWriter(file);
        CSVWriter writer = new CSVWriter(outputfile);

        String[] header = {"streamId", "postCount", "commentCount", "likeCount"};
        writer.writeNext(header);

        String url1 = "https://soleng-inc.us.beekeeper.io/api/2/streams";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Token 145ce186-3726-44dd-b96d-b14c4f3ebc09");

        // create request
        HttpEntity request = new HttpEntity(httpHeaders);

        ResponseEntity<List<Map<String, Object>>> streamIds = new RestTemplate().exchange(url1, HttpMethod.GET, request, new ParameterizedTypeReference<List<Map<String, Object>>>() {
        });

        List<Map<String, Object>> ids = streamIds.getBody();

        List<Integer> Ids = new ArrayList<>();

        for (Map<String, Object> stream : ids) {
            Ids.add((int) stream.get("id"));
        }

        for (Integer id : Ids) {
            String url = "https://soleng-inc.us.beekeeper.io/api/2/streams/" + String.valueOf(id) + "/posts";
            ResponseEntity<List<Map<String, Object>>> response = new RestTemplate().exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<List<Map<String, Object>>>() {});
            List<Map<String, Object>> res = response.getBody();

            int commentCount = 0;
            int likeCount = 0;

            for (Map<String, Object> post : res) {
                commentCount += (int) post.get("comment_count");
                likeCount += (int) post.get("like_count");
            }

            System.out.println(commentCount + " " + likeCount);

            String[] data = {String.valueOf(id), String.valueOf(res.size()), String.valueOf(commentCount), String.valueOf(likeCount)};

            writer.writeNext(data);

        }

        writer.close();

    }
}
