# Read Me First

## Description
- this repo is an exercise to support file upload via spring boot
- we attempt to support the following:
  - allow file upload and return file information
  - allow directory creation
  - add file to directory
- this solutions tries to separate concerns and allow ease of refactoring

## NOTE: 
- normally we don't directly work with the file system and just use a service that does this in high scale
- also normally we use spring boot with objects that are handled via some persistence solution...
  - but a file in itself is persistent 
  - so the only pojo's i have are data transfer objects