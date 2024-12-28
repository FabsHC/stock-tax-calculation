(ns stocks.models.output
  (:require [stocks.util.math :as math]))

(defrecord Output [tax])

(defn new-operation-output [tax] (->Output tax))

(defn new-capital-gain-output [gains]
  (let [tax (* gains 0.2)
        formatted-tax (math/round-two-decimals tax)]
    (new-operation-output formatted-tax)
    ))