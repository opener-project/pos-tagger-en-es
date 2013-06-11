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

              @kernel      = core_dir+'/ehu-pos-1.0.jar'
            end

            def command(opts={})
              arguments = opts[:arguments] || []
              arguments << "-t" if opts[:test]


		"cat #{opts[:input]} | java -jar #{kernel} -l en #{arguments.join(' ')}"

            end

          end
        end
    end
  end
end

require_relative 'en/version'

module Opener
  module POSTaggers
    class EN
      attr_reader :kernel, :lib

      def command(opts=[])
        "java -jar #{kernel} -l en #{opts.join(' ')}"
      end
      
      def run(opts=ARGV)
        `#{command(opts)}`
      end
      
      protected
      
      def core_dir
        File.expand_path("../../../core", File.dirname(__FILE__))
      end
      
      def kernel
        core_dir+'/ehu-pos-1.0.jar'
      end      
      
    end
  end
end






