Given /^the fixture file "(.*?)"$/ do |filename|
  @input = File.read(fixture_file(filename))
  @filename = filename
end

Given /^I put them through the kernel$/ do
  @output = kernel.run(@input)
end

Then /^the output should match the fixture "(.*?)"$/ do |filename|
  fixture_output = File.read(fixture_file(filename))
  @output.should eql(fixture_output)
end

def fixture_file(filename)
  File.expand_path("../../fixtures/#{filename}", __FILE__)
end
