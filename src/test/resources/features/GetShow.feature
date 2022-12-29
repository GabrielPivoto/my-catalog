Feature: List Tv Shows

  Scenario: Get all shows should return status 200 Ok
    Given no type provided
    When get shows
    Then should return list of shows
    And the status should be "200"
    
  Scenario: Get all movies should return status 200 Ok
    Given type "movie" is provided
    When get "movie"
    Then should return list of "movie"
    And the status should be "200"
    
  Scenario: Get all series should return status 200 Ok
    Given type "series" is provided
    When get "series"
    Then should return list of "series"
    And the status should be "200"
    
  Scenario: Get a show with valid id should return dto and status 200 ok
    Given id 1 is "valid"
    When get show by id 1
    Then the title should be "Game of Thrones"
    And the director should be "David Benioff, D.B. Weiss"
    And the status should be "200"
    
  Scenario: Get a show with invalid id should trow ShowNotFoundException
    Given id 2 is "invalid"
    When get show by id 2
    Then should throw "ShowNotFoundException"
    And the status should be "404"
    And the title should be "Show not found or doesn't exist"
