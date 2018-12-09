(ns advent-of-code-2018.day06-1
  (:require [advent-of-code-2018.utils :as u]
            [clojure.string :as str]))

(defn bounds
  [coords]
  (->> coords
       (reduce (fn [[max-x max-y min-x min-y] [x y]]
                 [(max max-x x) (max max-y y)
                  (min min-x x) (min min-y y)])
               [Integer/MIN_VALUE Integer/MIN_VALUE
                Integer/MAX_VALUE Integer/MAX_VALUE])))

(defn distance
  [[ax ay] [bx by]]
  (+ (java.lang.Math/abs (- bx ax))
     (java.lang.Math/abs (- by ay))))

(defn nearest-coordinates
  [coords pos]
  (->> coords
       (map (fn [cpos]
              {:cpos cpos :distance (distance cpos pos)}))
       (group-by :distance)
       (apply min-key first)
       second))

(reduce + [1 2 3])

(defn parse-coords
  [file]
  (->> file
       u/file-lines
       (map #(str/split % #", "))
       (map (fn [[a b]] [(Integer/parseInt a)
                         (Integer/parseInt b)]))))

(defn map-nearest
  [[max-x max-y min-x min-y] coords]
  (for [x (range min-x (inc max-x))
        y (range min-y (inc max-y))]
    (let [nearest-coords (nearest-coordinates coords [x y])
          nearest-coord (if (> (count nearest-coords) 1)
                          :. ;; equally from two or more coords
                          (:cpos (first nearest-coords)))]
      {:pos [x y] :nearest-coord nearest-coord})))

(defn border-positions
  [[max-x max-y min-x min-y]]
  (-> (for [x (range min-x (inc max-x))
            y (range min-y (inc max-y))
            :when (or (= x min-x)
                      (= x max-x)
                      (= y min-y)
                      (= y max-y))]
        [x y])
      set))

(defn solve
  []
  (let [coords (->> "input/day06.txt"
                    parse-coords)
        bounds (bounds coords)
        map-nearest (->> coords
                         (map-nearest bounds)
                         ;; filter out equally far tiles
                         (filter (fn [{nearest :nearest-coord}]
                                   (not (= nearest :.)))))
        border-positions (border-positions bounds)
        infinite (->> map-nearest
                      (filter (fn [{pos :pos}]
                                (get border-positions pos)))
                      (map :nearest-coord)
                      set)]
    (->> map-nearest                 
         ;; filter out infinite
         (filter (fn [{nearest :nearest-coord}]
                   (not (get infinite nearest))))
         (group-by :nearest-coord)
         (map (fn [[[a] b]]
                [a (count b)]))
         (apply max-key second)
         second)))

#_(solve)
;; => 4290
