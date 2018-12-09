(ns advent-of-code-2018.day04-1
  (:require [advent-of-code-2018.utils :as u]))

(defn parse-line
  [line]
  (let [[_ date minute cmd guard] (some #(re-find % line)
                                      [#"^\[(.+) \d\d:(\d\d)\] (wakes up)$"
                                       #"^\[(.+) \d\d:(\d\d)\] (falls asleep)$"
                                       #"^\[(.+) \d\d:(\d\d)\] (Guard #(\d+) begins shift)$"])]
    (-> {:date date :minute minute :cmd cmd :guard guard}
        (update :minute #(Integer/parseInt %)))))

(defn combine-data
  [recs]
  (->> recs
       (reduce (fn [[acc current-guard] {cmd :cmd guard :guard :as rec}]
                 (case cmd
                   ("wakes up" "falls asleep") [(conj acc (assoc rec :guard current-guard)) current-guard]
                   [acc (Integer/parseInt guard)]))
               [[] nil])
       first))

(defn expand-minutes
  [recs]
  (mapcat (fn [[{:keys [guard date] a-cmd :cmd a-minute :minute :as a}
                {b-cmd :cmd b-minute :minute :as b}]]             
            (map (fn [minute]
                   {:date date :guard guard :sleep-minute minute})
                 (range a-minute b-minute)))
          recs))

(defn most-minute
  [sleeps]
  (->> sleeps
       (group-by :sleep-minute)
       (map (fn [[minute minutes]]
              {:minute minute :count (count minutes)}))
       (apply max-key :count)))

(defn most-sleep
  [sleeps]
  (->> sleeps
       (group-by :guard)
       (map (fn [[g sleeps]]
              {:guard g
               :minutes-slept (count sleeps)
               :sleeps sleeps}))
       (apply max-key :minutes-slept)))

(defn parse-data
  [lines]
  (->> lines
       sort
       (map parse-line)
       combine-data
       (partition 2)
       expand-minutes))

(defn solve
  []
  (let [sleeps (->> "input/day04.txt"
                    u/file-lines
                    parse-data)
        {:keys [guard sleeps]} (most-sleep sleeps)]
    (* guard
       (:minute (most-minute sleeps)))))

#_ (solve)
;; => 87681
