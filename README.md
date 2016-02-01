### Installation

  You need to install maven first.

      $ mvn clean package


### How to run 


      $ mvn spring-boot:run 


  POST: http://localhost:8080/login

  Admin

      {
        "username": "chris",
        "password": "123456"
      }
  

  User

      {
        "username": "kitty",
        "password": "123456"
      }

  
  Add the token to request header
  

      Authorization: Bearer [token]

  
  GET: http://localhost:8080/public
  
  GET: http://localhost:8080/protected
  
  GET: http://localhost:8080/admin