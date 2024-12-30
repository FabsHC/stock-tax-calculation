(ns stocks.core
  (:require [stocks.models.stocks-info :as models]
            [stocks.models.input :as input]
            [stocks.models.profit :as profit]
            [stocks.services.buy-operation :as buy]
            [stocks.services.sell-operation :as sell]
            [stocks.handlers.tax-calculation :as tax]))


(def bo (buy/new-buy-operation))
(def se (sell/new-sell-operation))
(def tx (tax/new-tax-calculation bo se))

(def operations
  [{:operation input/BUY_OPERATION, :unit-cost 10.00, :quantity 10000}
   {:operation input/SELL_OPERATION, :unit-cost 20.00, :quantity 5000}
   {:operation input/SELL_OPERATION, :unit-cost 5.00, :quantity 5000}])

(println "Input:\n" operations)
(def result (tax/execute tx operations))
(println "\nOutput:\n" result)
