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
          updated-stocks-info (stock/subtract-shares stocks-info operation)]
      (cond
        (= (:unit-cost operation) (:average-price updated-stocks-info))
        (do
          (let [updated-profit (assoc profit :gains math/ZERO)] 
            [updated-stocks-info updated-profit]))
        
        (< (:unit-cost operation) (:average-price updated-stocks-info))
        (do
          (let [updated-profit (profit/add-losses profit (- avg-price-total unit-cost-total))
                updated-profit (assoc updated-profit :gains math/ZERO)]
            [updated-stocks-info updated-profit]))
        :else
        (do
          (let [updated-profit (assoc profit :gains (- unit-cost-total avg-price-total))
                updated-profit (profit/process-losses updated-profit)]
            [updated-stocks-info updated-profit]
            ))))))


(defn new-sell-operation []
  (->SellOperationImpl))