(ns aoc2019.day10
  (:require [clojure.string :as str]))

(def enumerate (partial map-indexed (fn [idx v] [idx v])))

(defn parse-input [ss]
  (set
    (let [data (-> ss str/trim-newline (str/split #"\n"))]
      (for [[y row] (enumerate data)
            [x v] (enumerate row)
            :when (= v \#)]
        {:x x :y y}))))

(defn radians-true [p1 p2]
  (Math/atan2 (- (:y p2) (:y p1)) (- (:x p2) (:x p1))))

; TODO, make it so that the radians "center point" is up. Right now its left/west/270
(defn radians [p1 p2]
  (+ (radians-true p1 p2) Math/PI))

(defn to-degrees [rad]
  (-> rad
      (* 180)
      (/ Math/PI)))

(defn degrees [p1 p2]
  (-> (radians p1 p2)
      to-degrees
      (+ 90)
      (mod 360)))

(do
  (println "0, " (degrees {:x 0 :y 0} {:x 0 :y 1}))
  (println "45, " (degrees {:x 0 :y 0} {:x 1 :y 1}))
  (println "90, " (degrees {:x 0 :y 0} {:x 1 :y 0}))
  (println "135, " (degrees {:x 0 :y 0} {:x 1 :y -1}))
  (println "180, " (degrees {:x 0 :y 0} {:x 0 :y -1}))
  (println "225, " (degrees {:x 0 :y 0} {:x -1 :y -1}))
  (println "269ish, " (degrees {:x 0 :y 0} {:x -1 :y -0.00001}))
  (println "270, " (degrees {:x 0 :y 0} {:x -1 :y 0}))
  (println "315, " (degrees {:x 0 :y 0} {:x -1 :y 1})))

(defn get-detections [data]
  (for [point data
        :let [other (disj data point)]]
    (-> (for [a other] (radians point a))
        set
        count
        (vector point))))

(defn manhattan [p1 p2]
  (+ (Math/abs (- (:x p1) (:x p2))) (Math/abs (- (:y p1) (:y p2)))))

; should return {radian: [{... :p point},{:p point}], radian: ...}
; and sorted by their manhattan distances
(defn get-destroys [point data]
  (let [other (disj data point)]
    (->> (for [a other] {:r (radians point a) :p a :m (manhattan point a)})
      (group-by :r)
      (map
        (fn [v] (sort-by :mah v))))))
;      (vector point))))

(defn foo [data]
  ())

(defn solve []
  (let [data (parse-input (slurp "day10.txt"))
        detections (get-detections data)
        [sees best] (apply max-key first detections)]        
        
    (println "part1" sees)))
;    (apply max-key first detections)))

(defn -main []
  (println "Part1" (solve)))
