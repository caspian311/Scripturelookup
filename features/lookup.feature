Feature: Lookup Service
  Scenario: Show home page
    Given scripturelookup is running
    When I go to "/"
    Then I should see "Scripture Lookup"

  Scenario: John 3:16
    Given scripturelookup is running
    When I go to "/"
    And I type "God love world" in "query"
    And I click the search button
    Then I should see "John 3:16"
