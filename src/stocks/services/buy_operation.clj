(ns stocks.services.buy-operation
  (:require [stocks.models.stocks-info :as models]))

(defprotocol BuyOperation
  (execute [this stocks-info operation]))

(defrecord BuyOperationImpl []
  BuyOperation
  (execute [_ stocks-info operation]
    (let [
          updated-stocks-info (models/calculate-new-average-share-price stocks-info operation)
          updated-stocks-info (models/add-shares updated-stocks-info operation)]
      [updated-stocks-info operation])))

(defn new-buy-operation []
  (->BuyOperationImpl))