(ns aoc2019.day1
  (:require [clojure.string :as str]))

(defn get-input [f] (str/split (slurp f) #"\n"))

; part1
(defn get-fuel [mass] (- (int (/ mass 3)) 2))

; part2
(defn total-fuel [mass]
  (loop [fuel (get-fuel mass) fuel-acc 0]
    (if-not (pos? fuel) fuel-acc
      (recur (get-fuel fuel) (+ fuel-acc fuel)))))

(defn -main [& args]
  (let [module-masses (map #(Integer/parseInt %) (get-input "day1.txt"))
        fuels (map get-fuel module-masses)
        total-fuels (map total-fuel module-masses)]
    (println "Part1: " (reduce + fuels))
    (println "Part2: " (reduce + total-fuels))))
