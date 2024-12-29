(ns stocks.handlers.tax-calculation
  (:require [stocks.models.input :as input]
            [stocks.models.output :as output]
            [stocks.models.stocks-info :as stocks-info]
            [stocks.models.profit :as profit]
            [stocks.util.math :as math]
            [stocks.services.buy-operation :as buy]
            [stocks.services.sell-operation :as sell]))

(defprotocol TaxCalculation
  (execute [this operations]))

(defn get-sales-gains [profits operation]
  (let [total-cost (* (:quantity operation) (:unit-cost operation))]
    (if (<= total-cost 20000)
      math/ZERO
      (:gains profits))))

(defrecord TaxCalculationImpl [buy-operation sell-operation]
  TaxCalculation
  (execute [_ operations]
    (let [stocks (atom (stocks-info/new-stocks-info))
          profits (atom (profit/new-profit))]
      ;(println "------------------------------------------")
      (reduce
        (fn [taxes operation]
          ;(println "Operation" operation)
          (let [operation-type (:operation operation)
                tax (cond
                      (= operation-type input/BUY_OPERATION)
                      (do
                        (let [updated-stocks (buy/execute buy-operation @stocks operation)]
                          (reset! stocks updated-stocks))
                        ;(println "\nBuy operation, stocks" @stocks)
                        (output/new-capital-gain-output math/ZERO))

                      (= operation-type input/SELL_OPERATION)
                      (do
                        (let [[updated-stocks updated-profit] (sell/execute sell-operation @stocks @profits operation)]
                          (reset! stocks updated-stocks)
                          (reset! profits updated-profit))
                        ;(println "\nSell operation, stocks" @stocks)
                        ;(println "\nSell operation, profits" @profits)
                        (output/new-capital-gain-output (get-sales-gains @profits operation)))

                      :else
                      (throw (IllegalArgumentException. (str "Unknown operation: " operation-type))))]
            ;(println "\nTax" tax)
            ;(println "------------------------------------------")
            (conj taxes tax)
          )
        )
        []
        operations
      )
    )
  )
)


(defn new-tax-calculation [buy-operation sell-operation]
  (->TaxCalculationImpl buy-operation sell-operation))