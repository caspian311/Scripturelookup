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
  
  Scenario: Show busy signal when searching
    Given scripturelookup is running
    When I go to "/"
    And I type "Acts" in "query"
    And I select "By Reference" in "queryType"
    And I click the "Search" button
    Then I should see "Searching Scripture..."

  Scenario: Hide busy signal when results show
    Given scripturelookup is running
    When I go to "/"
    And I type "Acts" in "query"
    And I select "By Reference" in "queryType"
    And I click the "Search" button
    Then I should see "Acts 1:1"
    And I should not see "Searching Scripture..."

  Scenario: Hide old results
    Given scripturelookup is running
    When I go to "/"
    And I type "Psalms 110" in "query"
    And I select "By Reference" in "queryType"
    And I click the "Search" button
    Then I should see "Psalms 110:7"
    And I type "John 3:16" in "query"
    And I select "By Reference" in "queryType"
    And I click the "Search" button
    And I should not see "Psalms 110:7"
    
    
  Scenario: Ranged chapters
    Given scripturelookup is running
    When I go to "/"
    And I type "Psalms 110-111" in "query"
    And I select "By Reference" in "queryType"
    And I click the "Search" button
    Then I should see "Psalms 110:1"
    And I should see "Psalms 110:2"
    And I should see "Psalms 110:3"
    And I should see "Psalms 110:4"
    And I should see "Psalms 110:5"
    And I should see "Psalms 110:6"
    And I should see "Psalms 110:7"
    And I should see "Psalms 111:1"
    And I should see "Psalms 111:2"
    And I should see "Psalms 111:3"
    And I should see "Psalms 111:4"
    And I should see "Psalms 111:5"
    And I should see "Psalms 111:6"
    And I should see "Psalms 111:7"
    And I should see "Psalms 111:8"
    And I should see "Psalms 111:9"
    And I should see "Psalms 111:10"

  Scenario: Ranged verses
    Given scripturelookup is running
    When I go to "/"
    And I type "John 3:16-18" in "query"
    And I select "By Reference" in "queryType"
    And I click the "Search" button
	Then I should see "John 3:16"
    Then I should see "John 3:17"
    Then I should see "John 3:18"
