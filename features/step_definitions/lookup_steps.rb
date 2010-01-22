require 'webrat'

Given /^scripturelookup is running$/ do
end

When /^I go to "([^\"]*)"$/ do |page|
  visit 'http://localhost:8080' + page
end

And /^I type in "([^\"]*)"$/ do |arg1|
  fill_in 'query', :with => arg1
end

And /^I click "([^\"]*)"$/ do |arg1|
  clicks_button arg1
end

Then /^I should see "([^\"]*)"$/ do |text|
  assert_contain(text)
end
