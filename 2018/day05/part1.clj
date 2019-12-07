(require '[clojure.string :as str])

(defn get-input []
  (seq (str/trim-newline (slurp "input.txt"))))

(defn same-letter [c1 c2]
  (= (Character/toLowerCase c1) (Character/toLowerCase c2)))

(defn opposite-case [c1 c2]
  (not (= (Character/isUpperCase c1) (Character/isUpperCase c2))))

(defn characters-explode [c1 c2]
  (and (same-letter c1 c2) (opposite-case c1 c2)))

(defn react [acc ch]
  (if (empty? acc)
    (conj acc ch)
    (if (characters-explode (first acc) ch)
      (rest acc)
      (conj acc ch))))

(defn react-chain [input]
  (reduce react (seq [(first input)]) (rest input)))

(time (let [input (get-input)
      polymer (react-chain input)]
  (do
    (println "Starting size " (count input))
    (println "Reduced size " (count polymer)))))

(println "Smallest with a letter removed"
  (apply min
    (for [l "abcdefghijklmnopqrstuvwxyz"]
      (let [input (remove #{l (Character/toUpperCase l)} (get-input))]
        (count (react-chain input))))))

