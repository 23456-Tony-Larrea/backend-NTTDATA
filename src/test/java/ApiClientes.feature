Feature: Plan de testear APIs Clientes
  Background:
    * def urlBase = 'http://localhost:8080/api'
    * def clientePath = '/clientes'
    * def nuevoCliente = { "nombre": "John Doe", "genero": "Male", "edad": 30, "identificacion": "123456789", "direccion": "123 Main St", "telefono": "555-1234", "contraseña": "password", "estado": true }
    * def clienteActualizado = { "nombre": "Jane Doe", "genero": "Female", "edad": 28, "identificacion": "987654321", "direccion": "456 Elm St", "telefono": "555-5678", "contraseña": "newpassword", "estado": true }

  Scenario: Obtener todos los clientes
    Given url urlBase + clientePath
    When method get
    Then status 200
    And match response == []


  Scenario: Obtener un cliente por ID
    Given url urlBase + clientePath + '/1'
    When method get
    Then status 200
    And match response.id == 1

  Scenario: Crear un nuevo cliente
    Given url urlBase + clientePath
    And request nuevoCliente
    When method post
    Then status 200
    And match response.nombre == 'John Doe'
    And match response.estado == true


    # Obtener el cliente recién creado
    Given url urlBase + clientePath + '/' + '/1'
    When method get
    Then status 200
    And match response.id == '/1'

  Scenario: Actualizar un cliente existente
    # Actualizar el cliente creado anteriormente
    Given url urlBase + clientePath + '/' + '/1'
    And request clienteActualizado
    When method put
    Then status 200
    And match response.nombre == 'Jane Doe'
    And match response.genero == 'Female'
    And match response.identificacion == '987654321'

  Scenario: Eliminar un cliente por ID
    # Eliminar el cliente creado anteriormente
    Given url urlBase + clientePath + '/' + '/1'
    When method delete
    Then status 204
