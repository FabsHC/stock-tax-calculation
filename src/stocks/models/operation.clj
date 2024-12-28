(ns stocks.models.operation
  (:require [stocks.util.math :as math]))

(defrecord StocksInfo [average-price shares])

(defn new-stocks-info [] (->StocksInfo math/ZERO math/ZERO))

(defn add-shares
  "Adds an amount X to the total number of shares"
  [stocks-info quantity]
  (assoc stocks-info :shares (+ (:shares stocks-info) quantity)))

(defn subtract-shares
  "Subtracts an amount X to the total number of shares"
  [stocks-info quantity]
  (let [total (- (:shares stocks-info) quantity)]
    (if (< total math/ZERO)
      (assoc stocks-info :shares math/ZERO)
      (assoc stocks-info :shares total)
      )))

(defn calculate-new-average-share-price [stocks-info operation]
  "Calculate the new average price base in stocks information and the operation that is being executed"
  (assoc stocks-info
    :average-price
    (math/average-price
      (:shares stocks-info)
      (:quantity operation)
      (:average-price stocks-info)
      (:unit-cost operation))
    ))