require 'bundler/gem_tasks'
require 'rake/clean'

CLEAN.include('tmp/*', 'pkg')

Dir.glob(File.expand_path('../task/*.rake', __FILE__)) do |task|
  import(task)
end

task :clean   => 'java:clean'
task :build   => :compile
task :default => :test
