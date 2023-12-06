# Akcess

## Contributors

- Johan "Sacane" Ramaroson Rakotomihamina
- Mathis "Verestah" Menaa
- Yohann "Haine" Regueme
- Quentin "qtdrake" Tellier
- Omairt Kamdom


## Swagger editor
```yml
openapi: 3.0.3
info:
  title: Akcess - OpenAPI 3.0
  description: |-
    API project akcess documentation
  termsOfService: http://swagger.io/terms/
  version: 1.0.0
externalDocs:
  description: Find out more about Swagger
  url: http://swagger.io
servers:
  - url: https://akcess.swagger.io/api
tags:
  - name: application
    description: Everything to manage your applications
    externalDocs:
      description: Find out more
      url: http://swagger.io
  - name: user
    description: A couple of credentials username and password used to represent a user
  - name: manager
    description: A couple of credentials login and password used to represent a manager of the Akcess server
paths:
  /application:
    get:
      tags:
        - application
      summary: Get all the registered applications
      description: Get all the registered applications in the akcess server
      operationId: listApplication
      responses:
        '200':
          description: successful operation
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Application_light'
      security:
        - bearer: []
    post:
      tags:
        - application
      summary: Add a new application
      description: Add a new application to your applications
      operationId: addApplication
      requestBody:
        description: Create a new application
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Application_light'
        required: true
      responses:
        '200':
          description: Successful operation
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Application_light'          
        '400':
          description: Invalid input
      security:
        - bearer: []
    put:
      tags:
        - application
      summary: Update an existing application
      description: Update an existing application by Id
      operationId: updateApplication
      requestBody:
        description: Update an existent application
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Application_full'
        required: true
      responses:
        '200':
          description: Successful operation
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Application_full'          
        '400':
          description: Invalid ID supplied
        '404':
          description: Application not found
        '405':
          description: Validation exception
      security:
        - bearer: []
  /application/{applicationId}:
    get:
      tags:
        - application
      summary: Find application by ID
      description: Returns informations of an application
      operationId: getApplicationById
      parameters:
        - name: applicationId
          in: path
          description: ID of application to return
          required: true
          schema:
            type: integer
            format: int64
      responses:
        '200':
          description: successful operation
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Application_full'          
        '400':
          description: Invalid ID supplied
        '404':
          description: Application not found
      security:
        - bearer: []
    delete:
      tags:
        - application
      summary: Deletes an application
      description: Deletes an application
      operationId: deleteApplication
      parameters:
        - name: applicationId
          in: path
          description: Application id to delete
          required: true
          schema:
            type: integer
            format: int64
      responses:
        '400':
          description: Invalid application value
        '200':
          description : Application sucessfully deleted
      security:
        - bearer: []
    post:
      tags:
        - application
      summary: Copy an app
      description: Copy an app
      parameters:
        - name: applicationId
          in: path
          description: Application id to copy
          required: true
          schema:
            type: integer
            format: int64
      responses:
        '400':
          description: Invalid application value
      security:
        - bearer: []
  /user:
    post:
      tags:
        - user
      summary: Register a credentials
      requestBody:
        description: Credentials to register
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/User'
      responses:
        '400':
          description: The given password or username is blank, empty or null.
      security:
        - bearer: []
    put:
      tags:
        - user
      summary: Update a registered crendential
      requestBody:
        description: Credential to update
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/User'
      responses:
        '400':
          description: The given password or username is blank, empty or null.
      security:
        - bearer: []
  /user/acces:
    post:
        tags:
          - user
        summary: Check if user has acces to an application
        requestBody:
          description: Check if user has acces to an application by providing the application ID, username and password.
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/UserAcces'
        responses:
          '400':
            description: The given password or username or application ID is blank, empty or null.
        security:
          - bearer: []
  /user/{userID}:      
    delete:
      tags:
        - user
      summary: Deletes credendials of an user
      description: Deletes credendials of an user
      operationId: deleteUser
      parameters:
        - name: userID
          in: path
          description: User id to delete
          required: true
          schema:
            type: integer
            format: int64
      responses:
        '400':
          description: Invalid application value
        '200' :
          description: User successfully deleted
      security:
        - bearer: []
      
    get:
      tags:
        - user
      summary: Retrieves informations of a specific user
      description: Retrieves informations of a specific user
      operationId: getUserInformation
      parameters:
        - name: userID
          in: path
          description: ID of the user
          required: true
          schema:
            type: integer
            format: int64
      responses:
        '200':
          description: successful operation
        '400':
          description: Invalid ID supplied
        '404':
          description: User not found
      security:
        - bearer: []
      
  /manager/auth:
    post:
      tags:
        - manager
      summary: Ask for an Access token for authentication as manager
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Credentials'
      responses:
          '200':
            $ref: '#/components/responses/JwtResponse'
          '400':
            description: The given password or username is blank, empty or null. Also if the password does not correspond to the templates.
          '403':
            description: Wrong rights to ask for an Access token
      security:
      - bearer: []
  /manager:
    post:
      tags:
        - manager
      summary: Create another manager for the Akcess server
      security:
        - bearer: []
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Manager'
      responses:
          '200':
            description: OK
          '400':
            description: Invalid credential
          '403': 
            description: Wrong rights to do this operation
    put:
      tags:
        - manager
      summary: Give new rights to another manager
      security: 
        - bearer: []
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/ManagerWithRoles'
      responses:
        '200':
          description: OK
        '403': 
          description: Wrong rights to do this operation
          
          
  /info:
    get:
      tags:
        - info
      summary: Get the current version of the API
      description: Get the current version of the API
      operationId: version
      responses:
        '200':
          description: successful operation
          content:
            application/json:
              schema:
                items:
                  $ref: '#/components/schemas/API_Info'
      security:
        - bearer: []
  /info/cve:
    get:
      tags:
        - info
      summary: Get the Common Vulnerabilities and Exposures.
      description: Get the Common Vulnerabilities and Exposures of this application version.
      responses:
        '200':
          description: successful operation
          content:
            application/json:
              schema:
                items:
                  type: array
                  $ref: '#/components/schemas/CVE_Info'
  /role/{roleId}:
    get:
      tags:
        - role
      summary: find role by ID
      description: Returns informations of a role
      operationId: getRole
      parameters:
        - name: roleId
          in: path
          description: ID of role to return
          required: true
          schema:
            type: integer
            format: int64
      responses:
        '200':
          description: successful operation
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Role'
        '400':
          description: Invalid ID supplied
        '404':
          description: Application not found
      security:
        - bearer: []
  /role:
    post:
      tags:
        - role
      summary: Add a new role
      description: Creates a new role
      operationId: addRole
      requestBody:
        description: Creates a new role
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Role'
        required: true
      responses:
        '200':
          description: successful operation
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Role'
        '400':
          description: the application Id is incorrect
      security:
        - bearer: [] 
    put:
      tags:
        - role
      summary: Update an existing role
      description: Returns informations of a role
      operationId: updateRole
      requestBody:
        description: update an existent role
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Role'
        required: true
      responses:
        '200':
          description: successful operation
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Role'
        '400':
          description: the application Id or the role Id is incorrect
        '404':
          description: the application Id or the role Id is not found
      security:
        - bearer: [] 
  /role/{applicationId}:
    get:
      tags:
        - role
      summary: Get all the registered applications
      description: Get all the roles from an application
      operationId: listRole
      parameters:
        - name: applicationId
          in: path
          description: ID of the application we wish the roles of
          required: true
          schema:
            type: integer
            format: int64
      responses:
        '200':
          description: successful operation
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Application_light'
      security:
        - bearer: []
      
    
components:
  responses:
    JwtResponse:
      description: OK
      content:
        application/json:
          example:
            token: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
  schemas:
    Application_light:
      type: object
      properties:
        id:
          type: integer
          format: int64
          example: 10
        name:
          type: string
          example: "MyAwesomeApplication"
        root_url: 
          type: string
          example: "https://myawesomeapplication.com/"
    API_Info:
      type: object
      properties:
        name:
          type: string
          example : "AKCESS"
        version:
          type: string
          example: "9.0.1"
    CVE_Info:
      type: object
      properties:
        cveId:
          type: string
          example : ["CVE-2023-43281", "CVE-2023-45661"]
    Application_full:
      type: object
      properties:
        id:
          type: integer
          format: int64
          example: 10
        name:
          type: string
          example: "MyAwesomeApplication"
        root_url:
          type: string
          example: "https://myawesomeapplication.com/"
        users:
          type: array
          items:
            $ref: '#/components/schemas/User'
        roles:
          type: array
          items:
            $ref: '#/components/schemas/Role'
    User:
      type: object
      properties:
        id:
          type: integer
          format: int64
          example: 10
        username:
          type: string
          example: "username"
        password:
          type: string
          example: '12345'
    UserAcces:
      type: object
      properties:
        applicationId:
          type: integer
          format: int64
          example: 10
        username:
          type: string
          example: "username"
        password:
          type: string
          example: '12345'
    Role:
      type: object
      properties:
        id:
          type: integer
          format: int64
          example: 1
        label:
          type: string
          example: "functional_admin"
        application_reference_id:
          type: integer
          format: int64
          example: 2
    Manager:
      type: object
      properties:
        id:
          type: integer
          example: 5
        credentials:
          $ref: '#/components/schemas/Credentials'
        roles: 
          type: array
          items:
            type: string
          example: ["user", "admin", "maintainer"]
    ManagerWithRoles:
      type: object
      properties:
        manager_id:
          type: integer
          example: 5
        roles:
          type: array
          items:
            type: string
          example: ["admin", "functional_admin"]
    Credentials:
      type: object
      properties:
        login:
          type: string
          example: "login"
        password:
          type: string
          example: "password"
  securitySchemes:
    bearer:
      type: apiKey
      name: Authorization
      in: header
```