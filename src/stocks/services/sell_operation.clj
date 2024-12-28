(ns stocks.services.sell-operation
  (:require [stocks.models.input :as input]
            [stocks.models.stocks-info :as stock]
            [stocks.models.profit :as profit]
            [stocks.util.math :as math]))

(defprotocol SellOperation
  (execute [this stocks-info profit operation]))

(defrecord SellOperationImpl []
  SellOperation
  (execute [_ stocks-info profit operation]
    (let [unit-cost-total (input/get-total-cost operation (:unit-cost operation))
          avg-price-total (input/get-total-cost operation (:average-price stocks-info))
          updated-stocks-info (stock/subtract-shares stocks-info operation)
          updated-profit profit]
      (cond
        (= (:unit-cost operation) (:average-price updated-stocks-info))
        (do
          (assoc updated-profit :gains math/ZERO)
          [updated-stocks-info updated-profit])

        (< (:unit-cost operation) (:average-price updated-stocks-info))
        (do
          (profit/add-losses updated-profit (- avg-price-total unit-cost-total))
          (assoc updated-profit :gains math/ZERO)
          [updated-stocks-info updated-profit])

        :else
        (do
          (assoc updated-profit :gains (- unit-cost-total avg-price-total))
          (profit/process-losses updated-profit)
          [updated-stocks-info updated-profit])))))

(defn new-sell-operation []
  (->SellOperationImpl))
