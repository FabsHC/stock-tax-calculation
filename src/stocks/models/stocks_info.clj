(ns stocks.models.stocks-info
  (:require [stocks.util.math :as math]))

(defrecord StocksInfo [average-price shares])

(defn new-stocks-info [] (->StocksInfo math/ZERO math/ZERO))

(defn add-shares
  "Adds an amount X to the total number of shares"
  [stocks-info operation]
  (update stocks-info :shares + (:quantity operation)))

(defn subtract-shares
  "Subtracts an amount X to the total number of shares"
  [stocks-info operation]
  (let [total (- (:shares stocks-info) (:quantity operation))]
    (if (< total math/ZERO)
      (assoc stocks-info :shares math/ZERO)
      (assoc stocks-info :shares total)
      )))

(defn calculate-new-average-share-price [stocks-info operation]
  "Calculate the new average price base in stocks information and the operation that is being executed"
  (assoc stocks-info
    :average-price
    (math/round-two-decimals
      (math/average-price
        (:shares stocks-info)
        (:quantity operation)
        (:average-price stocks-info)
        (:unit-cost operation))
      )))