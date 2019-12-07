(require '[clojure.string :as str])

(defn parse-int [val] (Long/parseLong val))

(defn get-input []
  (str/split (slurp "input.txt") #"\n"))

(defn parse [line]
  (let [[px py vx vy] (map parse-int
                        (drop 1
                          (re-find #"position=\<\s*(-?\d+),\s+(-?\d+)\> velocity=\<\s*(-?\d+),\s+(-?\d+)\>" line)))]
    {:position [px py], :velocity [vx vy]}))

(defn calculate-position [entry]
  (let [[px py] (:position entry)
        [vx vy] (:velocity entry)]
    {:position [(+ px vx) (+ py vy)] :velocity [vx vy]}))


(defn update-data [data]
  (map calculate-position data))

(defn vec2d
  "Return an x by y vector with all entries equal to val."
  [x y v]
  (vec (repeat y (vec (repeat x v)))))

(defn print-grid [grid]
  (doall (for [row grid]
           (println row))))

(defn fill-display [grd data min-x min-y]
  (loop [grid grd
         cur (first data)
         left (rest data)]
    (if (nil? cur)
      grid
      (recur
        (update-in grid [
                         (- (get-in cur [:position 1]) min-y)
                         (- (get-in cur [:position 0]) min-x)]
                   (constantly \#))
        (first left)
        (rest left)))))


(defn display [data threshold seconds]
  (let [max-x (apply max (map #(get-in % [:position 0]) data))
        min-x (apply min (map #(get-in % [:position 0]) data))
        max-y (apply max (map #(get-in % [:position 1]) data))
        min-y (apply min (map #(get-in % [:position 1]) data))
        width (+ 1 (Math/abs (- max-x min-x)))
        height (+ 1 (Math/abs (- max-y min-y)))
        should-display (and (> threshold width) (> threshold height))]
    (if should-display
      (let [grid (vec2d width height \.)
            ddata (fill-display grid data min-x min-y)]
        (print-grid ddata)
        (println "Seconds:" seconds ". Press ENTER to continue")
        (read-line)))))

(let [raw (get-input)
      in-data (map parse raw)]
  (loop [seconds 0
         data in-data]
    (display data 100 seconds)
    (recur (inc seconds) (update-data data))))

