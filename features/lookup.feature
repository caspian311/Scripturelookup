Feature: Lookup Service
  Scenario: Show home page
    Given scripturelookup is running
    When I go to "/"
    Then I should see "Scripture Lookup"

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
    Then I should see "in the beginning God created"
