(ns aoc2019.day8
  (:require [clojure.string :as str]
            [clojure.java.io :as io])
  (:import  (javax.imageio ImageIO)
            (java.awt.image BufferedImage)))


(defn get-input [ss] (str/trim-newline ss))

(def height 6)
(def width 25)
(def layer-size (* width height))
(defn get-layers [v] (partition layer-size v))

(def enumerate (partial map-indexed (fn [idx v] [idx v])))

(defn gif-image
  ([layer] (gif-image layer 8))
  ([layer size]
   (let [buf (new BufferedImage (* width size) (* height size) BufferedImage/TYPE_BYTE_BINARY)
         graphics (do (.createGraphics buf) (.getGraphics buf))]
     (doall
       (for [[y row] (enumerate (partition width layer))
             [x pixel] (enumerate row)]
         (condp = pixel
           \1 (.fillRect graphics (* x size) (* y size) size size)
           \0 (.clearRect graphics (* x size) (* y size) size size))))
     (ImageIO/write buf "gif" (io/file "day8.gif")))))

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
    (gif-image image)
    (display-image image)))

(defn -main [& args]
  (let [encoded-image (get-input (slurp "day8.txt"))]
    (do
      (println "Part1" (part1 encoded-image))
      (println "Part2")
      (println (part2 encoded-image)))))

