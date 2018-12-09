(ns advent-of-code-2018.day08-1
  (require [clojure.string :as str]))

(defn parse-input
  [file]
  (-> file
      slurp
      (str/split #" ")
      (->> (map #(str/trim %))
           (map #(Integer/parseInt %)))))

(defn read-node [[n-children n-metadata & r]]
  (let [[children r] (reduce (fn [[children r] _]
                               (let [[node r] (read-node r)]
                                 [(conj children node) r]))
                             [[] r]
                             (range n-children))
        metadata (take n-metadata r)
        r (drop n-metadata r)]
    [{:children children :metadata metadata}
     r]))

(defn sum-metadata [{:keys [children metadata]}]
  (+ (apply + metadata)
     (apply + (map sum-metadata children))))

(defn solve
  []
  (->> "input/day08.txt"
       parse-input
       read-node
       first
       sum-metadata))

#_(solve)
;; => 48155
