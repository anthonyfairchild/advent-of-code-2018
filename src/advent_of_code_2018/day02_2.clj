(ns advent-of-code-2018.day02-2)

(defn diff-strs
  [str1 str2]
  (->> (map vector str1 str2)
       (reduce (fn [diffs [a b]]
                 (+ diffs (if (= a b) 0 1)))
               0)))

(defn unique-pairs
  [cs]
  (->> cs
       (map-indexed vector)
       (mapcat (fn [[i c]]
                 (for [x (drop (inc i) cs)]
                   [c x])))))

(defn solve
  []
  (->> "input/day02.txt"
       slurp
       clojure.string/split-lines
       unique-pairs
       (filter (fn [[a b]] (= (diff-strs a b) 1)))
       first
       (apply map vector)
       (filter (fn [[a b]] (= a b)))
       (map first)
       (apply str)))

#_(solve)
;; => "bqlporuexkwzyabnmgjqctvfs"
