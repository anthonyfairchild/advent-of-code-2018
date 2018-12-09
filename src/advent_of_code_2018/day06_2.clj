(ns advent-of-code-2018.day06-2
  (:require [advent-of-code-2018.utils :as u]
            [advent-of-code-2018.day06-1 :as p1]))

(defn solve
  []
  (let [coords (->> "input/day06.txt"
                    p1/parse-coords)
        max-distance 10000
        [max-x max-y min-x min-y :as bounds] (p1/bounds coords)]
    (reduce + (for [x (range min-x (inc max-x))
                    y (range min-y (inc max-y))
                    :when (< (reduce + (map (fn [[cx cy]]
                                              (p1/distance [cx cy] [x y]))
                                            coords))
                             max-distance)]
                1))))

#_(solve)
;; => 37318
