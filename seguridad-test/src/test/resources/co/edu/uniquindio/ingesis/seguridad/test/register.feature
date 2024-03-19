Feature: Yo como usuario
  quiero poder registrarme en el sistema
  para poder hacer uso de las funcionalidades.

  Scenario: Registro exitoso
    Given Soy un usuario no registrado
    And proporciono los datos de registro
    When invoco el servicio de registro
    Then obtengo un status code 200
    And mis datos registrados

  Scenario Outline: Validación de campos requeridos en el registro
    Given Soy un usuario no registrado
    And proporciono los datos de registro omitiendo el "<campo>"
    When invoco el servicio de registro
    Then obtengo un status code 400
    And un mensaje que indica que el "<mensaje>"
    Examples:
      | campo    | mensaje                              |
      |  usuario | El nombre de usuario es obligatorio. |
      |  clave   | La clave es obligatoria.             |
