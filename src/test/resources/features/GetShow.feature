Feature: List Tv Shows

  Scenario: Get all shows
    Given no type provided
    When get shows
    Then should return list of shows
    And the status should be "OK"
