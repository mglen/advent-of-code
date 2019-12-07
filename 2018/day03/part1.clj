; #1 @ 469,741: 22x26
(require '[clojure.string :as str])

(defn parse-int [v] (Integer/parseInt v))

(defn get-input []
  (str/split (slurp "input.txt") #"\n"))

(defn parse-line [line]
  (let [
        [idx, l, t, w, h] (map parse-int (drop 1 (re-find #"#(\d+) @ (\d+),(\d+): (\d+)x(\d+)" line)))]
    {:id idx, :left l, :top t, :width w, :height h}))

(defn get_grid []
  (reduce
    (fn [grd, c]
      (for [x (range (get c :left) (+ (get c :left) (get c :width)))
            y (range (get c :top) (+ (get c :top) (get c :height)))]
        (assoc-in grd [x y] (inc (get-in grd [x y])))))
    (vec (repeat 1001 (vec (repeat 1001 0))))
    (map parse-line (get-input))
  ))

(get_grid)

(println
  (let [filled_grid (get_grid)]
    (reduce
      (fn [acc [x y]]
        (do (println acc x y)
        (if (< 1 (get-in filled_grid [x y]))
          (inc acc)
          acc)))
      0
      (for [x (range 1000)
            y (range 1000)] [x y]))))

