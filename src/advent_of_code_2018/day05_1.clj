(ns advent-of-code-2018.day05-1
  (:require [advent-of-code-2018.utils :as u]
            [clojure.string :as str]))

(defn toggle-case
  [c]
  (if (Character/isUpperCase c)
    (Character/toLowerCase c)
    (Character/toUpperCase c)))

(defn single-pass-with-backtrack
  [s]
  (loop [acc '() [a b & r] s]
    (if a
      (if b
        (if (= a (toggle-case b))
          (if (not-empty acc)
            ;; drop and backtrack
            (let [p (first acc)
                  acc (rest acc)
                  r (conj r p)]
              (recur acc r))
            ;; drop and move forward
            (recur acc r))        
          (recur (conj acc a) (conj r b)))
        (reverse (conj acc a)))
      (reverse acc))))

(defn solve
  []
  (->> "input/day05.txt"
       slurp
       str/trim
       (into '())
       reverse
       single-pass-with-backtrack
       count))

#_(solve)
;; => 9202
