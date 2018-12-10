(ns advent-of-code-2018.day03-1)

(defn parse-input
  [line]
  (->> line
       (map #(re-find #"^#(\d+) @ (\d+),(\d+): (\d+)x(\d+)$" %))
       (map #(drop 1 %))
       (map (fn [cs] (map #(Integer/parseInt %) cs)))
       (map vec)))

(defn plot-claim
  [m [id left top width height]]
  (reduce (fn [m [x y]]
            (let [x (+ left x)
                  y (+ top y)]
              (assoc m [x y] (inc (get m [x y] 0)))))
          m
          (for [x (range width)
                y (range height)]
            [x y])))

(defn solve
  []
  (->> "input/day03.txt"
       slurp
       clojure.string/split-lines
       parse-input
       (reduce plot-claim {})
       (map second)
       (filter #(> % 1))
       count))

#_(solve)
;; => 100595
