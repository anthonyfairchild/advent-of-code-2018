(ns advent-of-code-2018.day05-2
  (:require [advent-of-code-2018.day05-1 :as p1]
            [clojure.string :as str]))

(def all-units (->> (range 26)
                    (map #(+ (int \a) %))
                    (map char)
                    set))

(defn solve
  []
  (let [polymer (->> "input/day05.txt"
                     slurp
                     str/trim)]
    (->> all-units
         (pmap (fn [unit]
                [unit (->> polymer
                           (remove #(= unit %))
                           (remove #(= (Character/toUpperCase unit) %))
                           p1/single-pass-with-backtrack
                           count)]))
         (apply min-key second)
         second)))

#_(solve)
;; => 6394
