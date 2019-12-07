(require '[clojure.string :as str])
(use '[clojure.set :only (union)])

(defn parse-int [v] (Integer/parseInt v))

(defn get-input [] (slurp "test-input.txt"))

(defn parse [input]
  (for [[_ coord-a val-a coord-b val-b1 val-b2] (re-seq  #"([xy])=(\d+), ([xy])=(\d+)..(\d+)\n?" input)
        val-b (range (parse-int val-b1) (inc (parse-int val-b2)))]
    {(keyword coord-a) (parse-int val-a), (keyword coord-b) val-b}))

(defn draw [sand-coords water-flowing water-standing]
  (let [x-min (:x (apply min-key :x sand-coords))
        x-max (:x (apply max-key :x sand-coords))
        y-min (:y (apply min-key :y sand-coords))
        y-max (:y (apply max-key :y sand-coords))]
    (doseq [y (range y-min (inc y-max))
            x (range x-min (inc x-max))]
      (do
        (condp contains? {:x x, :y y}
          water-standing (print "~")
          water-flowing  (print "|")
          sand-coords    (print "#")
          (print "."))
        (if (= x x-max) (print "\n"))))))

(defn add-y [coord] (update coord :y inc))
(defn sub-y [coord] (update coord :y dec))
(defn add-x [coord] (update coord :x inc))
(defn sub-x [coord] (update coord :x dec))

(defn flow-side [_coord fn sand standing-water]
  (loop [coord (fn _coord)
         standing #{}]
    (if (or (contains? sand coord)
            (contains? standing-water coord))
      {:flowing #{}, :standing standing}
      (if (contains? sand (add-y coord))
        (recur (fn coord) (conj standing coord))
        {:flowing #{(add-y coord)}, :standing (conj standing coord)}))))

(defn flow-x [coord sand standing-water]
  (condp contains? (add-y coord)
    flowing-water {:flowing #{} :standing #{}}
;    standing-water {:flowing #{} :standing #{}}
    sand (let [right (flow-side coord add-x sand standing-water)
               left (flow-side coord sub-x sand standing-water)
               new-flowing (union (:flowing right) (:flowing left))
               new-standing (conj (union (:standing right) (:standing left)) coord)]
           (if (empty? new-flowing)
             {:flowing #{(sub-y coord)}, :standing new-standing}
             {:flowing new-flowing, :standing new-standing}))
    {:flowing #{(add-y coord)}, :standing #{}}))

(defn flow [sand-coords water-start-x]
  (let [y-min (:y (apply min-key :y sand-coords))
        y-max (:y (apply max-key :y sand-coords))
        water-start {:x water-start-x, :y y-min}]
    (loop [flows (clojure.lang.PersistentQueue/EMPTY)
           flow water-start
           flowing-water  #{}
           standing-water #{}]
      (if flow
        (if (> (:y flow) y-max)
          (recur (pop flows)
                 (peek flows)
                 flowing-water
                 standing-water)
          (let [water (flow-x flow sand-coords standing-water)
                new-flows (apply conj flows (:flowing water))
                new-flowing (union (:flowing water) flowing-water)
                new-standing (union (:standing water) standing-water)]
            (println "round" flow water)
            (recur (pop new-flows)
                   (peek new-flows)
                   new-flowing
                   new-standing)))
        {:flowing flowing-water, :standing standing-water}))))


(let [input (get-input)
      sand-coords (set (parse input))
      water-start-x 500
      flowed (flow sand-coords water-start-x)]
  (draw sand-coords (:flowing flowed) (:standing flowed)))
