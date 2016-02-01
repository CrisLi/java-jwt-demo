### Installation

  You need to install maven first.
  
  ```shell
  $ mvn clean package
  ```

### How to run 

  ```shell
  $ mvn spring-boot:run 
  ```

  POST: http://localhost:8080/login

  Admin

  ```javascript
  {
    "username": "chris",
    "password": "123456"
  }
  ```

  User

  ```javascript
  {
    "username": "kitty",
    "password": "123456"
  }
  ```
  
  Add the token to request header
  
  ```shell
  Authorization: Bearer [token]
  ```
  
  GET: http://localhost:8080/public
  
  GET: http://localhost:8080/protected
  
  GET: http://localhost:8080/admin