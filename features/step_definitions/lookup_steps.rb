BASE_URL='http://build-box:8080/Scripturelookup'

Given /^scripturelookup is running$/ do
end

When /^I go to "(.*)"$/ do |page|
  @browser.open(BASE_URL + page)
end

And /^I type "(.*)" in "(.*)"$/ do |text, field_name|
  @browser.type field_name, text
end

And /^I click the "(.*)" button$/ do |button_text|
  @browser.click "//button[contains(., '#{button_text}')]"
end

And /^I wait for the page to load$/ do |text|
  @browser.wait_for_page_to_load
end

Then /^I should see "(.*)"$/ do |text|
  @browser.wait_for_text text, :timeout_in_seconds => 10
end

When /^I select "([^\"]*)" in "([^\"]*)"$/ do |value, field|
  @browser.select field, value
end
