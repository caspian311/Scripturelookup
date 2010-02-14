require 'spec/expectations'
require 'selenium'
require 'selenium/client'

Before do
  @browser = Selenium::Client::Driver.new( 
    :host => "build-box",
    :port => 4444,
    :browser => "*firefox",
    :url => "http://www.google.com",
    :timeout_in_second => 60)
  @browser.start
end

After do
  @browser.stop
end

at_exit do
  @browser.close rescue nil
end
