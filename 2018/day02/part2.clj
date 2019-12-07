(use '[clojure.string :only (join)])

(defn omit-char [line index]
  (join [(subs line 0 index)
         (subs line (+ index 1))]))

(defn get-input []
  (str/split (slurp "input.txt") #"\n"))

(defn for-offset [lines index]
  (loop [seen #{}
         head (first lines)
         tail (rest lines)]
    (let [calcd (omit-char head index)]
      if (contains? seen head) head
        (recur (conj seen head) (first tail) (rest tail)))))

(defn solve []
  (let [lines (get-input)
        line_length (count (get lines 0))
        offsets (range line_length)]
    (take-while (partial for-offset lines) offsets)))

(println solve)

