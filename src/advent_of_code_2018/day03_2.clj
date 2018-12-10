(ns advent-of-code-2018.day03-2)

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
              (assoc m [x y] (conj (get m [x y] [])
                                   id))))
          m
          (for [x (range width)
                y (range height)]
            [x y])))

(def input (->> "input/day03.txt"
                slurp
                clojure.string/split-lines
                parse-input))

(def all-claim-ids (->> input
                        (map first)
                        set))

(defn remove-overlapping-claim
  [claims cell]
  (if (= 1 (count cell))
    claims
    (clojure.set/difference claims cell)))

(defn solve
  []
  (->> input
       (reduce plot-claim {})
       (map second)
       (map set)
       (reduce remove-overlapping-claim
               all-claim-ids)
       first))

#_(solve)
;; => 415
