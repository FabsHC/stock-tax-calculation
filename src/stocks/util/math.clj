(ns stocks.util.math)

(defn average-price
  "Returns average price"
  [[totalStocks stocksPurchased actualAveragePrice purchasePrice]]
  (/
    (+ (* totalStocks actualAveragePrice) (* stocksPurchased purchasePrice))
    (+ totalStocks stocksPurchased)))

(defn round-two-decimals
  "round value up to two decimals"
  [value]
  (let [ratio (Math/pow 10 2)]
    (/ (Math/round (* value ratio)) ratio)))