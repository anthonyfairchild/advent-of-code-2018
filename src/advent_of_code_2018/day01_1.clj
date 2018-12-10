(ns advent-of-code-2018.day01-1)

(defn solve
  []
  (->> "input/day01.txt"
       slurp
       clojure.string/split-lines
       (map #(Integer/parseInt %))
       (reduce +)))

#_(solve)
;; => 484
