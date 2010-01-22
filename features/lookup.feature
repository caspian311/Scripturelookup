Feature: Lookup Service
  Scenario: Show home page
    Given scripturelookup is running
    When I go to "/"
    Then I should see "Scripture Lookup"

#  Scenario: John 3:16
#    Given scripturelookup is running
#    When I go to "/"
#    And I type in "God love world"
#    And I click "search"
#    Then I should see "John 3:16"
