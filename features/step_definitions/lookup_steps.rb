Given /^scripturelookup is running$/ do
  BASE_URL='http://localhost:8080/Scripturelookup'
end

When /^I go to "([^\"]*)"$/ do |page|
  @browser.open(BASE_URL + page)
end

And /^I type in "([^\"]*)"$/ do |arg1|
  @broswer.type 'query', arg1
end

And /^I click "([^\"]*)"$/ do |arg1|
  @broswer.click arg1
end

Then /^I should see "([^\"]*)"$/ do |text|
  @browser.wait_for_page_to_load
  @browser.is_text_present(text).should be_true
end
