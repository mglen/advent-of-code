(defn calculate [x y serial]
  (let [rack-id (+ x 10)
        step2 (* rack-id y)
        step3 (+ step2 serial)
        step4 (* step3 rack-id)
        step5 (int (Math/floor (mod (/ step4 100) 10)))
        power-level (- step5 5)]
    power-level))

;(defn wrap-power [data x y size]



(defn power-value [data xv yv size]
  (reduce +
    (for [x (range xv (+ xv size))
          y (range yv (+ yv size))]
      (nth (nth data y) x))))

(defn get-powers [data]
  (for [x (range 0 (- 300 3))
        y (range 0 (- 300 3))
        size (range 2 (- 301 (max x y)))]
      [(power-value data x y size) (inc x) (inc y) size])) ; (inc) because zero-indexed

(let [serial 2694
      data (mapv (fn [y] (mapv #(calculate % y serial) (range 1 301))) (range 1 301))]
  (println (apply max-key first (get-powers data))))

