(require '[clojure.string :as str])

(defn get-input [] (str/split (slurp "input.txt") #"\n"))

(defn get-parsed []
  (into {}
    (for [[y line] (map-indexed #(vector %1 %2) (get-input))
          [x chr] (map-indexed #(vector %1 %2) line)
          :when (not= chr \space)]
      [[x y] chr])))

(def dirs {\< :left, \> :right, \^ :up \v :down})

(defn get-carts [tracks]
  (into (sorted-map)
    (for [[coords dir] (filter (fn [[k v]] (#{\< \> \^ \v} v)) tracks)]
      [coords {:dir (get dirs dir), :next-turn :left}])))

(defn track-for-cart [cart-data]
  (case (:dir cart-data)
    (:left :right) \-
    (:up :down) \|
    (throw (Exception. "Bad cart"))))

(defn fixup-data [tracks carts]
  "Replace cart markers on tracks with track characters"
  (apply assoc tracks (sequence cat (map (fn [[coords cart-data]] [coords (track-for-cart cart-data)]) carts))))


(defn get-next-turn [previous]
  (case previous
    :left :straight
    :straight :right
    :right :left))

(defn turn-to-direction [dir next-turn]
  (case next-turn
    :straight dir
    :left  (case dir :up :left, :left :down, :down :right, :right :up)
    :right (case dir :up :right, :right :down, :down :left, :left :up)))

(defn get-next-direction [dir next-turn track]
  (if (= track \+) (turn-to-direction dir next-turn)
    (case dir
      :up (case track \/ :right \\ :left dir)
      :down (case track \/ :left \\ :right dir)
      :left (case track \/ :down \\ :up dir)
      :right (case track \/ :up \\ :down dir))))


(defn get-next-coords [[x y] dir]
  (case dir
    :up    [x (- y 1)]
    :down  [x (+ y 1)]
    :left  [(- x 1) y]
    :right [(+ x 1) y]))

(defn move-carts [grid carts]
  "Returns updated list of carts?"
  (loop [[coords cart-data] (first carts) ; can you do this on a sorted map? yes, yes you can.
         left (dissoc carts coords)
         processed (sorted-map)]
    (if (nil? coords)
      processed
      (let [next-coords (get-next-coords coords (:dir cart-data)) ; Cart is already facing the right way
            next-track (get grid next-coords)
            next-dir (get-next-direction (:dir cart-data) (:next-turn cart-data) next-track)
            next-turn (if (= next-track \+) (get-next-turn (:next-turn cart-data)) (:next-turn cart-data))
            next-data {:dir next-dir, :next-turn next-turn}]
        (if (or
              (contains? processed next-coords)
              (contains? left next-coords))
          (let [_processed (dissoc processed next-coords)
                _left (dissoc left next-coords)]
            (recur
              (first _left)
              (dissoc _left (nth (first _left) 0))
              _processed))
          (recur
            (first left)
            (dissoc left (nth (first left) 0))
            (conj processed [next-coords next-data])))))))

(let [unfixed-data (get-parsed)
      carts (get-carts unfixed-data)
      data (fixup-data unfixed-data carts)]
;  (println data)
  (println carts)
  (loop [cart-data (move-carts data carts)
         counter 1]
    (if (<= (count cart-data) 1)
      (println "Last cart is at" cart-data)
      (recur (move-carts data cart-data) (inc counter)))))
