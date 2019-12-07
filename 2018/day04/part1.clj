(require '[clojure.string :as str])

(defn parse-int [val] (Long/parseLong val))

(defn get-input []
  (str/split (slurp "input.txt") #"\n"))

;[idx, l, t, w, h] (map parse-int (drop 1 (re-find #"[(\d+)-(\d+)-(\d+) (\d+):(\d+)" line)))
;[1518-04-12 00:36] falls asleep
(defn parse [line]
  (let [datetime (subs line 1 17)
        [date timee] (str/split datetime #" ")
        [year, month, day] (str/split date #"-")
        [hour, minut] (str/split timee #":")
        epochish (parse-int (str/join [year month day hour minut]))
        minute (parse-int minut)
        typee (condp #(str/includes? %2 %1) line
                "wakes" :wake
                "falls" :sleep
                "Guard" :switch)
        id (if (= typee :switch)
             (let [start (inc (str/index-of line "#"))
                   end (str/index-of line " " start)]
               (parse-int (subs line start end)))
             nil)]
    {:epoch epochish, :minute minute, :type typee, :guard_id id}))

(defn partition-by-key [keyname seqn]
  (let [last_val (volatile! nil)
        thinger (fn [item]
                  (if (or (= item last_val) (= item nil))
                    last_val
                    (vreset! last_val item)))]
    (partition-by #(thinger (get keyname %)) seqn)))

(let [last_val (atom false)]
  (->> (map parse (get-input))
       (sort-by #(get :epoch %))
       (partition-by-key :guard_id)
       (map #(reduce #() [] %))
       ; then reduce to a [(range start end),...] values?
       ))
; todo more on this
