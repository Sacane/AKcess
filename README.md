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
  title: Akcess - Rest documentation 0.1.0
  description: |-
    API REST project Akcess documentation
  termsOfService: http://swagger.io/terms/
  version: 0.1.0
externalDocs:
  description: Find out more about Akcess
  url: http://swagger.io
servers:
  - url: https://akcess.pentagon.com/api
tags:
  - name: Application
    description: Everything to manage your applications
    externalDocs:
      description: Find out more
      url: http://swagger.io
  - name: User
    description: Entity subscribed to a registered application
  - name: Manager
    description: A couple of credentials login and password used to represent a manager of the Akcess server
  - name: info
    description: Global informations of the current Akcess server
paths:
  /application:
    get:
      tags:
        - Application
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
        - Application
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
        - Application
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
        - Application
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
        '404':
          description: Application not found
      security:
        - bearer: []
    delete:
      tags:
        - Application
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
        '200':
          description : Application sucessfully deleted
        '404':
          description: Application not found
      security:
        - bearer: []
    post:
      tags:
        - Application
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
        '404':
          description: Application not found
      security:
        - bearer: []
  /user/{applicationId}:
    post:
      tags:
        - User
      summary: Register a User into a registered Application
      parameters:
        - name: applicationId
          in: path
          description: Application Id to registered the user in
          required: true
          schema:
            type: integer
            format: int64
      requestBody:
        description: Credentials to register
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/User'
      responses:
        '200':
          description: User has successfully been created.
        '400':
          description: The given password or username is blank, empty or null.
        '404':
          description: The applicationId does not exists
      security:
        - bearer: []
    put:
      tags:
        - User
      summary: Update a registered crendential
      requestBody:
        description: Credential to update
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/User'
      parameters:
        - name: applicationId
          in: path
          description: Application Id to registered the user in
          required: true
          schema:
            type: integer
            format: int64
      responses:
        '400':
          description: The given password or username is blank, empty or null.
      security:
        - bearer: []
  /user/access/application/{applicationId}:
    post:
      tags:
        - User
      summary: Check if user has acces to an application
      requestBody:
        description: Check if user has acces to an application by providing the application ID, username and password.
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/UserAcces'
      parameters:
        - name: applicationId
          in: path
          description: Application Id to registered the user in
          required: true
          schema:
            type: integer
            format: int64
      responses:
        '200':
          description: The user is recognize registered into the given application
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/UserTokenRedirection'
        '400':
          description: The given password or username or application ID is blank, empty or null.
        '404':
          description: the given applicationId has not been find
      security:
        - bearer: []
  /user/{userID}:
    delete:
      tags:
        - User
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
        '404':
          description: The user does not exists
      security:
        - bearer: []

    get:
      tags:
        - User
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
        - Manager
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
        - Manager
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
        - Manager
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
          description: The selected manager has its new rights
        '403':
          description: Wrong rights to do this operation
  /manager/{managerId}:
    delete:
      tags:
        - Manager
      summary: Delete a manager from the Akcess server
      security:
        - bearer: []
      parameters:
        - name: managerId
          in: path
          description: ID of role to return
          required: true
          schema:
            type: integer
      responses:
        '200':
          description: Manager has been deleted successfully
        '403':
          description: Wrong rights to do this operation
        '404':
          description: The given manager does not exists by the given ID

  /info/version:
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
          description: The role has successfully been retrieved
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Application_light'
        '404':
          description: The role does not exists
      security:
        - bearer: []
  /role/{roleId}/application/{applicationId}:
    delete:
      tags:
        - role
      summary: Delete a role from an application
      description: Delete a role by its Id from an application from the given's ID
      parameters:
        - name: roleId
          in: path
          description: ID of the role
          required: true
          schema:
            type: integer
        - name: applicationId
          in: path
          description: ID of the application's role
          required: true
          schema:
            type: integer
      responses:
        '200':
          description: the role has been deleted successfully
        '404':
          description: The role or the application does not exists
components:
  responses:
    JwtResponse:
      description: Jwt token succesfully generated
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
      type: string
      example : "CVE-2023-45661"
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
    UserTokenRedirection:
      type: object
      properties:
        access_token:
          type: string
          example: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
        redirection_url:
          type: string
          example: "http://client.myawesomeapplication.com"
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
          description: "login of the entity"
        password:
          type: string
          example: "password"
          description: "password of the entity"
  securitySchemes:
    bearer:
      type: apiKey
      name: Authorization
      in: header
```