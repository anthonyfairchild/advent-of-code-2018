(ns advent-of-code-2018.day09-1)

(defn parse-input
  [file]
  (->> file
       slurp
       (re-find #"^(\d+) players; last marble is worth (\d+) points$")
       (drop 1)
       (map #(Integer/parseInt %))))

(defn rotate [n xs]
  (let [at (mod n (count xs)) 
        [newtail newhead] (split-at at xs)]
    (concat newhead newtail)))

(defn place-marble
  [{:keys [marbles player-scores] :as state} marble player]
  (if (= 0 (mod marble 23))
    (let [{:keys [marbles] :as state} (-> state
                                          (update :marbles #(rotate -7 %)))
          removed-marble (first marbles)]
      (-> state
          (update :marbles rest)
          (update-in [:player-scores player] (fnil + 0) (+ marble removed-marble))))
    (-> state
        (update :marbles #(rotate 2 %))
        (update :marbles conj marble))))

(defn solve
  []
  (let [[players max-marble] (parse-input "input/day09.txt")]
    (->> (map vector
              (-> (range players) repeat flatten)
              (range max-marble))
         (reduce (fn [state [p m]]
                   (place-marble state (inc m) (inc p)))
                 {:marbles '(0) :player-scores {}})
         :player-scores
         (map identity)                 
         (apply max-key second)
         second)))

#_(solve)
;; => 361466
