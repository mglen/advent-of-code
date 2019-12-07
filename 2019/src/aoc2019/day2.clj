(ns aoc2019.day2
  (:require [clojure.string :as str]))

(defn get-input [f]
  (map
    #(Integer/parseInt %)
    (str/split (str/trim-newline (slurp f)) #",")))

(defn math-opt [f {:keys [memory cursor] :as program}]
  (let [first-id  (nth memory (+ cursor 1))
        second-id (nth memory (+ cursor 2))
        dest-id   (nth memory (+ cursor 3))
        value (f (nth memory first-id) (nth memory second-id))]
    (-> program
        (assoc-in [:memory dest-id] value)
        (update :cursor + 4))))

(def add-opt (partial math-opt +))

(def multiply-opt (partial math-opt *))

(defn run [program]
  (let [opt (nth (:memory program) (:cursor program))]
    (condp = opt
      1 (recur (add-opt program))
      2 (recur (multiply-opt program))
      99 program
      (println "unexpected optcode" opt))))

(defn part1 [program]
  (let [modified-program (-> program
                             (assoc-in [:memory 1] 12)
                             (assoc-in [:memory 2] 2))
        final-state (run modified-program)]
    (-> final-state :memory first)))

(defn part2 [program]
  (loop [inputs (for [n (range 0 100) v (range 0 100)] [n v])]
    (if-let [[noun verb] (first inputs)]
      (let [modified-program (-> program
                                 (assoc-in [:memory 1] noun)
                                 (assoc-in [:memory 2] verb))]
        (if (= 19690720 (-> (run modified-program) :memory first))
          (+ (* 100 noun) verb)
          (recur (rest inputs))))
      "No aswer found!")))

(defn -main [& args]
  (let [memory-intcodes (vec (get-input "day2.txt"))
        program {:memory memory-intcodes
                 :cursor 0}]
    (println program)
    (println "Part1: " (part1 program))
    (println "Part2: " (part2 program))))

