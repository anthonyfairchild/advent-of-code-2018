(ns advent-of-code-2018.day02-1)

(defn twos-and-threes
  [str]
  (let [counts (->> str
                    (group-by identity)
                    (map (fn [[_ cs]] (count cs)))
                    set)]
    [(if (> (get counts 2 0) 0) 1 0)
     (if (> (get counts 3 0) 0) 1 0)]))

(defn solve
  []
  (->> "input/day02.txt"
       slurp
       clojure.string/split-lines
       (map twos-and-threes)
       (reduce (fn [[twos threes] [tws ths]]
                 [(+ twos tws) (+ threes ths)])
               [0 0])
       (apply *)))

#_(solve)
;; => 5928
