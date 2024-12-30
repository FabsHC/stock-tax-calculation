(defproject stock-tax-calculation "0.1.0-SNAPSHOT"
  :description "Stocks tax calculation app"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/test.check "1.1.1"]]
  :repl-options {:init-ns stocks.core}
  :source-paths ["src"]
  :test-paths ["test"])
