(ns advent-of-code-2018.day07-1
  (:require [advent-of-code-2018.utils :as u]
            [clojure.string :as str]))

(defn parse-graph
  [file]
  (->> file
       slurp
       str/split-lines
       (map #(re-find #"^Step (.) must be finished before step (.) can begin.$" %))
       (map #(drop 1 %))
       (reduce (fn [acc [a b]]
                 (-> acc
                     (update b (fnil conj #{}) a)
                     (update a (fnil conj #{}))))
               {})))

(defn solve
  []
  (let [graph (parse-graph "input/day07.txt")]
    (-> (loop [graph graph
               acc []]
          (if (empty? graph)
            acc
            (let [next (->> graph
                            (filter #(empty? (second %)))
                            (map first)
                            sort
                            first)
                  graph (->> (dissoc graph next)
                             (map (fn [[k v]]
                                    [k (disj v next)]))
                             (into {}))]
              (recur graph (conj acc next)))))
        str/join)))

#_(solve)
;; => "BCEFLDMQTXHZGKIASVJYORPUWN"
