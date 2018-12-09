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

(defn perform-work
  [graph active-steps]
  (let [;; decrement seconds for each active step
        graph (->> graph
                   (map (fn [[step {deps :deps seconds :seconds} :as row]]
                          (if (contains? active-steps step)
                            [step {:deps deps :seconds (dec seconds)}]
                            row)))
                   (into {}))
        ;; find all finished steps
        finished-steps (->> graph
                            (filter (fn [[step {seconds :seconds}]]
                                      (= seconds 0)))
                            (map first)
                            set)
        ;; remove finished steps from graph
        graph (->> graph
                   (filter (fn [[step _]]
                             (not (contains? finished-steps step))))
                   (into {}))
        ;; remove finished steps from dependencies
        graph (->> graph
                   (map #(update-in % [1 :deps] clojure.set/difference finished-steps))
                   (into {}))]
    graph))

(defn available
  [num-workers graph]
  (->> graph
       (filter (fn [[step {deps :deps}]]
                 (empty? deps)))
       (take num-workers)
       (map first)
       set))

(defn solve
  []
  (let [duration-offset 60
        num-workers 5
        graph (parse-graph "input/day07.txt" duration-offset)]
    (loop [graph graph
           second 0]
      (let [available (available num-workers graph)]
        (if (empty? graph)
          second
          (do
            (recur (perform-work graph available)
                  (inc second))))))))

#_(solve)
;; => 987
