require 'bundler/gem_tasks'
require 'opener/build-tools/tasks/java'

CORE_DIRECTORY = File.expand_path('../core', __FILE__)

desc 'Runs the tests'
task :test => 'java:compile' do
  sh 'cucumber features'
end

desc 'Cleans the repository'
task :clean => ['java:clean:packages']

task :build   => 'java:compile'
task :default => :test
