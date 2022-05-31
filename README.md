# Read Me First

## Description
- this repo is an exercise to support file upload via spring boot
- we attempt to support the following:
  - allow file upload and return file information
  - allow directory creation
  - add file to directory
- this solutions tries to separate concerns and allow ease of refactoring

## Routes
- this is a restful api with 3 resource: file, directory, and content (list of files)
  - so the effective routes are the following:
    - `api/v1/files`
    - `api/v1/directories`
    - `api/v1/directories/contents`
      - this should be something like directories/id/files but since i am lazy i am using a special endpoint
## NOTE:
### Decisions in making the solution
- normally we don't directly work with the file system and just use a service that does this in high scale
- also normally we use spring boot with objects that are handled via some persistence solution...
  - but a file in itself is persistent 
  - so the only pojo's i have are data transfer objects
- since Java files handle both directories and files, i decided to create a single model for storage
- the model is very anemic and a lot of the logic is kept in the service layer
  - but the bulk of the logic is just translating from a file to the model used in response
  - processing requests is left to the controller layer.
- I did not use any persistence layer that would need an orm because the File libraries are sufficient
  - if the need would arise we can just create an repo for any specific datasource but for this case we can just use File and Paths

### TODO
1. create a model for exceptions and a rest advice that would handle it
   - add tests
2. increase test coverage
