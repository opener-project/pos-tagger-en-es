require 'open3'
require 'stringio'
require 'nokogiri'

require File.expand_path('../../../../core/target/ehu-pos-1.0.jar', __FILE__)

# import 'java.io.InputStreamReader'
# import 'ixa.kaflib.KAFDocument'
# import 'ehu.pos.Resources'
# import 'ehu.lemmatize.MorfologikLemmatizer'
# import 'ehu.lemmatize.LemmatizerDispatcher'

require_relative 'en_es/version'
require_relative 'en_es/en_es'
require_relative 'en_es/en'
require_relative 'en_es/es'
require_relative 'en_es/nl'
require_relative 'en_es/it'
require_relative 'en_es/fr'
