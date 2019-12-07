(require '[clojure.string :as str])

(defn get-input []
  (str/split (slurp "input.txt") #"\n"))

(defn to-bool [c] (= c \#))

(defn parse-changes [line]
  (let [[from to] (str/split line #" => ")]
    [(map to-bool from) (to-bool to)]))

(defn parse-initial [line]
  (let [raw_initial (subs line 15)
        initial_bools (map to-bool raw_initial)]
    (map (fn [i x d] {:id i, :exists x}) (range) initial_bools)))

(defn parse []
  (let [input (get-input)
        initial (parse-initial (first input))
        state_changes (into {} (map parse-changes (drop 2 input)))]
    (println initial)
    (println state_changes)))

;(def extend-pots [current]
(def false-vec (vec (repeat 5 false)))

(defn plant-change [current values state-changes]
  (let [offset (- current 2)
        indexes (map #(- % offset) values)
        to-check (apply assoc false-vec (map indexes (repeat true)))]
    (get state-changes to-check)))

;(defn generate-next [previous-gen state-changes]
;  (loop [values (vector (first previous-gen))
;         current (- (:id (first previous-gen) 2))
;         more (rest previous-gen)]
;    (let [matches (does-match current values)]



(defn generate-next-generation [previous_gen state_changes]
  (loop [five [false false false false (first current)]
         more (rest current)
         new []]
    (if (contains? state_changes five)
      (recur
        (conj (rest five) (first more))
        (rest more)
        (conj new (get state_changes five)))
      (recur
        (conj (rest five) (first more))
        (rest more)
        (conj new (nth five 2))))))


(parse)
