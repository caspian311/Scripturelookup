Feature: Lookup Service
  Scenario: Show home page
    Given scripturelookup is running
    When I go to "/"
    Then I should see "Scripture Lookup"

  Scenario: Gen 1:1
    Given scripturelookup is running
    When I go to "/"
    And I type "in the beginning" in "query"
    And I click the search button
    Then I should see "Genesis 1:1"

  Scenario: no results
    Given scripturelookup is running
    When I go to "/"
    And I type "blah blah blah" in "query"
    And I click the search button
    Then I should see "No results found"
