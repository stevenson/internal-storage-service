# Read Me First

## Description
- this repo is an exercise to support file upload via spring boot
- we attempt to support the following:
  - allow file upload and return file information
  - allow directory creation
  - add file to directory
- This solution tries to separate concerns and allow ease of refactoring
  - if we were to make an alternative Solutions like saving the bytecode of files to a db, we can just add a repo that will integrate with the service layer and create another service layer implementation

### Limitations
1. For this there was no specific download feature required.
2. The path params are not being sanitized but the exception handler should suffice.
  - basically the api assumes paths don't have starting / because the implementation adds it.
  - ie. 
    ```
     curl --location --request GET 'localhost:8080/api/v1/file/?filepath=test/stevenson.png' \
     --form 'file=@"/home/stevenson/Downloads/stevenson.png"'
     ```
    - notice that the filepath has no / at the beginning


## Routes
- this is a restful api with 3 resource: file, directory, and content (list of files)
  - so the effective routes are the following:
    - `api/v1/files`
    - `api/v1/directories`
    - `api/v1/directories/contents`
      - this should be something like directories/id/files; but since I am lazy, I am using a special endpoint

## NOTE:
### Decisions in making the solution
- Ideally (but not for this exercise):
  1. We don't directly work with the file system and just use a service that does this in high scale
  2. For exceptions, we don't need to throw the whole cause but for this exercise we want to just be verbose
  3. We use spring boot with objects that are handled via some persistence solution...
    - but a file in itself is persistent 
    - so the only pojo's I have are data transfer objects
- Models:
  - since Java files handle both directories and files, I decided to create a single model for storage
  - the model is very anemic and a lot of the logic is kept in the service layer
    - but the bulk of the logic is just translating from a file to the model used in response
    - processing requests is left to the controller layer.
- Lastly:
  - I did not use any persistence layer that would need an orm because the File libraries are sufficient
    - if the need would arise we can just create an repo for any specific datasource but for this case we can just use File and Paths
- Testing
  - right now, I am not sure how to best test a File based service layer; 
  - brute force says set up the files, test, and tear it down, but there should be a more elegant way.

### TODO
1. increase test coverage: 
   - add negative tests
   - add stubs and test service layer
