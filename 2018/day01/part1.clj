(require '[clojure.string :as str])

(defn parse-int [v] (Integer/parseInt v))

(defn get-input []
  (map parse-int (str/split (slurp "input.txt") #"\n")))

(defn part1 [input] (reduce + input))

(defn part2 [input]
  (loop [freqs #{}
         current 0
         tail (cycle input)]
    (if (contains? freqs current)
      current
      (recur
        (conj freqs current)
        (+ current (first tail))
        (rest tail)))))

(let [input (get-input)]
  (println "Part 1:" (part1 input))
  (println "Part 2:" (part2 input)))
