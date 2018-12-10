(ns advent-of-code-2018.deque)

(defn deque 
  ([]
   '[()()])
  ([& elems]
   [elems '()]))

(defn push-front [deque elem]
  (let [[head tail] deque]
    [(cons elem head) tail]))

(defn push-back [deque elem]
  (let [[head tail] deque]
    [head (cons elem tail)]))

(defn pop-front [deque]
  (let [[head tail] deque]
    (if (empty? head)
      [(-> tail reverse rest) head]
      [(rest head) tail])))

(defn pop-back [deque]
  (let [[head tail] deque]
    (if (empty? tail)
      [tail (-> head reverse rest)]
      [head (rest tail)])))

(defn peek-front [deque]
  (let [[head tail] deque]
    (if (empty? head)
      (-> tail reverse first)
      (first head))))

(defn peek-back [deque]
  (let [[head tail] deque]
    (if (empty? tail)
      (-> head reverse first)
      (first tail))))

(defn rotate [deque n]
  (reduce (fn [deque _]
            (if (> n 0)
              (let [elem (peek-front deque)]
                (-> deque
                    pop-front
                    (push-back elem)))
              (let [elem (peek-back deque)]
                (-> deque
                    pop-back
                    (push-front elem)))))
          deque
          (range (java.lang.Math/abs n))))
