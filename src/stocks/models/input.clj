(ns stocks.models.input
  (:require [stocks.util.math :as math]))

(defrecord OperationInput [operation unit-cost quantity])

(defn new-operation-input [] (->OperationInput nil, math/ZERO, math/ZERO))

(defn get-total-cost [input price]
  (* (:quantity input) price))

;; Operation Type constant
(def BUY_OPERATION "buy")
(def SELL_OPERATION "sell")