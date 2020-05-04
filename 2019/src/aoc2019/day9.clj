(ns aoc2019.day9
  (:require [clojure.string :as str]))

(defn get-input [ss]
  (map
    #(Long/parseLong %)
    (str/split (str/trim-newline ss) #",")))

(defn set-memory [program idx v]
  (let [memory (:memory program)
        size (count memory)
        diff (inc (- idx size))
        p (if (pos? diff)
            (assoc program :memory (apply conj memory (repeat diff 0)))
            program)]
    (assoc-in p [:memory idx] v)))
          
(defn immediate [program pos]
  (nth (:memory program) pos))
(defn position [program pos]
  (nth (:memory program) (immediate program pos) 0))
(defn rel-immediate [program pos]
  (+ (:relative-base program) (immediate program pos)))
(defn relative [program pos]
  (nth (:memory program) (rel-immediate program pos)))

(defn get-mem [prog mode pos]
  (condp = mode
   \0 (position prog pos)
   \1 (immediate prog pos)
   \2 (relative prog pos)))

(defn set-mem [prog mode pos v]
  (condp = mode
    \0 (set-memory prog (immediate prog pos) v)
    \1 (throw (Exception. (str "Cannot set mem in immediate mode " mode "pos " pos "val " v)))
    \2 (set-memory prog (rel-immediate prog pos) v)
    (println "Mode not recognized" mode)))

(defn param-modes [opt]
  (-> opt
      (quot 100)
      str
      reverse
      (concat (repeat \0))))

; Operations
(defn math-opt [f [mode1 mode2 mode3] {:keys [memory cursor] :as program}]
  (let [param1 (get-mem program mode1 (+ cursor 1))
        param2 (get-mem program mode2 (+ cursor 2))
        value  (f param1 param2)]
    (-> program
        (set-mem mode3 (+ cursor 3) value)
        (update :cursor + 4))))

(def add-opt (partial math-opt +))
(def multiply-opt (partial math-opt *))

(defn input-opt [[mode] {:keys [memory cursor] :as program}]
  (let [input (first (:inputs program))]
    (if (nil? input)
      nil
      (-> program
          (set-mem mode (+ cursor 1) input)
          (update :cursor + 2)
          (update :inputs rest)))))

(defn output-opt [[mode] {:keys [memory cursor] :as program}]
  (let [output (get-mem program mode (inc cursor))]
    (println "Output:" output)
    (-> program
        (update :cursor + 2)
        (assoc :last-output output))))

(defn jump-if-opt [pred [mode1 mode2] {:keys [memory cursor] :as program}]
  (let [param1  (get-mem program mode1 (+ cursor 1))
        param2  (get-mem program mode2 (+ cursor 2))]
    (-> program
        (assoc :cursor (if (pred param1) param2 (+ cursor 3))))))

(def jump-if-true (partial jump-if-opt #(not (zero? %))))
(def jump-if-false (partial jump-if-opt zero?))

(defn compare-opt [cmp [mode1 mode2 mode3] {:keys [memory cursor] :as program}]
  (let [param1  (get-mem program mode1 (+ cursor 1))
        param2  (get-mem program mode2 (+ cursor 2))
        value (if (cmp param1 param2) 1 0)]
    (-> program
        (set-mem mode3 (+ cursor 3) value)
        (update :cursor + 4))))

(def less-than-opt (partial compare-opt <))
(def equals-opt (partial compare-opt =))

(defn relative-opt [[mode] {:keys [memory cursor] :as program}]
  (let [output (get-mem program mode (inc cursor))]
    (-> program
      (update :cursor + 2)
      (update :relative-base + output))))

; Loop
(defn run [program]
  (let [opt (nth (:memory program) (:cursor program))
        opcode (mod opt 100)
        modes (param-modes opt)]
    (condp = opcode
      1 (recur (add-opt modes program))
      2 (recur (multiply-opt modes program))
      3 (if-let [result (input-opt modes program)]
          (recur result)
          (assoc program :state :waiting-input)) ; Exit program, waiting on input
      4 (recur (output-opt modes program))
      5 (recur (jump-if-true modes program))
      6 (recur (jump-if-false modes program))
      7 (recur (less-than-opt modes program))
      8 (recur (equals-opt modes program))
      9 (recur (relative-opt modes program))
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
  (run (assoc program :inputs [1])))
(defn part2 [program]
  (run (assoc program :inputs [2])))

(defn -main [& args]
  (let [memory-intcodes (vec (get-input (slurp "day9.txt")))
;  (let [memory-intcodes (vec (get-input (read-line)))
        program {:memory memory-intcodes
                 :cursor 0
                 :relative-base 0}]
    (do
      (println "Part1" (part1 program))
      (println "Part2" (part2 program)))))

