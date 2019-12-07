(require '[clojure.string :as str])
(use '[clojure.set :only (difference)])

(defn get-input []
  (str/split (slurp "input.txt") #"\n"))

(defn parse [line]
  (let [parts (str/split line #"\s+")
        before (get parts 1)
        after (get parts 7)]
    [before after]))

(defn get-available [data]
  (let [before_items (set (map #(get % 0) data))
        after_items (set (map #(get % 1) data))]
    (difference before_items after_items)))

(defn solve [dt]
  (loop [data dt
         answer []]
    (let [avail (get-available data)
          step (first (sort avail))]
          (if (not (nil? step))
            (recur (filter #(not (= step (get % 0))) data) (conj answer step))
            answer))))


(println (solve (map parse (get-input))))
