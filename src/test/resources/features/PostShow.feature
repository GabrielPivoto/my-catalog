Feature: Add a new show

  Scenario:  Add a new show with personal score of 7 and valid title should return status 200 Ok
    Given "Pacific Rim" is provided as title
    And 7 is provided as personal score
    When post a new show with the "form"
    Then the status should be "CREATED"

  Scenario:  Add a new show with personal score higher than 10 should throw MethodArgumentNotValidException
    Given 11 is provided as personal score
    When post a new show with the "form"
    Then should throw "MethodArgumentNotValidException"
    And the status should be "BAD_REQUEST"

  Scenario:  Add a new show with personal score lower than 0 should throw MethodArgumentNotValidException
    Given -1 is provided as personal score
    When post a new show with the "form"
    Then should throw "MethodArgumentNotValidException"
    And the status should be "BAD_REQUEST"

  Scenario:  Add a new show with invalid personal score should throw MessageNotReadableException
    Given "dog" is provided as personal score
    When post a new show with the "testform"
    Then should throw "MessageNotReadableException"
    And the status should be "BAD_REQUEST"

  Scenario: Add a new show with invalid title should throw ShowNotFoundException
    Given "Robin Hoodson" is an invalid title
    And "Robin Hoodson" is provided as title
    When post a new show with the "form"
    Then should throw "ShowNotFoundException"
    And the status should be "NOT_FOUND"

  Scenario: Add a new show with already registered title should throw ShowAlreadyRegisteredException
    Given "Game of Thrones" is already registered
    And "Game of Thrones" is provided as title
    When post a new show with the "form"
    Then should throw "ShowAlreadyRegisteredException"
    And the status should be "BAD_REQUEST"