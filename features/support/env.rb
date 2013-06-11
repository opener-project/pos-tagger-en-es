require_relative '../../lib/opener/pos_taggers/en'
require 'tempfile'

def kernel_root
  File.expand_path("../../../", __FILE__)
end

def kernel
  Opener::POSTaggers::EN.new
end

