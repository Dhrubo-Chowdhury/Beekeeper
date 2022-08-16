# Beekeeper
## Problem Statement:
A client named Soleng has requested the following pieces of information in csv format about activity in their Beekeeper platform:
  1. the number of posts in each stream 	
  2. the number of comments from all of the posts per stream 
  3. the number of likes from all of the posts per stream 
  
## Generated Output:
 ```
"streamId","postCount","commentCount","likeCount"
"91234","2","0","3"
"91235","0","0","0"
"91231","8","8","16"
"91236","4","0","0"
"91233","2","0","1"
"91241","10","0","0"
"91237","3","0","0"
"91238","2","0","1"
"91240","3","0","0"
"91242","6","2","0"
"91239","4","0","0"
"91232","5","0","0"
```

## APIs used:
1. "/api/2/streams" (called one time) : get all the streamIds and store inside a list
2. "/api/2/streams/{stream_id}/posts" (called N times, where N is the total number of streams)

## Challenges:
1. how to get the api key using bot: instructions from the Solutions Engineer Technical Interview Task PDF
2. how to use the authorization header in Java environment with api key: https://stackoverflow.com/questions/19238715/how-to-set-an-accept-header-on-spring-resttemplate-request
3. how to write to a csv file: using java opencsv maven dependency https://www.geeksforgeeks.org/writing-a-csv-file-in-java-using-opencsv/
