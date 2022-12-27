Feature: Controller

  Scenario:  Personal score higher than 10 should throw MethodArgumentNotValidException
    Given 11 is provided as personal score
    When post a new show
    Then should throw MethodArgumentNotValidException

  Scenario:  Personal score lower than 0 should throw MethodArgumentNotValidException
    Given -1 is provided as personal score
    When post a new show
    Then should throw MethodArgumentNotValidException