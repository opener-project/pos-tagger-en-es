require "bundler/gem_tasks"

CORE = File.expand_path('../core', __FILE__)

desc 'Removes all built files'
task :clean do
  Dir.chdir(CORE) do
    sh 'mvn clean'
  end
end

desc 'Generates JAR files'
task :generate => :clean do
  Dir.chdir(CORE) do
    sh "mvn package"
  end
end

desc 'Runs the tests'
task :test => :generate do
  sh 'cucumber features'
end

task :build   => :generate
task :default => :test