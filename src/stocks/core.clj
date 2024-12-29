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

;(def operations
;  [{:operation input/BUY_OPERATION, :unit-cost 10.00, :quantity 10000}
;   {:operation input/BUY_OPERATION, :unit-cost 20.00, :quantity 5000}
;   {:operation input/SELL_OPERATION, :unit-cost 5.00, :quantity 5000}])
;
;(println "Input:\n" operations)
;(def result (tax/execute tx operations))
;(println "Output:\n" result)


;(def stocks-info (models/new-stocks-info))
;(def operation (-> (input/new-operation-input)
;                   (assoc :operation input/BUY_OPERATION)
;                   (assoc :unit-cost 10)
;                   (assoc :quantity 10000)))
;
;(println "Initial operation:" operation)
;(println "Initial stocks-info:" stocks-info)
;(println "Execution buy operation")
;(let [updated-stocks-info (buy/execute bo stocks-info operation)]
;  (def stocks-info updated-stocks-info))
;(println "Updated stocks-info" stocks-info)
;
;(println "\nSecond operation")
;(def operation (-> (input/new-operation-input)
;                   (assoc :operation input/BUY_OPERATION)
;                   (assoc :unit-cost 25)
;                   (assoc :quantity 5000)))
;(println "Operation:" operation)
;(println "Execution buy operation")
;(let [updated-stocks-info (buy/execute bo stocks-info operation)]
;  (def stocks-info updated-stocks-info))
;(println "Updated stocks-info" stocks-info)
;
;(println "\nThird operation")
;(def operation (-> (input/new-operation-input)
;                   (assoc :operation input/SELL_OPERATION)
;                   (assoc :unit-cost 100)
;                   (assoc :quantity 10000)))
;(println "Operation:" operation)
;(println "Execution sell operation")
;(def prof (profit/new-profit))
;(let [[updated-stock-info updated-profit] (sell/execute se stocks-info prof operation)]
;  (def stocks-info updated-stock-info)
;  (def prof updated-profit))
;(println "Updated stocks-info" stocks-info)
;(println "Updated profit info" prof)
