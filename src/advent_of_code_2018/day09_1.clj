(ns advent-of-code-2018.day09-1
  (:require [advent-of-code-2018.deque :as dq]))

(defn parse-input
  [file]
  (->> file
       slurp
       (re-find #"^(\d+) players; last marble is worth (\d+) points$")
       (drop 1)
       (map #(Integer/parseInt %))))

(defn place-marble
  [{:keys [marbles player-scores] :as state} marble player]
  (if (= 0 (mod marble 23))
    (let [{:keys [marbles] :as state} (-> state
                                          (update :marbles #(dq/rotate % -7)))
          removed-marble (dq/peek-front marbles)]
      (-> state
          (update :marbles dq/pop-front)
          (update-in [:player-scores player] (fnil + 0) (+ marble removed-marble))))
    (-> state
        (update :marbles #(dq/rotate % 2))
        (update :marbles dq/push-front marble))))

(defn solve
  ([players max-marble]
   (->> (map vector
               (-> (range players) repeat flatten)
               (range max-marble))
          (reduce (fn [state [p m]]
                    (place-marble state (inc m) (inc p)))
                  {:marbles (dq/deque 0) :player-scores {}})
          :player-scores
          (map identity)                 
          (apply max-key second)
          second))
  ([]
   (let [[players max-marble] (parse-input "input/day09.txt")]
     (solve players max-marble))))

#_(solve)
;; => 361466
