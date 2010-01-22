require 'webrat'

Webrat.configure do |config|
  config.mode = :mechanize
  config.application_environment = :external
  config.application_address = "http://localhost:8080"
end

World do
  include Webrat::Methods
  include Webrat::Matchers
end
