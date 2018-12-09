(ns advent-of-code-2018.utils
  (:require [clojure.string :as str]))

(defn file-lines [file]
  (-> file
      slurp
      str/split-lines))
