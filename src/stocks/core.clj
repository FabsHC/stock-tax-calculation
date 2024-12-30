(ns stocks.core
  (:require [clojure.data.json :as json]
            [clojure.java.io :as io]
            [stocks.services.tax-calculation :as tax]
            [stocks.services.buy-operation :as buy]
            [stocks.services.sell-operation :as sell]
            ))

(defn process-data
  [operations]
  (let [
        buy-operation (buy/new-buy-operation)
        sell-operation (sell/new-sell-operation)
        tax-calculation (tax/new-tax-calculation buy-operation sell-operation)]
    (tax/execute tax-calculation operations)
    ))

(defn -main
  [& args]
  (let [input (slurp (io/reader *in*))
        operations (json/read-str input :key-fn keyword)
        result (process-data operations)
        output (json/write-str result)]
    (println output)))