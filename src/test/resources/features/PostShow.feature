Feature: Add a new show

  Scenario:  Add a new show with personal score of 7 and valid title should return status 200 Ok
    Given "Pacific Rim" is not yet registered
    Given "Pacific Rim" is provided as title
    And 7 is provided as personal score
    When post a new show with the "form"
    Then the status should be "201"
    And the title should be "Pacific Rim"
    And the director should be "Guillermo del Toro"
    And the type should be "movie"

  Scenario:  Add a new show with personal score higher than 10 should throw MethodArgumentNotValidException
    Given "Pacific Rim" is provided as title
    And 11 is provided as personal score
    When post a new show with the "form"
    Then should throw "MethodArgumentNotValidException"
    And the status should be "400"
    And the detail should be "Personal score must be less than or equal to 10"
    And the title should be "Invalid personal score"

  Scenario:  Add a new show with personal score lower than 0 should throw MethodArgumentNotValidException
    Given "Pacific Rim" is provided as title
    And -1 is provided as personal score
    When post a new show with the "form"
    Then should throw "MethodArgumentNotValidException"
    And the status should be "400"
    And the detail should be "Personal score must be greater than or equal to 0"
    And the title should be "Invalid personal score"

  Scenario:  Add a new show with invalid personal score should throw MessageNotReadableException
    Given "dog" is provided as personal score
    When post a new show with the "testform"
    Then should throw "MessageNotReadableException"
    And the status should be "400"
    And the detail should be "Personal score must be a double"
    And the title should be "Invalid personal score"

  Scenario: Add a new show with invalid title should throw ShowNotFoundException
    Given "Robin Hoodson" is an invalid title
    And "Robin Hoodson" is provided as title
    When post a new show with the "form"
    Then should throw "ShowNotFoundException"
    And the status should be "404"
    And the title should be "Show not found or doesn't exist"

  Scenario: Add a new show with already registered title should throw ShowAlreadyRegisteredException
    Given "Game of Thrones" is already registered
    And "Game of Thrones" is provided as title
    When post a new show with the "form"
    Then should throw "ShowAlreadyRegisteredException"
    And the status should be "400"
    And the title should be "Show already registered."