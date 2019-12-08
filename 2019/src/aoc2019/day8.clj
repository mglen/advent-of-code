(ns aoc2019.day8
  (:require [clojure.string :as str]))

(defn get-input [ss] (str/trim-newline ss))

(def width 25)
(def layer-size (* width 6))
(defn get-layers [v] (partition layer-size v))

(defn merge-layers [_layers]
  (loop [index 0
         layers _layers
         result []]
    (if (empty? (first layers))
      result
      (let [pixels (map first layers)
            more (map rest layers)
            pixel (first (filter #(not= % \2) pixels))]
        (recur (inc index) more (conj result pixel))))))

(defn display-image [layer]
  (str/join "\n"
    (for [row (partition width layer)
          :let [v (map #(condp = %
                          \0 \space
                          \1 \#) row)]]
      (str/join v))))

(defn part1 [encoded-image]
  (let [layers (get-layers encoded-image)
        layers-freqs (map frequencies layers)
        fewest-zeros (apply min-key #(get % \0) layers-freqs)]
    (* (get fewest-zeros \1) (get fewest-zeros \2))))

(defn part2 [encoded-image]
  (let [layers (get-layers encoded-image)
        image (merge-layers layers)]
    (display-image image)))

(defn -main [& args]
  (let [encoded-image (get-input (slurp "day8.txt"))]
    (do
      (println "Part1" (part1 encoded-image))
      (println "Part2")
      (println (part2 encoded-image)))))

