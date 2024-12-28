(ns stocks.models.profit
  (:require [stocks.util.math :as math]))

(defrecord Profit [losses gains])

(defn new-profit []
  (->Profit math/ZERO math/ZERO))

(defn add-losses [profit loss]
  (assoc profit :losses (+ (:losses profit) loss)))

(defn process-losses [profit]
  (let [gains (:gains profit)
        losses (:losses profit)
        updated-profit
        (cond
          (> gains losses)
          (-> profit
              (assoc :gains (- gains losses))
              (assoc :losses math/ZERO))

          :else
          (-> profit
              (assoc :losses (- losses gains))
              (assoc :gains math/ZERO)))]
    updated-profit))
