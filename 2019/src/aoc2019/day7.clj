(ns aoc2019.day7
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
        input (first (:inputs program))]
    (if (nil? input)
      nil
      (-> program
          (assoc-in [:memory address] input)
          (update :cursor + 2)
          (update :inputs rest)))))

(defn output-opt [[mode] {:keys [memory cursor] :as program}]
  (let [output (mode memory (inc cursor))]
;    (println "Output:" output)
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
      3 (if-let [result (input-opt program)]
          (recur result)
          (assoc program :state :waiting-input)) ; Exit program, waiting on input
      4 (recur (output-opt modes program))
      5 (recur (jump-if-true modes program))
      6 (recur (jump-if-false modes program))
      7 (recur (less-than-opt modes program))
      8 (recur (equals-opt modes program))
      99 (assoc program :state :halted)
      (println "unexpected optcode" opt))))

; Copied from the internet
(defn permutations [colls]
  (if (= 1 (count colls))
    (list colls)
    (for [head colls
          tail (permutations (disj (set colls) head))]
      (cons head tail))))

(defn run-amplification [program phase-settings]
  (loop [settings phase-settings
         last-output 0
         programs-states []]
    (if (empty? settings)
      {:output last-output :programs programs-states}
      (let [setting (first settings)
            remaining (rest settings)
            p (assoc program :inputs [setting last-output])
            final-state (run p)
            output (:last-output final-state)]
        (recur remaining output (conj programs-states final-state))))))

(defn run-amplification-feedback-loop [program phase-settings]
  (let [{:keys [output programs]} (run-amplification program phase-settings)]
    (loop [waiting-programs programs
           last-output output]
      (println last-output)
      (if (empty? waiting-programs)
        {:output last-output}
        (let [p (first waiting-programs)
              new-waiting (subvec waiting-programs 1)
              new-state (run (assoc p :inputs [last-output]))
              output (:last-output new-state)]
          (if (= (:state new-state) :waiting-input)
            (recur (conj new-waiting new-state) output)
            (recur new-waiting output)))))))

(defn part1 [program]
  (let [stupid-elves (permutations [0 1 2 3 4])
        outputs (map #(vector (:output (run-amplification program %)) %) stupid-elves)]
    (apply max-key first outputs)))

(defn part2 [program]
  (let [stupid-elves (permutations [5 6 7 8 9])
        outputs (map #(vector (:output (run-amplification-feedback-loop program %)) %) stupid-elves)]
    (apply max-key first outputs)))

(defn -main [& args]
  (let [memory-intcodes (vec (get-input (slurp "day7.txt")))
;  (let [memory-intcodes (vec (get-input (read-line)))
        program {:memory memory-intcodes
                 :cursor 0}]
    (do
      (println "Part1" (part1 program))
      (println "Part2" (part2 program)))))


