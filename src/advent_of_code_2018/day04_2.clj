(ns advent-of-code-2018.day04-2
  (:require [advent-of-code-2018.utils :as u]
            [advent-of-code-2018.day04-1 :as p1]))

(defn solve
  []
  (let [{:keys [guard best-minute] :as a}
        (->> "input/day04-test.txt"
             u/file-lines
             p1/parse-data
             (group-by :guard)
             (map (fn [[guard minutes]]
                    {:guard guard
                     :best-minute (->> minutes
                                       (group-by :sleep-minute)
                                       (map (fn [[minute recs]]
                                              {:minute minute :count (count recs)}))
                                       (apply max-key :count)
                                       :minute)}))
             (apply max-key :best-minute))]
    (* guard best-minute)
    ))

#_(solve)

;; Wrong: => 73451
