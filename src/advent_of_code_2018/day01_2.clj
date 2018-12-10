(ns advent-of-code-2018.day01-2)

(defn solve
  []
  (->> "input/day01.txt"
       slurp
       clojure.string/split-lines
       (map #(Integer/parseInt %))
       repeat
       flatten
       (reduce (fn [[freqs sum] n]
                 (let [sum (+ sum n)]
                   (if (get freqs sum)
                     (reduced sum)
                     [(conj freqs sum) sum])))
               [#{} 0])))

#_(solve)
;; => 367
