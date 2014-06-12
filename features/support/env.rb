require_relative '../../lib/opener/pos_taggers/en'
require 'rspec'

def kernel
  Opener::POSTaggers::EN.new
end

RSpec.configure do |config|
  config.expect_with :rspec do |c|
    c.syntax = [:should, :expect]
  end

  config.mock_with :rspec do |c|
    c.syntax = [:should, :expect]
  end
end
