(require '[clojure.string :as str])

(defn parse-int [val] (Integer/parseInt val))

(def input (map parse-int (str/split (str/trim-newline (slurp "input.txt")) #"\s+")))

(defn get-entries [data]
  (let [[children metadata] (take 2 data)]
    (if (not (nil? metadata))
      (recur (drop (+ 2 (+ children metadata)) data)  (+ metadata-count metadata))
      metadata-count)))

(println (get-entries input))




