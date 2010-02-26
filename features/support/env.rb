require 'spec/expectations'
require 'selenium'
require 'selenium/client'

BASE_URL='http://localhost:8888'
TIMEOUT=120

Before do
  @browser = Selenium::Client::Driver.new( 
    :host => "localhost",
    :port => 4444,
    :browser => "*firefox",
    :url => "http://www.google.com",
    :timeout_in_second => TIMEOUT)
  @browser.start
end

After do
  @browser.stop
end

at_exit do
  @browser.close rescue nil
end
