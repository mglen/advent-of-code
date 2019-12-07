(ns aoc2019.day5
  (:require [clojure.string :as str]))

(defn get-input [ss]
  (map
    #(Integer/parseInt %)
    (str/split (str/trim-newline ss) #",")))

; Parameter handling
(defn position [memory pos] (nth memory (nth memory pos)))
(defn immediate [memory pos] (nth memory pos))

(defn param-mode [v]
  (condp = v
    \0 position
    \1 immediate))

(defn param-modes [opt]
  (-> opt
      (quot 100)
      (#(map param-mode (str %)))
      reverse
      (concat (repeat position))))

; Operations
(defn math-opt [f [mode1 mode2] {:keys [memory cursor] :as program}]
  (let [param1  (mode1 memory (+ cursor 1))
        param2  (mode2 memory (+ cursor 2))
        address (immediate memory (+ cursor 3))
        value (f param1 param2)]
    (-> program
        (assoc-in [:memory address] value)
        (update :cursor + 4))))

(def add-opt (partial math-opt +))
(def multiply-opt (partial math-opt *))

(defn input-opt [{:keys [memory cursor] :as program}]
  (let [address (immediate memory (+ cursor 1))
        input (:input program)]
    (-> program
        (assoc-in [:memory address] input)
        (update :cursor + 2))))

(defn output-opt [[mode] {:keys [memory cursor] :as program}]
  (let [output (mode memory (inc cursor))]
    (println "Output:" output)
    (-> program
        (update :cursor + 2)
        (assoc :last-output output))))

(defn jump-if-opt [pred [mode1 mode2] {:keys [memory cursor] :as program}]
  (let [param1  (mode1 memory (+ cursor 1))
        param2  (mode2 memory (+ cursor 2))]
    (-> program
        (assoc :cursor (if (pred param1) param2 (+ cursor 3))))))

(def jump-if-true (partial jump-if-opt #(not (zero? %))))
(def jump-if-false (partial jump-if-opt zero?))

(defn compare-opt [cmp [mode1 mode2] {:keys [memory cursor] :as program}]
  (let [param1  (mode1 memory (+ cursor 1))
        param2  (mode2 memory (+ cursor 2))
        address (immediate memory (+ cursor 3))
        value (if (cmp param1 param2) 1 0)]
    (-> program
        (assoc-in [:memory address] value)
        (update :cursor + 4))))

(def less-than-opt (partial compare-opt <))
(def equals-opt (partial compare-opt =))

; Loop
(defn run [program]
  (let [opt (nth (:memory program) (:cursor program))
        opcode (mod opt 100)
        modes (param-modes opt)]
    (condp = opcode
      1 (recur (add-opt modes program))
      2 (recur (multiply-opt modes program))
      3 (recur (input-opt program))
      4 (recur (output-opt modes program))
      5 (recur (jump-if-true modes program))
      6 (recur (jump-if-false modes program))
      7 (recur (less-than-opt modes program))
      8 (recur (equals-opt modes program))
      99 program
      (println "unexpected optcode" opt))))

(defn -main [& args]
  (let [memory-intcodes (vec (get-input (slurp "day5.txt")))
;  (let [memory-intcodes (vec (get-input (read-line)))
        program {:memory memory-intcodes
                 :cursor 0}]
    (do
      (println "Part1")
      (run (assoc program :input 1)))
    (do
      (println "Part2")
      (run (assoc program :input 5)))))

