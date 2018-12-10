(ns advent-of-code-2018.day09-2
  (:require [advent-of-code-2018.day09-1 :as p1]))

(defn solve
  []
  (let [[players max-marble] (p1/parse-input "input/day09.txt")]
    (p1/solve players (* 100 max-marble))))

#_(solve)
;; => 2945918550
