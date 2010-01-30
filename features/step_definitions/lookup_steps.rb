BASE_URL='http://localhost:8080/Scripturelookup'

Given /^scripturelookup is running$/ do
end

When /^I go to "(.*)"$/ do |page|
  @browser.open(BASE_URL + page)
end

And /^I type "(.*)" in "(.*)"$/ do |text, field_name|
  @browser.type field_name, text
end

And /^I click the search button$/ do
  @browser.click "//*[@id='submitButtonContainer']/button"
end

And /^I wait for the page to load$/ do |text|
  @browser.wait_for_page_to_load
end

Then /^I should see "(.*)"$/ do |text|
  @browser.wait_for_text text, :timeout_in_seconds => 10
end
