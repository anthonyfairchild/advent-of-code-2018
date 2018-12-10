(ns advent-of-code-2018.day08-2
  (:require [advent-of-code-2018.day08-1 :as p1]))

(defn node-value
  [{:keys [children metadata]}]
  (if (empty? children)
    (reduce + metadata)
    (->> metadata
         (map (fn [m]
                (if-let [child (get children (dec m))]
                  (node-value child)
                  0)))
         (reduce +))))

(defn solve
  []
  (let [graph (->> "input/day08.txt"
                   p1/parse-input
                   p1/read-node
                   first)]
    (->> graph
         node-value)))

#_(solve)
;; => 40292
