Feature: Lookup Service
  Scenario: Show home page
    Given scripturelookup is running
    When I go to "/"
    Then I should see "Scripture Lookup"

  Scenario: Show dataloading page
    Given scripturelookup is running
    When I go to "/Dataloading.html"
    Then I should see "Data Management"

  Scenario: Delete all existing Bible verse data
    Given scripturelookup is running
    When I go to "/Dataloading.html"
    And I click the "Delete" button
    Then I should see "Data has deleted successfully."

  Scenario: Reload all Bible verse data
    Given scripturelookup is running
    When I go to "/Dataloading.html"
    And I click the "Reload" button
    Then I should see "Data has reloaded successfully."

  Scenario: Gen 1:1 by keyword
    Given scripturelookup is running
    When I go to "/"
    And I type "in the beginning God created" in "query"
    And I select "By Keyword" in "queryType"
    And I click the "Search" button
    Then I should see "Genesis 1:1"

  Scenario: no results for nonsense keywords
    Given scripturelookup is running
    When I go to "/"
    And I type "blah blah blah" in "query"
    And I select "By Keyword" in "queryType"
    And I click the "Search" button
    Then I should see "No results found"

  Scenario: Gen 1:1 by reference
    Given scripturelookup is running
    When I go to "/"
    And I type "Genesis 1:1" in "query"
    And I select "By Reference" in "queryType"
    And I click the "Search" button
    Then I should see "In the beginning God created"

  Scenario: John 3 by reference
    Given scripturelookup is running
    When I go to "/"
    And I type "John 3" in "query"
    And I select "By Reference" in "queryType"
    And I click the "Search" button
    Then I should see "John 3:1"
    And I should see "John 3:2"
    And I should see "John 3:16"

  Scenario: Acts by reference
    Given scripturelookup is running
    When I go to "/"
    And I type "Acts" in "query"
    And I select "By Reference" in "queryType"
    And I click the "Search" button
    Then I should see "Acts 1:1"
