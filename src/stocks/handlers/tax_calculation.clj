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
  (let [total-cost (input/get-total-cost operation (:unit-cost operation))]
    (if (<= total-cost 20000)
      math/ZERO
      (:gains profits))))

(defrecord TaxCalculationImpl [buy-operation sell-operation]
  TaxCalculation
  (execute [_ operations]
    (let [stocks (stocks-info/new-stocks-info)
          profits (profit/new-profit)]
      (reduce
        (fn [output-taxes operation]
          (let [operation-type (:operation operation)
                tax (cond
                      (= operation-type input/BUY_OPERATION)
                      (do
                        (let [updated-stocks (buy/execute buy-operation stocks operation)]
                          (def stocks updated-stocks))
                        (output/new-capital-gain-output math/ZERO))

                      (= operation-type input/SELL_OPERATION)
                      (do
                        (let [[updated-stocks updated-profit] (sell/execute sell-operation stocks profits operation)]
                          (def stocks updated-stocks)
                          (def profits updated-profit))
                        (output/new-capital-gain-output (get-sales-gains profits operation)))

                      :else
                      (throw (IllegalArgumentException. (str "Unknown operation: " operation-type))))]
            (conj output-taxes tax)))
        []
        operations)
      )))


(defn new-tax-calculation [buy-operation sell-operation]
  (->TaxCalculationImpl buy-operation sell-operation))