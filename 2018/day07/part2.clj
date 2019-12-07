(require '[clojure.string :as str])
(use '[clojure.set :only (difference union)])

(def letter-times (zipmap "ABCDEFGHIJKLMNOPQRSTUVWXYZ" (range 61 87)))

(defn get-input []
  (str/split (slurp "input.txt") #"\n"))

(defn parse [line]
  (let [parts (str/split line #"\s+")
        before (first (char-array (get parts 1)))
        after (first (char-array (get parts 7)))]
    [before after]))

(defn get-available [data]
  (set (map first (filter #(empty? (second %)) data))))

(defn drop-finished-steps [steps data]
  (let [upd-data (apply dissoc data steps)]
    (reduce
      (fn [acc, k] (update-in acc [k] #(difference % steps)))
      upd-data
      (keys upd-data))))

(defn check-in-progress [timee in-progress]
  (set (for [[k v] in-progress :when (= v timee)] k)))

(defn solve [dt]
  (loop [data dt
         answer []
         timee 0
         in-progress {}]
    (if (and (pos? timee) (zero? (count data))) timee
    (let [avail (difference (get-available data) (keys in-progress))
          step (first (sort avail))]
          (if (and
                (not (nil? step))
                (< (count in-progress) 5))
            (recur
              data
              (conj answer step)
              timee
              (assoc in-progress step
                     (do (println timee step)
                     (+ timee
                        (get letter-times step)))))
            (let [new-time (inc timee)
                  finished-steps (check-in-progress new-time in-progress)
                  new-in-progress (apply dissoc in-progress finished-steps)
                  new-data (drop-finished-steps finished-steps data)]
              (recur
                new-data
                answer
                new-time
                new-in-progress)
            ))))))

(defn fmap [f m]
    (zipmap (keys m) (map f (vals m))))

(defn the-data []
  (let [input-data (map parse (get-input))
      all-steps (set (concat (map second input-data) (map first input-data)))
      steps-with-blocked (fmap #(set (map first %)) (group-by second input-data))]
  (reduce (fn [acc v] (if (contains? acc v) acc (assoc acc v #{}))) steps-with-blocked all-steps)))

(println (solve (the-data)))
