require 'tempfile'

module Opener
  module Kernel
    module EHU
        module POSTagger
          class EN 
            VERSION = "0.0.2"

            attr_reader :kernel, :lib

            def initialize
              core_dir    = File.expand_path("../core", File.dirname(__FILE__))

              @kernel      = core_dir+'/ehu-opennlp-pos-en-1.0.jar'
            end

            def command(opts={})
              arguments = opts[:arguments] || []
              arguments << "-n" if opts[:test]

              "cat #{opts[:input]} | java -jar #{kernel} -l en"
              #"cat #{opts[:input]} | java -jar #{kernel} #{opts[:input]} #{arguments.join(' ')}"

            end

          end
        end
    end
  end
end


