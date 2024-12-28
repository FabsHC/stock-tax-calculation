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
    (let [unit-cost-total (input/get-total-cost (:unit-cost operation) (:quantity operation))
          avg-price-total (input/get-total-cost (:average-price stocks-info) (:quantity operation))
          updated-stocks-info (stock/subtract-shares stocks-info (:quantity operation))]
      (cond
        (= (:unit-cost operation) (:average-price updated-stocks-info))
        (do
          (reset! (:gains profit) math/ZERO)
          updated-stocks-info)

        (< (:unit-cost operation) (:average-price updated-stocks-info))
        (do
          (profit/add-losses profit (- avg-price-total unit-cost-total))
          (reset! (:gains profit) math/ZERO)
          updated-stocks-info)

        :else
        (do
          (reset! (:gains profit) (- unit-cost-total avg-price-total))
          (profit/process-losses profit)
          updated-stocks-info)))))

(defn new-sell-operation []
  (->SellOperationImpl))
