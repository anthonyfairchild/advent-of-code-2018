(ns advent-of-code-2018.day07-2
  (:require [advent-of-code-2018.day07-1 :as p1]))

(defn parse-graph
  [file duration-offset]
  (->> file
       p1/parse-graph
       (map (fn [[k v]]
              [k {:deps v
                  :seconds (+ (- (int (first k))
                                 (int \A))
                              1
                              duration-offset)}]))
       (into {})))

(defn solve
  []
  (let [duration-offset 0
        workers 2
        graph (parse-graph "input/day07-test.txt" duration-offset)]
    graph))

#_(solve)
