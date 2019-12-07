(require '[clojure.string :as str])
(require 'clojure.set)

(defn solve [lines]
  (map
    (fn [line] (clojure.set/intersection
        #{2 3}
        (set
          (vals
            (frequencies
              (seq line))))))
    lines))

(defn has-twos-or-threes [line]
  (reduce
    (fn [[two three] v]
      (cond
        (and two three) [true true]
        (= v 2) [true three]
        (= v 3) [two true]
        :else [two three]))
      [false false]
      line))

(println (solve (str/split (slurp "input.txt") #"\n")))
