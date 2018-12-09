(ns advent-of-code-2018.core
  (:require [advent-of-code-2018.day04-1 :as d4-1]
            [advent-of-code-2018.day05-1 :as d5-1] [advent-of-code-2018.day05-2 :as d5-2]
            [advent-of-code-2018.day06-1 :as d6-1] [advent-of-code-2018.day06-2 :as d6-2]
            [advent-of-code-2018.day07-1 :as d7-1] [advent-of-code-2018.day07-2 :as d7-2]))

(def problem-fns-and-answers
  [[d4-1/solve 87681]
   [d5-1/solve 9202] [d5-2/solve 6394]
   [d6-1/solve 4290] [d6-2/solve 37318]
   [d7-1/solve "BCEFLDMQTXHZGKIASVJYORPUWN"] [d7-2/solve 987]])

(defn test-all
  []
  (->> problem-fns-and-answers
       (pmap (fn [[fun answer]]
               (assert (= (fun) answer))))
       doall)
  (println "Success!"))

#_(test-all)
